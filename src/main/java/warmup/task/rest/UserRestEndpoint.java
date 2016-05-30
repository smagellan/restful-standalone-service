package warmup.task.rest;

import org.slf4j.LoggerFactory;
import warmup.task.ApiProviderHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 5/26/16.
 */
@Path("api-v1/user")
public class UserRestEndpoint {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserRestEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        try {
            List<Long> tmp = new ArrayList<>(ApiProviderHolder.instance.userIds());
            GenericEntity<List<Long>> wrapped = new GenericEntity<List<Long>>(tmp){};
            Response.ResponseBuilder bldr = Response.ok(wrapped);
            return bldr.build();
        } catch (Exception ex) {
            LOGGER.error("can't list users", ex);
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public long createUser(long balance) {
        try {
            LOGGER.debug("creating user with balance {}", balance);
            return ApiProviderHolder.instance.createAccount(balance);
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @GET
    @Path("{uid}/balance")
    @Produces(MediaType.TEXT_PLAIN)
    public String userbalance(@PathParam("uid") long uid) {
        try {
            return "userbalance for " + uid;
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Path("transfer")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String transfer(String msg) {
        try {
            return msg + "world";
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }
}