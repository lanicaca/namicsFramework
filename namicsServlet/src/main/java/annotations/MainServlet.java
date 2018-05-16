package annotations;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){

    }
   // Called by the server (via the service method) to allow a servlet to handle a DELETE request.
   @Override
    protected void	doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       resp.setContentType("text/html");
       resp.setStatus(HttpServletResponse.SC_OK);
           resp.getWriter().println("<h1> in doGet Method</h1>");
           resp.getWriter().println("YEY!");


    }
    //Called by the server (via the service method) to allow a servlet to handle a GET request.
    @Override
    protected void	doHead(HttpServletRequest req, HttpServletResponse resp){

    }
   // Receives an HTTP HEAD request from the protected service method and handles the request.
   @Override
    protected void	doOptions(HttpServletRequest req, HttpServletResponse resp){

    }
    //Called by the server (via the service method) to allow a servlet to handle a OPTIONS request.
    @Override
    protected void	doPost(HttpServletRequest req, HttpServletResponse resp){

    }
    //Called by the server (via the service method) to allow a servlet to handle a POST request.
    protected void	doPut(HttpServletRequest req, HttpServletResponse resp){

    }
    //Called by the server (via the service method) to allow a servlet to handle a PUT request.
    @Override
    protected void	doTrace(HttpServletRequest req, HttpServletResponse resp){

    }
    //Called by the server (via the service method) to allow a servlet to handle a TRACE request.
   /* @Override
    protected long	getLastModified(HttpServletRequest req){

    }
    //Returns the time the HttpServletRequest object was last modified, in milliseconds since midnight January 1, 1970 GMT.
    @Override
    protected void	service(HttpServletRequest req, HttpServletResponse resp){

    }
    //Receives standard HTTP requests from the public service method and dispatches them to the doXXX methods defined in this class.




   /* Server server;
    ContextHandler contextHandler;
    public MainServlet (String path){
        server = new Server();
        contextHandler = new ContextHandler();
        contextHandler.setContextPath(path);
        server.setHandler(contextHandler);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error ("Unable to start the server");
        }

    }*/
}
