package tests.examples;

//import annotations.MainServlet;
//import annotations.NamicsFramework;
//import org.apache.log4j.Logger;

//singleton
public class InitMyFramework {

    /*private static final Logger log = Logger.getLogger(InitMyFramework.class);
    private static InitMyFramework instance = null;

    private InitMyFramework() {
        NamicsFramework.init(getClass());
        startJetty(getClass());
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
    }*/
}
