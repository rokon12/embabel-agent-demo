package ca.bazlur;

import ca.bazlur.model.ReadingHistory;
import org.springframework.stereotype.Component;

@Component
public class ReadingHistoryRepository {
    //TODO Currently using mock data, will figure out later.
    public ReadingHistory findReadingHistory() {

        return ReadingHistory.createSampleHistory();
    }
}
