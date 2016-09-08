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

    private static EmbeddedElasticServer instance;

    private Node node;


    public static EmbeddedElasticServer getInstance() {
        if (null == instance) {
            instance = new EmbeddedElasticServer();
        }
        return instance;
    }

    EmbeddedElasticServer() {
        this(DEFAULT_DATA_LOCATION);
    }

    EmbeddedElasticServer(final String dataDirectory) {

        Settings.Builder settings =
                Settings
                        .settingsBuilder()
                        .put("http.enabled",false)
                        .put("path.data", dataDirectory)
                        ;

        node = nodeBuilder()
                .local(true)
                .settings(settings)
                .node();
    }

    Client getClient() {
        return node.client();
    }

    void shutdown() {
        node.close();
    }


    /**
     * Create an index
     * @param index The index to create
     */

    void createIndex(String index) {
        CreateIndexResponse createResponse =
                getClient()
                        .admin()
                        .indices()
                        .create(createIndexRequest(index))
                        .actionGet();
    }

    /**
     * Refresh the index
     * @param index The index to refresh
     */
    void refreshIndex(String index) {
        getClient()
                .admin()
                .indices()
                .prepareRefresh(index)
                .get();
    }

    /**
     * Delete an index
     * @param index The index to delete
     */
    void deleteIndex(String index) {
        getClient()
                .admin()
                .indices()
                .prepareDelete(index)
                .get();
    }
}
