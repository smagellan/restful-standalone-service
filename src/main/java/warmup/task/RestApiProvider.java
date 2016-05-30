package warmup.task;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vladimir on 5/30/16.
 */
public class RestApiProvider implements ApiInterface, AutoCloseable{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RestApiProvider.class);

    private final Client client;
    private final String endpointUrl;

    public RestApiProvider(String endpointUrl){
        this.endpointUrl = endpointUrl;
        this.client      = ClientBuilder.newClient( new ClientConfig().register( LoggingFeature.class ) );
    }

    @Override
    public long createAccount(long initialAmount) throws ProcessingException {
        WebTarget webTarget = client.target(endpointUrl);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(new Long(1000), MediaType.APPLICATION_JSON) );
        long result = response.readEntity(Long.class);
        response.close();
        return result;
    }

    @Override
    public void transfer(long srcAccountId, long destAccountId, long amount) throws ProcessingException {

    }

    @Override
    public Set<Long> userIds() {
        WebTarget webTarget = client.target(endpointUrl);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Set<Long> result = new HashSet<>(response.readEntity(ArrayList.class));
        response.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        client.close();
    }


    public static void main(String[] args) throws ProcessingException, Exception {
        try (RestApiProvider client = new RestApiProvider("http://localhost:8282/api-v1/user")) {
            LOGGER.debug("creating client, result: {}", client.createAccount(1000));
            LOGGER.debug("creating client, result: {}", client.createAccount(1000));
            LOGGER.debug("creating client, result: {}", client.createAccount(1000));
            LOGGER.debug("userIds, result: {}", client.userIds());
        }
    }
}
