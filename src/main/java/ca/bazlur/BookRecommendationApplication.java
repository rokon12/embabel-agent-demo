package ca.bazlur;

import ca.bazlur.agent.BookRecommendationAgent;
import ca.bazlur.model.BookRecommendationReport;
import ca.bazlur.model.ReadingHistory;
import ca.bazlur.model.ReadingMood;
import com.embabel.agent.domain.io.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.client.autoconfigure.McpClientAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.Instant;


@SpringBootApplication(scanBasePackages = {
        "ca.bazlur",
        "com.embabel.agent",
}, exclude = {McpClientAutoConfiguration.class})
@EnableConfigurationProperties(BookRecommendationProperties.class)
public class BookRecommendationApplication {

    private static final Logger logger = LoggerFactory.getLogger(BookRecommendationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookRecommendationApplication.class, args);
        logger.info("Book Recommendation Engine started successfully");
    }
}
