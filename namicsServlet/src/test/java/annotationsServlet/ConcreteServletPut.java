package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "PUT",
        returns = "JSON"
)
public class ConcreteServletPut implements MyServletInterface {

    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "put";
    }
}
