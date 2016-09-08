package eu.waldonia.lffcoach.selection;

import eu.waldonia.lffcoach.model.Screening;
import eu.waldonia.lffcoach.processing.LFFProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sih
 */
public class FilmSelector {

    private static final String SCREENINGS_INDEX = "i_screning";


    /**
     * This will start up the server and index files at the supplied location
     */
    public FilmSelector() {
        this(LFFProcessor.DEFAULT_SCREENINGS_LOCATION);
    }

    public FilmSelector(final String screeningsDirectory) {
        EmbeddedElasticServer server = new EmbeddedElasticServer();

        server.createIndex(SCREENINGS_INDEX);

    }

    /**
     * Pass a list of film titles or near film titles and find matches
     * @param films
     * @return
     */
    List<Screening> match(String ... films) {
        List<Screening> matches = new ArrayList<>();



        return matches;
    }

}
