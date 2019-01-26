package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//Functional Interface
public interface MyServletInterface<T> {
    T handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
