package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyServletInterface {
    //TODO Try to replace with Generic
    Object handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
