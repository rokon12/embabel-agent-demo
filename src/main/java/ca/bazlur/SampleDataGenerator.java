package ca.bazlur;

import ca.bazlur.model.*;
import com.embabel.agent.domain.library.InternetResource;

import java.time.LocalDate;
import java.util.*;

/**
 * Utility class for generating sample data for demonstration purposes.
 */
public class SampleDataGenerator {

    /**
     * Generates a sample book recommendation report.
     *
     * @return A sample BookRecommendationReport
     */
    public static BookRecommendationReport generateSampleReport() {
        return new BookRecommendationReport(
                "Based on your reading history, you show a strong preference for thought-provoking fiction and science fiction with complex characters. You also enjoy biographies of remarkable individuals who overcome significant challenges.",
                generateSampleRecommendations(),
                generateGenreAffinityScores(),
                generateCuratedReadingLists(),
                generateReadingPatterns(),
                generateAuthorRecommendations(),
                generateExpandHorizonsGenres(),
                LocalDate.now().plusMonths(1),
                generateLinks()
        );
    }

    private static List<BookRecommendation> generateSampleRecommendations() {
        return List.of(
                new BookRecommendation(
                        "Klara and the Sun",
                        "Kazuo Ishiguro",
                        BookGenre.SCIENCE_FICTION,
                        "9780571364879",
                        4.7,
                        "A story of an Artificial Friend with outstanding observational qualities, who watches customers come and go, hoping that someone will choose her.",
                        "Based on your enjoyment of thought-provoking science fiction that explores human nature, this novel offers a unique perspective on humanity through the eyes of an AI.",
                        RecommendationConfidence.HIGH,
                        List.of("Project Hail Mary", "The Midnight Library"),
                        8
                ),
                new BookRecommendation(
                        "The Lincoln Highway",
                        "Amor Towles",
                        BookGenre.FICTION,
                        "9780735222359",
                        4.5,
                        "In June 1954, eighteen-year-old Emmett Watson is driven home to Nebraska by the warden of the juvenile work farm where he has just served fifteen months for involuntary manslaughter.",
                        "Your reading history shows you appreciate character-driven narratives with unexpected journeys, which this novel delivers with exceptional prose.",
                        RecommendationConfidence.VERY_HIGH,
                        List.of("The Midnight Library"),
                        12
                ),
                new BookRecommendation(
                        "The Code Breaker",
                        "Walter Isaacson",
                        BookGenre.BIOGRAPHY,
                        "9781982115852",
                        4.6,
                        "The bestselling author of Leonardo da Vinci and Steve Jobs returns with a gripping account of how Nobel Prize winner Jennifer Doudna and her colleagues launched a revolution that will allow us to cure diseases, fend off viruses, and have healthier babies.",
                        "Given your interest in biographies of innovative thinkers and scientific advancements, this book about CRISPR gene-editing technology should be fascinating.",
                        RecommendationConfidence.HIGH,
                        List.of("Educated"),
                        10
                )
        );
    }

    private static Map<BookGenre, Double> generateGenreAffinityScores() {
        Map<BookGenre, Double> scores = new HashMap<>();
        scores.put(BookGenre.FICTION, 0.85);
        scores.put(BookGenre.SCIENCE_FICTION, 0.78);
        scores.put(BookGenre.BIOGRAPHY, 0.72);
        scores.put(BookGenre.THRILLER, 0.65);
        scores.put(BookGenre.SELF_HELP, 0.45);
        return scores;
    }

    private static List<ReadingList> generateCuratedReadingLists() {
        return List.of(
                new ReadingList(
                        "Exploring Consciousness",
                        "Books that delve into the nature of consciousness, reality, and perception.",
                        List.of(
                                new BookRecommendation(
                                        "Consciousness Explained",
                                        "Daniel C. Dennett",
                                        BookGenre.NON_FICTION,
                                        "9780316180665",
                                        4.3,
                                        "A comprehensive exploration of consciousness from a philosophical and scientific perspective.",
                                        "This foundational text will give you a solid grounding in consciousness studies.",
                                        RecommendationConfidence.MEDIUM,
                                        List.of(),
                                        14
                                ),
                                new BookRecommendation(
                                        "The Mind's I",
                                        "Douglas Hofstadter & Daniel C. Dennett",
                                        BookGenre.NON_FICTION,
                                        "9780465030910",
                                        4.5,
                                        "Fantasies and reflections on self and soul.",
                                        "A collection of essays and stories that explore the nature of self.",
                                        RecommendationConfidence.HIGH,
                                        List.of(),
                                        12
                                )
                        ),
                        ReadingMood.THOUGHT_PROVOKING,
                        6
                ),
                new ReadingList(
                        "Space Exploration Adventures",
                        "Thrilling stories of space exploration and discovery.",
                        List.of(
                                new BookRecommendation(
                                        "The Martian",
                                        "Andy Weir",
                                        BookGenre.SCIENCE_FICTION,
                                        "9780553418026",
                                        4.8,
                                        "An astronaut becomes stranded on Mars and must use his wits to survive.",
                                        "A scientifically accurate and thrilling survival story.",
                                        RecommendationConfidence.VERY_HIGH,
                                        List.of("Project Hail Mary"),
                                        8
                                ),
                                new BookRecommendation(
                                        "Seveneves",
                                        "Neal Stephenson",
                                        BookGenre.SCIENCE_FICTION,
                                        "9780062190376",
                                        4.2,
                                        "When the moon explodes, humanity has two years to prevent extinction.",
                                        "An epic tale of human ingenuity and survival.",
                                        RecommendationConfidence.HIGH,
                                        List.of(),
                                        20
                                )
                        ),
                        ReadingMood.ADVENTUROUS,
                        4
                )
        );
    }

    private static List<String> generateReadingPatterns() {
        return List.of(
                "You tend to read more during winter months",
                "You typically finish books within 7-10 days",
                "You often alternate between fiction and non-fiction",
                "You prefer character-driven narratives over plot-driven ones",
                "You rarely abandon books once started"
        );
    }

    private static Map<String, String> generateAuthorRecommendations() {
        Map<String, String> recommendations = new HashMap<>();
        recommendations.put("Ted Chiang", "For fans of Andy Weir who enjoy philosophical science fiction");
        recommendations.put("Madeline Miller", "For readers who appreciate character depth and emotional resonance");
        recommendations.put("Erik Larson", "For those who enjoy meticulously researched historical narratives");
        return recommendations;
    }

    private static List<String> generateExpandHorizonsGenres() {
        return List.of("POETRY", "DRAMA", "HISTORY", "COOKING");
    }

    private static List<InternetResource> generateLinks() {
        return List.of(
                new InternetResource("Goodreads", "https://www.goodreads.com"),
                new InternetResource("Literary Hub", "https://lithub.com"),
                new InternetResource("Book Riot", "https://bookriot.com"),
                new InternetResource("The StoryGraph", "https://thestorygraph.com")
        );
    }
}
