package warmup.task.rest;

import org.slf4j.LoggerFactory;
import warmup.task.ApiInterface;
import warmup.task.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 5/26/16.
 */
@Path(Constants.BASE_PATH)
public class UserRestEndpoint {
    @javax.inject.Inject
    private ApiInterface apiInterface;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserRestEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        try {
            LOGGER.debug("apiInterface: {}", apiInterface);
            List<Long> tmp = new ArrayList<>(apiInterface.userIds());
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
            return apiInterface.createAccount(balance);
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @GET
    @Path("{uid}/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public long userbalance(@PathParam("uid") long uid) {
        try {
            return apiInterface.userBalance(uid);
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Path("{uidFrom}/transfer/{uidTo}/{moneyAmount}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public long transfer(@PathParam("uidFrom") long uidFrom, @PathParam("uidTo")long uidTo, @PathParam("moneyAmount")long moneyAmount) {
        try {
            apiInterface.transfer(uidFrom, uidTo, moneyAmount);
            return 0;
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }
}