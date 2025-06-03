package ca.bazlur.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription("Different moods or emotional states that influence reading preferences")
public enum ReadingMood {
    ESCAPIST, EDUCATIONAL, INSPIRATIONAL, RELAXING, CHALLENGING,
    ENTERTAINING, THOUGHT_PROVOKING, EMOTIONAL, ADVENTUROUS
}
