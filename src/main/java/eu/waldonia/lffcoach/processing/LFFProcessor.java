package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author sih */
@Slf4j
class LFFProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(LFFProcessor.class);
  private byte[] calendarContent;

  LFFProcessor(byte[] calendarContent) {
    this.calendarContent = calendarContent;
  }

  LFFProcessor() {
    Properties props = new Properties();
    try {
      String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      String propsPath = String.join("", rootPath, "processor.properties");
      props.load(new FileInputStream(propsPath));
      String calendarUrl = props.getProperty("downloadLocation");
      CalendarDownloader downloader = new CalendarDownloader();
      calendarContent = downloader.download(calendarUrl);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Screening> process() {
    List<Screening> screenings = null;
    try {
      PDFPipeline pipeline = new PDFPipeline();
      StringWriter writer = pipeline.extract(calendarContent);
      screenings = pipeline.transform(writer);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }

    return screenings;
  }

  public static void main(String[] args) throws Exception {

    LFFProcessor processor = new LFFProcessor();
    List<Screening> screenings = processor.process();
    //    Set<String> films =
    // screenings.stream().map(Screening::getFilm).collect(Collectors.toSet());
    //    for (String film : films) {
    //      LOGGER.info(film);
    //    }
    for (Screening screening : screenings) {
      LOGGER.info(screening.toString());
    }
  }
}
