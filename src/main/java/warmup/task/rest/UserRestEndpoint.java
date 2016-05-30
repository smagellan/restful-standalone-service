package warmup.task.rest;

import warmup.task.ApiProviderHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by vladimir on 5/26/16.
 */
@Path("api-v1/user")
public class UserRestEndpoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listUsers() {
        try {
            return ApiProviderHolder.instance.userIds().toString();
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String createUser(long balance) {
        try {
            //long balance = Long.parseLong(val);
            return "creating user with balance " + balance;
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