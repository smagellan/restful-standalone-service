package warmup.task;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by vladimir on 5/26/16.
 */
public interface ApiInterface {
    long createAccount(long initialAmount) throws ProcessingException;
    void transfer(long srcAccountId, long destAccountId, long amount) throws ProcessingException;
    Set<Long> userIds();
}
