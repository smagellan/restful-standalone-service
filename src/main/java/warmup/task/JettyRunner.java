package warmup.task;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * Created by vladimir on 5/26/16.
 */
public class JettyRunner {
    private final Server server;

    public JettyRunner(int port) {
        server = new Server(port);
    }


    public void startServer() throws Exception {
        ServletContextHandler sch = new ServletContextHandler(server, "/");
        sch.addServlet(DefaultServlet.class,"/").setInitParameter("resourceBase", "webapps");

        ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
        jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MyWebApp.class.getCanonicalName());
        sch.addServlet(jerseyServletHolder, "/*");
        server.start();
    }

    public void join() throws Exception{
        server.join();
    }

    public void destroy() throws Exception{
        server.destroy();
    }

    public void stop() throws Exception{
        server.stop();
    }
}


