package warmup.task;

import java.util.Set;

/**
 * Created by vladimir on 5/26/16.
 */
public interface ApiInterface {
    long userBalance(long uid) throws ProcessingException;
    long createAccount(long initialAmount) throws ProcessingException;
    void transfer(long srcAccountId, long destAccountId, long amount) throws ProcessingException;
    Set<Long> userIds() throws ProcessingException;
}
