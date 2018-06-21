package tests.examples;

import annotations.NamicsFramework;
import annotationsServlet.MainServlet;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

//singleton
public class InitMyFramework {

    private static final Logger log = Logger.getLogger(InitMyFramework.class);
    private static InitMyFramework instance = null;

    private InitMyFramework() {
        NamicsFramework.init(MainServlet.class);
        startJetty(MainServlet.class);
    }

    public static InitMyFramework getInstance() {
        if (instance == null) {
            instance = new InitMyFramework();
        }
        return instance;
    }

    private static void startJetty(Class c) {
        try {
            Server server = new Server();
            Connector con = new SelectChannelConnector();
            con.setPort(8080);
            server.addConnector(con);
            Context context = new Context(server, "/", Context.SESSIONS);
            context.addServlet(new ServletHolder(new MainServlet(c)), "/*");
            server.start();
        } catch (Exception e) {
            log.info("Couldn't initialize jetty server");
            log.error("Couldn't initialize jetty server : ",e);
        }
    }
}
