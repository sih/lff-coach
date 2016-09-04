package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import org.apache.commons.lang3.text.WordUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Turns the venue's dates in to ISO-8601 date times
 * @author sih
 */
public class DateNormalizer extends ScreeningNormalizer {

    private static final String VENUE_DATETIME_PATTERN = "EEEE d MMMM yyyy HH:mm";
    private static final String YEAR_PATTERN = "yyyy";


    @Override
    public void normalize(List<Screening> screenings) {

        screenings
                .stream()
                .forEach(s -> {
                    s.setIsoDate(this.toIsoDate(s.getVenueDate(),s.getVenueTime()));
                });

    }

    private String toIsoDate(String venueDate, String venueTime) {

        // TODO make fewer assumptions on the format
        LocalDate now = LocalDate.now();
        String thisYear = now.format(DateTimeFormatter.ofPattern(YEAR_PATTERN));

        String venueDateTime = WordUtils.capitalizeFully(
                                String
                                .join(" ",venueDate,thisYear,venueTime)
                                .toLowerCase()
                                );

        DateTimeFormatter venueFormatter = DateTimeFormatter.ofPattern(VENUE_DATETIME_PATTERN);

        LocalDateTime venueDT = LocalDateTime.parse(venueDateTime, venueFormatter);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String isoDate = venueDT.format(isoFormatter);


        return isoDate;
    }



}
