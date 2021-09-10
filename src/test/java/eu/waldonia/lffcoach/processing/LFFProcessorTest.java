package eu.waldonia.lffcoach.processing;

import static org.junit.Assert.assertNotNull;

import eu.waldonia.lffcoach.model.Screening;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author sih */
public class LFFProcessorTest {
  private static final String LFF_2016_PDF = "./src/test/resources/lff-2016.pdf";
  private static final String LFF_2017_PDF = "./src/test/resources/lff-2017.pdf";
  private static final String LFF_2021_PDF = "./src/test/resources/lff-2021.pdf";

  private LFFProcessor lffProcessor;
  private byte[] content;

  @Before
  public void setUp() throws IOException {
    content = Files.readAllBytes(Paths.get(LFF_2021_PDF));
    lffProcessor = new LFFProcessor(content);
  }

  @Test
  public void processShouldProduceCompleteListings() {

    List<Screening> screenings = lffProcessor.process();
    for (Screening s : screenings) {
      assertNotNull(s);
      assertNotNull(s.getCinema());
      assertNotNull(s.getFilm());
      assertNotNull(s.getVenueDate());
      assertNotNull(s.getVenueTime());
    }
  }
}
