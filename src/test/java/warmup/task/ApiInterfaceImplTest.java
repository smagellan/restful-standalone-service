package warmup.task;


/**
 * Created by vladimir on 5/26/16.
 */
public class ApiInterfaceImplTest {

    @org.junit.Test(expected = ProcessingException.class)
    public void transferNotEnoughMoney() throws Exception {
        ApiInterfaceImpl provider = new ApiInterfaceImpl();
        WorkHorse horse = new WorkHorse(provider);
        horse.transferNotEnoughMoney();
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIllegalAccountId() throws Exception {
        ApiInterfaceImpl provider = new ApiInterfaceImpl();
        WorkHorse horse = new WorkHorse(provider);
        horse.transferIllegalAccountId();
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIntraTransfer() throws Exception {
        ApiInterfaceImpl provider = new ApiInterfaceImpl();
        WorkHorse horse = new WorkHorse(provider);
        horse.transferIntraTransfer();
    }
}