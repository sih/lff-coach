package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

/**
 * @author sih
 */
public class LFFProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LFFProcessor.class);
    static final String PDF_LISTING_FILE_LOCATION = "./src/main/resources/lff-2016.pdf";

    public static final String DEFAULT_SCREENINGS_LOCATION = "./data";


    public List<Screening> process(String fileLocation) {
        List<Screening> screenings = null;
        try {

            PDFExtractor extractor = new PDFExtractor();
            CinemaNormalizer cinemaNormalizer = new CinemaNormalizer();
            DateNormalizer dateNormalizer = new DateNormalizer();
            FilmIndexer indexer = new FilmIndexer();

            StringWriter writer = extractor.readFile(new File(fileLocation));
            screenings = extractor.transform(writer);
            cinemaNormalizer.normalize(screenings);
            dateNormalizer.normalize(screenings);
            // add to the search index
            for (Screening screening: screenings) {
                indexer.indexFilm(screening);
            }


        }
        catch(Exception e) {
            LOGGER.error(e.getMessage());
        }

        return screenings;
    }

    public static void main(String[] args) {
        LFFProcessor processor = new LFFProcessor();
        List<Screening> screenings = processor.process(PDF_LISTING_FILE_LOCATION);
        for (Screening s: screenings) {
            LOGGER.info(s.toJson());
        }
    }
}
