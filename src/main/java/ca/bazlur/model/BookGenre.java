package ca.bazlur.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription("Categories or genres of books used for classification and recommendation")
public enum BookGenre {
    FICTION, NON_FICTION, MYSTERY, THRILLER, ROMANCE, SCIENCE_FICTION,
    FANTASY, BIOGRAPHY, HISTORY, SELF_HELP, BUSINESS, HEALTH,
    TRAVEL, COOKING, ART, POETRY, DRAMA, CHILDREN, YOUNG_ADULT, HISTORICAL_FICTION,PHILOSOPHY
}
