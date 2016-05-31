package warmup.task;

import org.junit.After;
import org.junit.Before;

/**
 * Created by vladimir on 5/30/16.
 */
public class RestApiProviderTest {

    private final JettyRunner jettyRunner;
    private final String endPointUrl;

    public RestApiProviderTest() {
        this.jettyRunner = new JettyRunner(Constants.DEFAULT_PORT);
        this.endPointUrl = String.format("http://localhost:%d/%s", Constants.DEFAULT_PORT, Constants.BASE_PATH);
    }

    @Before
    public void runServer() throws Exception {
        jettyRunner.startServer();
    }

    @After
    public void stopServer() throws Exception {
        jettyRunner.stop();
        jettyRunner.destroy();
    }


    @org.junit.Test(expected = ProcessingException.class)
    public void transferNotEnoughMoney() throws Exception {
        ApiInterface provider = new RestApiProvider(endPointUrl);
        WorkHorse horse = new WorkHorse(provider);
        horse.transferNotEnoughMoney();
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIllegalAccountId() throws Exception {
        ApiInterface provider = new RestApiProvider(endPointUrl);
        WorkHorse horse = new WorkHorse(provider);
        horse.transferIllegalAccountId();
    }

    @org.junit.Test(expected = ProcessingException.class)
    public void transferIntraTransfer() throws Exception {
        ApiInterface provider = new RestApiProvider(endPointUrl);
        WorkHorse horse = new WorkHorse(provider);
        horse.transferIntraTransfer();
    }
}
