# Embabel Agent Demo - Book Recommendation System

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

## Overview

This project demonstrates the capabilities of the Embabel Agent framework by implementing a sophisticated book recommendation system. The system analyzes a user's preferences to generate personalized book recommendations, curated reading lists, and reading insights.

## Features

- **Personalized Book Recommendations**: Generate tailored book recommendations based on user preferences and requests.
- **Thematic Reading Lists**: Create curated reading lists focused on specific themes or topics.
- **Reading Horizon Expansion**: Suggest books that expand a user's reading comfort zone into new genres and styles.
- **Reading Pattern Analysis**: Analyze reading habits and patterns to provide insights and optimization suggestions.
- **Mood-Based Recommendations**: Recommend books based on current mood and life situation.
- **Similar Books Finder**: Find books similar to a specific book you enjoyed.
- 
## Architecture

The application is built using:

- **Spring Boot**: For application framework and dependency injection
- **Embabel Agent API**: For AI-powered recommendation generation
- **Java Records**: For immutable data models
- **Thymeleaf**: For server-side HTML templating
- **Bootstrap**: For responsive UI design
- **Command Line Interface**: For demonstration purposes (optional)

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Access to Embabel repository

### Running the Application

1. Clone the repository
2. Build the project:
   ```
   mvn clean package
   ```
3. Run the application in web mode (default):
   ```
   export SPRING_PROFILES_ACTIVE=shell,starwars,docker-desktop
   java -jar target/embabel-agent-demo-1.0-SNAPSHOT.jar
   ```

### Screenshots

(Screenshots will be added here)

## Configuration

The application can be configured through application properties:

```properties
# Server configuration
server.port=8080

# Book recommendation properties
book.recommendation.recommendation-llm=gpt-4o
```

## Project Structure

- `ca.bazlur.model`: Contains domain models for book recommendations
- `ca.bazlur`: Contains the main application code and recommendation engine

## Example Usage

### Programmatic API

```java
// Create user input with preferences
UserInput userInput = new UserInput("I'm looking for science fiction books similar to Project Hail Mary", Instant.now());

// Extract user preferences from input
UserPreferences userPreferences = engine.extractUserPreferences(userInput);

// Generate personalized recommendations
BookRecommendationReport report = engine.generatePersonalizedRecommendations(userInput, userPreferences);
```

## Future Enhancements

- Integration with external book APIs for real-time data
- User accounts and persistent preferences
- Social features for sharing recommendations
- Mobile application
- Advanced search and filtering options
- Reading progress tracking

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
