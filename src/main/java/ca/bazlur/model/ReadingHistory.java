package ca.bazlur.model;

import com.embabel.agent.domain.library.HasContent;
import com.embabel.common.ai.prompt.PromptContribution;
import com.embabel.common.ai.prompt.PromptContributionLocation;
import com.embabel.common.ai.prompt.PromptContributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a user's reading history, including books read, ratings, and reading patterns.
 *
 * @param averageReadingSpeed pages per hour
 */
public record ReadingHistory(String userId, List<ReadBook> booksRead, Map<BookGenre, Integer> genrePreferences,
                             List<String> favoriteAuthors, Map<String, Double> ratingHistory, LocalDate lastUpdated,
                             double averageReadingSpeed,
                             int averageBooksPerMonth) implements HasContent, PromptContributor {

    public String getRole() {
        return "reading_history";
    }

    @NotNull
    @Override
    public PromptContributionLocation getPromptContributionLocation() {
        return PromptContributionLocation.END;
    }

    @NotNull
    @Override
    public PromptContribution promptContribution() {
        return new PromptContribution(getContent(),  getPromptContributionLocation(), getRole());
    }

    @NotNull
    @Override
    public String contribution() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reading History Analysis:\n");
        sb.append("User ID: ").append(userId).append("\n");
        sb.append("Total Books Read: ").append(booksRead.size()).append("\n");
        sb.append("Average Reading Speed: ").append(averageReadingSpeed).append(" pages/hour\n");
        sb.append("Average Books Per Month: ").append(averageBooksPerMonth).append("\n");
        sb.append("Last Updated: ").append(lastUpdated).append("\n\n");

        // Genre preferences analysis
        sb.append("Genre Preferences (ranked by frequency):\n");
        genrePreferences.entrySet().stream()
                .sorted(Map.Entry.<BookGenre, Integer>comparingByValue().reversed())
                .forEach(entry -> sb.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" books\n"));

        sb.append("\nFavorite Authors:\n");
        favoriteAuthors.forEach(author -> sb.append("- ").append(author).append("\n"));

        // Recent reading activity
        sb.append("\nRecent Books (last 5):\n");
        booksRead.stream()
                .sorted((a, b) -> b.dateRead().compareTo(a.dateRead()))
                .limit(5)
                .forEach(book -> sb.append("- ").append(book.title()).append(" by ").append(book.author())
                        .append(" (").append(book.userRating()).append("/5.0)\n"));

        // Reading patterns
        double avgRating = ratingHistory.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        sb.append("\nReading Patterns:\n");
        sb.append("- Average Rating Given: ").append(String.format("%.1f", avgRating)).append("/5.0\n");

        long completedBooks = booksRead.stream().mapToLong(book -> book.completed() ? 1 : 0).sum();
        double completionRate = booksRead.isEmpty() ? 0.0 : (double) completedBooks / booksRead.size() * 100;
        sb.append("- Completion Rate: ").append(String.format("%.1f", completionRate)).append("%\n");

        return sb.toString();

    }

    /**
     * Represents a book that has been read by the user.
     */
    public record ReadBook(String title, String author, BookGenre genre, String isbn, int pageCount, double userRating,
                           LocalDate dateRead, boolean completed, String userNotes) {
    }

    /**
     * Creates a sample reading history for demonstration purposes.
     */
    public static ReadingHistory createSampleHistory() {
        List<ReadBook> books = new ArrayList<>();
        books.add(new ReadBook(
                "The Midnight Library",
                "Matt Haig",
                BookGenre.FICTION,
                "9780525559474",
                304,
                4.5,
                LocalDate.now().minusMonths(2),
                true,
                "Loved the concept of infinite possibilities."
        ));
        books.add(new ReadBook(
                "Educated",
                "Tara Westover",
                BookGenre.BIOGRAPHY,
                "9780399590504",
                334,
                5.0,
                LocalDate.now().minusMonths(4),
                true,
                "Inspiring story of self-education."
        ));
        books.add(new ReadBook(
                "Project Hail Mary",
                "Andy Weir",
                BookGenre.SCIENCE_FICTION,
                "9780593135204",
                496,
                4.8,
                LocalDate.now().minusMonths(1),
                true,
                "Excellent sci-fi with great scientific details."
        ));
        books.add(new ReadBook(
                "Atomic Habits",
                "James Clear",
                BookGenre.SELF_HELP,
                "9780735211292",
                320,
                4.2,
                LocalDate.now().minusMonths(3),
                true,
                "Practical advice on habit formation."
        ));
        books.add(new ReadBook(
                "The Silent Patient",
                "Alex Michaelides",
                BookGenre.THRILLER,
                "9781250301697",
                336,
                3.9,
                LocalDate.now().minusMonths(5),
                true,
                "Surprising twist at the end."
        ));

        Map<BookGenre, Integer> genrePrefs = new HashMap<>();
        genrePrefs.put(BookGenre.FICTION, 12);
        genrePrefs.put(BookGenre.SCIENCE_FICTION, 8);
        genrePrefs.put(BookGenre.THRILLER, 7);
        genrePrefs.put(BookGenre.BIOGRAPHY, 5);
        genrePrefs.put(BookGenre.SELF_HELP, 3);

        List<String> favoriteAuthors = List.of("Andy Weir", "Matt Haig", "Tara Westover", "James Clear");

        Map<String, Double> ratings = new HashMap<>();
        ratings.put("The Midnight Library", 4.5);
        ratings.put("Educated", 5.0);
        ratings.put("Project Hail Mary", 4.8);
        ratings.put("Atomic Habits", 4.2);
        ratings.put("The Silent Patient", 3.9);

        return new ReadingHistory(
                "user123",
                books,
                genrePrefs,
                favoriteAuthors,
                ratings,
                LocalDate.now(),
                45.0, // pages per hour
                3     // books per month
        );
    }

    @Override
    public String getContent() {
        return String.format(
                "Reading History for User: %s\n" +
                        "Books Read: %d\n" +
                        "Favorite Genres: %s\n" +
                        "Favorite Authors: %s\n" +
                        "Average Reading Speed: %.1f pages/hour\n" +
                        "Average Books Per Month: %d\n" +
                        "Last Updated: %s",
                userId, booksRead.size(),
                genrePreferences.keySet().stream().limit(3).toList(),
                String.join(", ", favoriteAuthors),
                averageReadingSpeed,
                averageBooksPerMonth,
                lastUpdated
        );
    }
}