package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Cleans up any misspellings or abbreviations
 * @author sih
 */
public class CinemaNormalizer extends ScreeningNormalizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaNormalizer.class);


    private static final String CINEMA_ALIAS_FILE = "cinema.alias";
    private Properties aliases = new Properties();
    public CinemaNormalizer() {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(CINEMA_ALIAS_FILE);
        try {
            aliases.load(is);
        }
        catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }
    }

    void normalize(List<Screening> screenings) {
        screenings
                .stream()
                .filter(s -> aliases.containsKey(s.getCinema()))
                .forEach(s -> s.setCinema(aliases.getProperty(s.getCinema())));
    }
}
