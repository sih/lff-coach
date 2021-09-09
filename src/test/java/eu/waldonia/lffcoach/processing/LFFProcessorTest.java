package eu.waldonia.lffcoach.processing;

import static org.junit.Assert.assertNotNull;

import eu.waldonia.lffcoach.model.Screening;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sih
 */
public class LFFProcessorTest {

    private LFFProcessor lffProcessor;

    @Before
    public void setUp() {
        lffProcessor = new LFFProcessor();
    }

    @Test
    public void processShouldProduceCompleteListings() {

        List<Screening> screenings = lffProcessor.process(LFFProcessor.PDF_LISTING_FILE_LOCATION);
        for (Screening s: screenings) {
            assertNotNull(s);
            assertNotNull(s.getCinema());
            assertNotNull(s.getFilm());
            assertNotNull(s.getVenueDate());
            assertNotNull(s.getVenueTime());
        }

    }

}