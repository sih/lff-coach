package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sih
 */
public class LFFProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LFFProcessor.class);
    // static final String PDF_LISTING_FILE_LOCATION = "./src/main/resources/lff-2016.pdf";
    // static final String PDF_LISTING_FILE_LOCATION = "./src/main/resources/lff-2017.pdf";
    static final String PDF_LISTING_FILE_LOCATION = "./src/main/resources/lff-2021.pdf";


    public List<Screening> process(String fileLocation) {
        List<Screening> screenings = null;
        try {
            PDFExtractor extractor = new PDFExtractor();
            StringWriter writer = extractor.readFile(new File(fileLocation));
            screenings = extractor.transform(writer);
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
        }

        return screenings;
    }

    public static void main(String[] args) throws Exception {

        LFFProcessor processor = new LFFProcessor();
        List<Screening> screenings = processor.process(PDF_LISTING_FILE_LOCATION);
        Set<String> films = screenings.stream().map(Screening::getFilm).collect(Collectors.toSet());
        for (String film: films) {
            LOGGER.info(film);
        }
    }
}
