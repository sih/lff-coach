package eu.waldonia.lffcoach.processing;


import eu.waldonia.lffcoach.model.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** @author sih */
public class LFFProcessorTest {
  private static final String LFF_2016_PDF = "./src/test/resources/lff-2016.pdf";
  private static final String LFF_2017_PDF = "./src/test/resources/lff-2017.pdf";
  private static final String LFF_2021_PDF = "./src/test/resources/lff-2021.pdf";
  private static final String LFF_2023_PDF = "./src/test/resources/lff-2023.pdf";

  private LFFProcessor lffProcessor;
  private byte[] content;

  @BeforeEach
  void setUp() throws IOException {
    content = Files.readAllBytes(Paths.get(LFF_2021_PDF));
    lffProcessor = new LFFProcessor(content);
  }

  @Test
  public void processShouldProduceCompleteListings() {

    List<Screening> screenings = lffProcessor.process();
    for (Screening s : screenings) {
      assertThat(s).isNotNull();
      assertThat(s.getCinema()).isNotNull();
      assertThat(s.getFilm()).isNotNull();
      assertThat(s.getVenueDate()).isNotNull();
      assertThat(s.getVenueTime()).isNotNull();
    }
  }
}
