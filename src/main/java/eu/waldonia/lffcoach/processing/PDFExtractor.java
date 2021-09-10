package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sih
 */
public class PDFExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PDFExtractor.class);

    private int startPage;
    private int endPage;
    private String[] cinemas;
    private String dateRegex;
    private String filmRegex;
    private String endProcessingString;

    /**
     * Set up the regex and string matchers
     */
    PDFExtractor() {
        Properties props = new Properties();
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String propsPath = String.join("", rootPath, "extractor.properties");
            props.load(new FileInputStream(propsPath));
            startPage = Integer.parseInt(props.getProperty("startPage"));
            endPage = Integer.parseInt(props.getProperty("endPage"));
            cinemas = props.getProperty("cinemas").split(",");
            dateRegex = props.getProperty("dateRegex");
            filmRegex = props.getProperty("filmRegex");
            endProcessingString = props.getProperty("endProcessingString");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    StringWriter readFile(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(startPage);
        stripper.setEndPage(endPage);
        StringWriter sw = new StringWriter();
        stripper.writeText(doc, sw);
        return sw;
    }


    List<Screening> transform(StringWriter writer) {
        List<Screening> screenings = new ArrayList<Screening>();

        if (writer != null) {
            String date = null;
            String cinema = null;
            String film = null;
            String time = null;
            boolean ended = false;
            Screening screening = null;

            Scanner lines = new Scanner(writer.toString());
            while (lines.hasNext() && !ended) {
                String line = lines.nextLine().trim();
                LOGGER.debug(line);
                if (line.matches(dateRegex)) {
                    date = line;
                    LOGGER.debug(date);
                }
                else if (isCinemaLine(line)) {
                    cinema = line;
                    LOGGER.debug(cinema);
                }
                else if (line.matches(filmRegex)) {
                    if (screening != null) {
                        screenings.add(screening);
                    }
                    int firstSpace = line.indexOf(' ');
                    time = line.substring(0,firstSpace).trim();
                    film = line.substring(firstSpace).trim();
                    // set up  the fixed lines
                    screening = new Screening();
                    screening.setCinema(cinema);
                    screening.setFilm(film);
                    screening.setVenueDate(date);
                    screening.setVenueTime(time);
                }
                // end of useful info
                else if (line.equals(endProcessingString)) {
                    ended = true;
                }
            }
        }
        return screenings;
    }

    private boolean isCinemaLine(String line) {
        for (String cinema : cinemas) {
            if (line.contains(cinema)) {
                return true;
            }
        }
        return false;
    }

}
