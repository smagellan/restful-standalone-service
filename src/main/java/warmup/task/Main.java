package warmup.task;

import org.slf4j.LoggerFactory;

/**
 * Created by vladimir on 5/26/16.
 */
public class Main {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        JettyRunner runner = new JettyRunner(8282);
        runner.startServer();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    runner.stop();
                    runner.join();
                } catch (Exception ex) {
                    LOGGER.error("can't stop server", ex);
                }
            }
        });
    }
}
