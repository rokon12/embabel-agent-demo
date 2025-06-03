
package ca.bazlur.agent;

import ca.bazlur.BookRecommendationProperties;
import ca.bazlur.model.*;
import ca.bazlur.repository.BookRepository;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.WaitFor;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.domain.library.InternetResource;
import com.embabel.common.ai.model.LlmOptions;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Agent(
        description = "Recommend books based on preferences and personalized analysis",
        scan = true
)
public class BookRecommendationAgent {

    private static final Logger logger = LoggerFactory.getLogger(BookRecommendationAgent.class);
    private final BookRecommendationProperties recommendationProperties;
    private final BookRepository bookRepository;

    public BookRecommendationAgent(
            BookRecommendationProperties recommendationProperties,
            BookRepository bookRepository) {
        this.recommendationProperties = recommendationProperties;
        this.bookRepository = bookRepository;
    }

    @Action
    public UserPreferences extractUserPreferences(UserInput userInput) {
        logger.info("Extracting user preferences from input: {}", userInput.getContent());
        return PromptRunner.withLlm(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()))
                .createObjectIfPossible(
                        """
                                Extract reading preferences from this user input, including favorite genre,
                                current reading mood, available reading time per week, and any specific author interest:
                                %s""".formatted(userInput.getContent()),
                        UserPreferences.class
                );
    }

    @Action(cost = 100.0)
    public UserPreferences collectUserPreferences() {
        return WaitFor.formSubmission("Let's get some reading preferences to personalize your recommendations",
                UserPreferences.class);
    }

    @Action(toolGroups = {CoreToolGroups.WEB})
    @JsonDeserialize(as = ArrayList.class)
    public List<InternetResource> findBookRelatedResources(String bookTitle, String author, String genre) {
        logger.info("Finding book-related resources for: {} by {} in genre {}", bookTitle, author, genre);

        var prompt = """
                Find relevant online resources related to the book "%s" by %s in the genre %s.
                Use web tools and generate search queries to find:
                1. Book reviews from reputable sources
                2. Author interviews or profiles
                3. Reading guides or discussion questions
                4. Similar book recommendations
                5. Literary analyses or critical essays
                
                For each resource, provide:
                - A brief summary (2-3 sentences)
                - The URL
                - Why it would be valuable to a reader interested in this book
                
                Return 5-7 high-quality resources that would enhance the reading experience.
                """.formatted(bookTitle, author, genre);

        return PromptRunner.withLlm().createObject(prompt, ArrayList.class);
    }

    @Action
    @JsonDeserialize(as = ArrayList.class)
    public List<Book> findBooksByGenre(UserPreferences userPreferences) {
        logger.info("Finding books in genre: {}", userPreferences.favoriteGenre());
        try {
            return bookRepository.findByGenre(BookGenre.valueOf(userPreferences.favoriteGenre().toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid genre: {}, using FICTION as default", userPreferences.favoriteGenre());
            return bookRepository.findByGenre(BookGenre.FICTION);
        }
    }

    @Action(toolGroups = {CoreToolGroups.WEB})
    @JsonDeserialize(as = ArrayList.class)
    public List<InternetResource> findWebResources(UserPreferences userPreferences) {
        logger.info("Finding web resources for preferences: {}", userPreferences);

        var prompt = """
                Find online resources for someone who loves %s books and is in a %s mood.
                They have %s hours per week to read.
                Use web tools to find:
                1. Reading recommendation sites
                2. Book review blogs
                3. Genre-specific communities
                4. Author interviews or spotlights
                
                Return 3-5 high-quality resources with URLs and brief descriptions.
                """.formatted(
                userPreferences.favoriteGenre(),
                userPreferences.currentMood(),
                userPreferences.readingTimePerWeek()
        );

        return PromptRunner.withLlm().createObject(prompt, ArrayList.class);
    }

    @AchievesGoal(description = "Personalized book recommendations have been generated for the user")
    @Action
    public BookRecommendationReport generateRecommendationReport(
            UserPreferences userPreferences,
            List<Book> availableBooks,
            List<InternetResource> webResources) {
        logger.info("Generating final recommendation report for user: {}", userPreferences);

        var llm = LlmOptions.fromModel(recommendationProperties.getRecommendationLlm())
                .withTemperature(0.7);

        var bookList = availableBooks.stream()
                .map(book -> "- " + book.title() + " by " + book.author())
                .limit(5)
                .collect(java.util.stream.Collectors.joining("\n"));

        var resourceList = webResources.stream()
                .map(resource -> "- " + resource.toString())
                .limit(3)
                .collect(java.util.stream.Collectors.joining("\n"));

        var prompt = """
                Create personalized book recommendations for this reader:
                
                Preferences:
                - Genre: %s
                - Mood: %s
                - Reading time: %s hours/week
                - Author interest: %s
                
                Available books:
                %s
                
                Web resources found:
                %s
                
                Generate a comprehensive report with:
                1. Personalized book recommendations with confidence scores
                2. Reading lists tailored to their mood and time
                3. Links to helpful resources
                
                Format as a complete recommendation report.
                """.formatted(
                userPreferences.favoriteGenre(),
                userPreferences.currentMood(),
                userPreferences.readingTimePerWeek(),
                userPreferences.authorInterest() != null ? userPreferences.authorInterest() : "None specified",
                bookList,
                resourceList
        );

        return PromptRunner.withLlm(llm).createObject(prompt, BookRecommendationReport.class);
    }

    @Action
    public BookRecommendationReport createThematicReadingList(String theme, int timeframeWeeks) {
        logger.info("Creating thematic reading list for theme: '{}' with timeframe: {} weeks", theme, timeframeWeeks);

        return PromptRunner.withLlm(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()))
                .createObject("""
                        Create a curated reading list focused on: '%s'
                        Design for completion within %d weeks.
                        Include 3-5 books with brief descriptions and reading order.
                        Explain thematic connections between books.
                        """.formatted(theme, timeframeWeeks), BookRecommendationReport.class);
    }

    @Action
    public BookRecommendationReport recommendSimilarBooks(String bookTitle, String author) {
        logger.info("Finding books similar to: '{}' by {}", bookTitle, author);

        return PromptRunner.withLlm(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()))
                .createObject("""
                        Find books similar to '%s' by %s.
                        Consider similar themes, writing style, and genre.
                        Provide 3-5 recommendations with explanations of similarities.
                        Include both well-known and lesser-known titles.
                        """.formatted(bookTitle, author), BookRecommendationReport.class);
    }
}
