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

    private static final String DATE_LINE = "^(SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY)\\s[0-9]{1,2}\\s(OCTOBER)\\s?$";
    private static final String BAD_LINE = "OF ROCK";
    private static final String CINEMA_LINE = "^[A-Z]+((\\s[A-Z0-9]+)*)?$";
    private static final String FILM_LINE = "^[0-9]{2}:[0-9]{2}\\s[A-Z0-9]+((\\s[A-Z0-9]+)*)?$";


    private static final String END_STRING = "This film will be screened in 3D";

    StringWriter readFile(File pdfFile) throws IOException {

        PDDocument doc = PDDocument.load(pdfFile);

        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(1);
        stripper.setEndPage(13);
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
                String line = lines.nextLine();

                LOGGER.debug(line);
                if (line.matches(DATE_LINE)) {
                    date = line;
                    LOGGER.debug(date);
                }
                else if (line.matches(CINEMA_LINE) && !line.equals(BAD_LINE)) {
                    cinema = line;
                    LOGGER.debug(cinema);
                }
                else if (line.matches(FILM_LINE)) {

                    if (screening != null) {
                        screening.setSynopsis(synopsis.toString().trim());
                        screenings.add(screening);
                    }

                    int firstSpace = line.indexOf(' ');
                    time = line.substring(0,firstSpace).trim();
                    film = line.substring(firstSpace).trim();

                    // set up  the fixed lines
                    screening = new Screening();
                    synopsis = new StringBuffer();
                    screening.setCinema(cinema);
                    screening.setFilm(film);
                    screening.setVenueDate(date);
                    screening.setVenueTime(time);

                }
                // end of useful info
                else if (line.equals(END_STRING)) {
                    ended = true;
                }
                // synopsis line
                else {
                    synopsis.append(line);
                }
            }


        }

        return screenings;
    }



}
