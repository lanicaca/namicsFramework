package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ReflectoServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "PUT",
        returns = "JSON"
)
public class ConcreteServletPut implements MyServletInterface {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "put";
    }
}
