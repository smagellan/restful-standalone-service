package warmup.task;

import static org.junit.Assert.*;

/**
 * Created by vladimir on 5/26/16.
 */
public class ApiProviderTest {

    @org.junit.Test(expected = ProcessingException.class)
    public void transferNotEnoughMoney() throws Exception {
        ApiProvider provider = new ApiProvider();
        long firstAccount = provider.createAccount(1000);
        long secondAccount = provider.createAccount(2000);
        provider.transfer(firstAccount, secondAccount, 2000);
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIllegalAccountId() throws Exception {
        ApiProvider provider = new ApiProvider();
        long firstAccount = provider.createAccount(1000);
        long secondAccount = provider.createAccount(2000);
        provider.transfer(firstAccount + secondAccount, secondAccount, 2000);
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIntraTransfer() throws Exception {
        ApiProvider provider = new ApiProvider();
        long firstAccount = provider.createAccount(1000);
        provider.transfer(firstAccount, firstAccount, 2000);
    }
}