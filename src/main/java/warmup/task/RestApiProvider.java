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

// TODO: These 'response.getStatusInfo() == Response.Status.OK' are insane.
// Can I make Invocation.Builderto to throw exceptions if needed?
public class RestApiProvider implements ApiInterface, AutoCloseable{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RestApiProvider.class);

    private final Client client;
    private final String endpointUrl;

    public RestApiProvider(String endpointUrl){
        this.endpointUrl = endpointUrl;
        this.client      = ClientBuilder.newClient( new ClientConfig().register( LoggingFeature.class ) );
    }

    @Override
    public long userBalance(long uid) throws ProcessingException {
        WebTarget webTarget = client.target(String.format("%s/%d/balance", endpointUrl, uid));
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = null;
        try {
            response = invocationBuilder.get();
            if (response.getStatusInfo() == Response.Status.OK) {
                long result = response.readEntity(Long.class);
                return result;
            } else {
                throw new ProcessingException("Server error");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

    }

    @Override
    public long createAccount(long initialAmount) throws ProcessingException {
        WebTarget webTarget = client.target(endpointUrl);
        return longToLongRPC(webTarget, initialAmount);
    }

    public static long longToLongRPC(WebTarget webTarget, long param)  throws ProcessingException  {
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = null;
        try {
            response = invocationBuilder.post(Entity.entity(new Long(param), MediaType.APPLICATION_JSON));
            if (response.getStatusInfo() == Response.Status.OK) {
                long result = response.readEntity(Long.class);
                return result;
            } else {
                throw new ProcessingException("Server error");
            }
        } finally {
            if (response != null ) {
                response.close();
            }
        }
    }


    @Override
    public void transfer(long srcAccountId, long destAccountId, long amount) throws ProcessingException {
        WebTarget webTarget = client.target(String.format("%s/%d/transfer/%d/%d", endpointUrl, srcAccountId, destAccountId, amount));
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = null;
        try {
            response = invocationBuilder.post(Entity.entity(0, MediaType.APPLICATION_JSON));
            if (response.getStatusInfo() == Response.Status.OK) {
                response.readEntity(String.class);
            } else {
                throw new ProcessingException("Server error");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public Set<Long> userIds() throws ProcessingException{
        WebTarget webTarget = client.target(endpointUrl);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response  = null;
        try {
            response = invocationBuilder.get();
            if (response.getStatusInfo() == Response.Status.OK) {
                Set<Long> result = new HashSet<>(response.readEntity(ArrayList.class));
                return result;
            } else {
                throw new ProcessingException("Server error");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public void close() throws Exception {
        client.close();
    }


    public static void main(String[] args) throws ProcessingException, Exception {
        final String basePath = String.format("http://localhost:%d/%s", Constants.BASE_PATH, Constants.DEFAULT_PORT);
        try (RestApiProvider client = new RestApiProvider(basePath)) {
            final long uid1 = client.createAccount(1000);
            final long uid2 = client.createAccount(1000);
            final long uid3 = client.createAccount(1000);

            LOGGER.debug("creating client, result: {}", uid1);
            LOGGER.debug("creating client, result: {}", uid2);
            LOGGER.debug("creating client, result: {}", uid3);
            LOGGER.debug("userIds, result: {}", client.userIds());
            LOGGER.debug("user1 balance, result: {}", client.userBalance(uid1));
            LOGGER.debug("user2 balance, result: {}", client.userBalance(uid2));
            client.transfer(uid1 + 1000, uid2, 100);
            LOGGER.debug("user1 balance, result: {}", client.userBalance(uid1));
            LOGGER.debug("user2 balance, result: {}", client.userBalance(uid2));
        }
    }
}
