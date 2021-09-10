package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author sih
 */
public class PDFExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFExtractor.class);
    private static final String[] CINEMAS = {"BFI","CURZON","SOUTHBANK","ODEON","INSTITUTE"};
    private static final String DATE_LINE = "^(SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY)\\s[0-9]{1,2}\\s(OCTOBER)\\s?$";
    private static final String FILM_LINE = "^[0-9]{2}:[0-9]{2}\\s[A-Z0-9’]+((\\s[A-Z0-9’]+)*)?$";


    private static final String END_STRING = "This film will be screened in 3D";

    StringWriter readFile(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(3);
        stripper.setEndPage(8);
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
            StringBuffer synopsis = null;

            Scanner lines = new Scanner(writer.toString());
            while (lines.hasNext() && !ended) {
                String line = lines.nextLine().trim();
                LOGGER.debug(line);
                if (line.matches(DATE_LINE)) {
                    date = line;
                    LOGGER.debug(date);
                }
                else if (isCinemaLine(line)) {
                    cinema = line;
                    LOGGER.debug(cinema);
                }
                else if (line.matches(FILM_LINE)) {
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
                else if (line.equals(END_STRING)) {
                    ended = true;
                }
            }


        }

        return screenings;
    }

    private boolean isCinemaLine(String line) {
        for (String cinema : CINEMAS) {
            if (line.contains(cinema)) {
                return true;
            }
        }
        return false;
    }


}
