package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;
import eu.waldonia.lffcoach.selection.EmbeddedElasticServer;
import org.elasticsearch.action.index.IndexResponse;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author sih
 */
public class FilmIndexer {

    public static final String SCREENINGS_INDEX = "i_screning";
    public static final String FILM_TYPE = "film";

    public static final String FIELD_FILM = "film";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_TIME= "time";
    public static final String FIELD_CINEMA = "cinema";
    public static final String FIELD_SYNOPSIS = "synopsis";

    private static EmbeddedElasticServer server;

    /**
     * This will start up the server and index files at the supplied location
     */
    public FilmIndexer() {
        this(LFFProcessor.DEFAULT_SCREENINGS_LOCATION);
    }

    public FilmIndexer(final String screeningsDirectory) {
        server = EmbeddedElasticServer.getInstance();
        server.createIndex(SCREENINGS_INDEX);
    }


    public void indexFilm(Screening screening) throws IOException {

        IndexResponse response = server.client().prepareIndex(
                FilmIndexer.SCREENINGS_INDEX,
                FilmIndexer.FILM_TYPE,
                String.valueOf(screening.hashCode()))
                .setSource(jsonBuilder()
                        .startObject()
                        .field(FIELD_FILM, screening.getFilm())
                        .field(FIELD_DATE, screening.getVenueDate())
                        .field(FIELD_TIME, screening.getVenueTime())
                        .field(FIELD_CINEMA, screening.getCinema())
                        .field(FIELD_SYNOPSIS, screening.getSynopsis())
                        .endObject()
                )
                .get();
    }
}
