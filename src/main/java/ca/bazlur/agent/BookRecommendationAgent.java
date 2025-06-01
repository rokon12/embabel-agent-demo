
package ca.bazlur.agent;

import ca.bazlur.BookRecommendationProperties;
import ca.bazlur.model.ReadingHistory;
import ca.bazlur.model.BookRecommendationReport;
import ca.bazlur.model.ReadingMood;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.BuildableLlmOptions;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.common.ai.model.ModelSelectionCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;

import static com.embabel.agent.api.annotation.ActionMethodPromptRunnerKt.using;

@Agent(
        description = "Recommend books based on reading history, preferences, and personalized analysis",
        scan = false
)
public class BookRecommendationAgent {

    private static final Logger logger = LoggerFactory.getLogger(BookRecommendationAgent.class);
    private final BookRecommendationProperties recommendationProperties;

    public BookRecommendationAgent(BookRecommendationProperties recommendationProperties) {
        this.recommendationProperties = recommendationProperties;
    }

    @Action
    @AchievesGoal(description = "Personalized book recommendations have been generated for the user")
    public BookRecommendationReport generatePersonalizedRecommendations(UserInput userInput,
                                                                        ReadingHistory readingHistory) {
        logger.info("Generating personalized recommendations for user input: {}", userInput.getContent());

        BuildableLlmOptions buildableLlmOptions = LlmOptions.fromCriteria(ModelSelectionCriteria.byName(recommendationProperties.getRecommendationLlm()));
        logger.info("Using BuildableLlmOptions: {}", buildableLlmOptions);

        return using(LlmOptions.fromCriteria(ModelSelectionCriteria.byName(recommendationProperties.getRecommendationLlm())),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory)
        )
                .createObject("""
                        Generate highly personalized book recommendations based on the user's reading history.
                        Analyze their reading patterns including:
                        - Preferred genres and authors
                        - Reading frequency and book completion rates
                        - Rating patterns and review sentiment
                        - Seasonal reading preferences
                        - Book length preferences and reading speed
                        Create diverse recommendations that balance familiar preferences with gentle exploration.
                        Include confidence scores and detailed reasoning for each recommendation.
                        Provide links to book review sites, author interviews, reading communities,
                        and literary analysis resources that enhance the reading experience.
                        User's specific request or preferences:
                        "%s"
                        """.trim().formatted(userInput.getContent()), BookRecommendationReport.class);
    }

    @Action
    @AchievesGoal(description = "Curated reading lists have been created for specific purposes")
    public BookRecommendationReport createThematicReadingLists(ReadingHistory readingHistory,
                                                               String theme, int timeframeWeeks) {
        logger.info("Creating thematic reading list for theme: '{}' with timeframe: {} weeks", theme, timeframeWeeks);

        return using(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory))
                .createObject("""
                        Create curated reading lists focused on the theme: '%s'
                        Design lists that can be completed within %d weeks.
                        Consider:
                        - Progressive difficulty or complexity
                        - Complementary perspectives on the theme
                        - Mix of classic and contemporary works
                        - Variety in book lengths and styles
                        - Connection between books in the list
                        Provide reading order suggestions and explain the thematic connections.
                        Include links to discussion guides, book club resources, and thematic analysis
                        that would enhance understanding of the chosen theme.
                        """.formatted(theme, timeframeWeeks), BookRecommendationReport.class);
    }

    @Action
    @AchievesGoal(description = "Reading horizons have been expanded with new genre recommendations")
    public BookRecommendationReport expandReadingHorizons(ReadingHistory readingHistory) {
        logger.info("Expanding reading horizons for user");

        return using(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory))
                .createObject("""
                        Analyze the user's reading comfort zone and recommend books to expand their horizons.
                        Identify:
                        - Genres they haven't explored or rarely read
                        - Authors from different cultural backgrounds
                        - Books that challenge their typical preferences
                        - Gateway books that ease transition to new genres
                        - Contemporary voices in familiar genres
                        Provide gentle introductions to new genres with bridge recommendations.
                        Explain why each genre expansion would benefit their reading journey.
                        Include links to genre guides, diverse author spotlights, and reading
                        challenge resources that support literary exploration.
                        """, BookRecommendationReport.class);
    }

    @Action
    @AchievesGoal(description = "Reading patterns and habits have been analyzed for optimization")
    public BookRecommendationReport analyzeReadingPatterns(ReadingHistory readingHistory) {
        logger.info("Analyzing reading patterns for user");

        return using(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory))
                .createObject("""
                        Perform comprehensive analysis of the user's reading patterns and habits.
                        Examine:
                        - Reading velocity and completion rates
                        - Seasonal and temporal reading preferences
                        - Genre cycling and mood-based selections
                        - Book abandonment patterns and reasons
                        - Rating consistency and preference evolution
                        - Series vs. standalone preferences
                        Provide insights into optimal reading strategies and timing.
                        Suggest improvements to reading habits and goal-setting approaches.
                        Include links to reading tracking tools, speed reading techniques,
                        and habit formation resources for better reading experiences.
                        """, BookRecommendationReport.class);
    }

    @Action
    @AchievesGoal(description = "Book recommendations based on current mood and life situation")
    public BookRecommendationReport recommendForCurrentMood(ReadingHistory readingHistory,
                                                            ReadingMood currentMood,
                                                            String lifeContext) {
        logger.info("Generating recommendations for mood: {} and life context: {}", currentMood, lifeContext);

        return using(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory))
                .createObject("""
                        Recommend books perfectly suited for the user's current mood and life situation.
                        Current mood: %s
                        Life context: %s
                        Consider:
                        - Emotional resonance with current state
                        - Appropriate book length for available time
                        - Complexity level matching mental bandwidth
                        - Themes that provide comfort, inspiration, or escape
                        - Books that complement or contrast current experiences
                        Provide immediate reading options and longer-term suggestions.
                        Include links to mood-based reading guides, bibliotherapy resources,
                        and emotional wellness through literature materials.
                        """.formatted(currentMood, lifeContext), BookRecommendationReport.class);
    }

    @Action
    @AchievesGoal(description = "Book recommendations similar to a specific book have been generated")
    public BookRecommendationReport recommendSimilarBooks(ReadingHistory readingHistory,
                                                          String bookTitle,
                                                          String author) {
        logger.info("Generating recommendations similar to book: '{}' by {}", bookTitle, author);

        return using(LlmOptions.fromModel(recommendationProperties.getRecommendationLlm()),
                Collections.emptySet(),
                List.of(),
                List.of(readingHistory))
                .createObject("""
                        Recommend books similar to '%s' by %s.
                        Consider:
                        - Similar themes, writing style, and narrative structure
                        - Books by the same author that the user hasn't read
                        - Books that influenced this author or were influenced by them
                        - Contemporary works in the same subgenre
                        - Classic works that share similar elements
                        Explain the specific similarities between each recommendation and the reference book.
                        Include a mix of well-known and lesser-known titles to provide both familiar and discovery options.
                        Provide links to literary analyses comparing these works, author interviews discussing influences,
                        and reading guides that highlight the connections between the recommended books.
                        """.formatted(bookTitle, author), BookRecommendationReport.class);
    }
}