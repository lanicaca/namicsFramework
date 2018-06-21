package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public interface MyServletInterface<T> {
    T handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
