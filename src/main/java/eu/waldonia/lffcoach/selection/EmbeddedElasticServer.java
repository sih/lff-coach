package eu.waldonia.lffcoach.selection;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.client.Requests.createIndexRequest;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author sih
 */
public class EmbeddedElasticServer {

    private static final String DEFAULT_DATA_LOCATION = "./target/data";
    private static final String HOME_DIR = ".";

    private static EmbeddedElasticServer instance;

    private Node node;


    public static EmbeddedElasticServer getInstance() {
        if (null == instance) {
            instance = new EmbeddedElasticServer();
        }
        return instance;
    }

    public EmbeddedElasticServer() {
        this(DEFAULT_DATA_LOCATION);
    }

    public EmbeddedElasticServer(final String dataDirectory) {

        Settings.Builder settings =
                Settings
                        .settingsBuilder()
                        .put("http.enabled",false)
                        .put("path.data", dataDirectory)
                        .put("path.home", HOME_DIR)
                        ;

        node = nodeBuilder()
                .local(true)
                .settings(settings)
                .node();
    }

    public Client client() {
        return node.client();
    }

    public void shutdown() {
        node.close();
    }


    /**
     * Create an index
     * @param index The index to create
     */

    public void createIndex(String index) {
        CreateIndexResponse createResponse =
                client()
                        .admin()
                        .indices()
                        .create(createIndexRequest(index))
                        .actionGet();
    }

    /**
     * Refresh the index
     * @param index The index to refresh
     */
    public void refreshIndex(String index) {
        client()
                .admin()
                .indices()
                .prepareRefresh(index)
                .get();
    }

    /**
     * Delete an index
     * @param index The index to delete
     */
    public void deleteIndex(String index) {
        client()
                .admin()
                .indices()
                .prepareDelete(index)
                .get();
    }


}
