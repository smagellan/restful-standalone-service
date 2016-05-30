package warmup.task;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by vladimir on 5/26/16.
 */
public class JettyRunner {
    private final Server server;

    public JettyRunner(int port) {
        server = new Server(port);
    }

    public void startServer() throws Exception {
        ResourceConfig config = new ResourceConfig();
        config.packages("warmup.task.rest");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));


        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        server.start();
    }

    public void join() throws Exception{
        try {
            server.join();
        }finally {
            server.destroy();
        }
    }

    public void stop() throws Exception{
        server.stop();
    }
}
