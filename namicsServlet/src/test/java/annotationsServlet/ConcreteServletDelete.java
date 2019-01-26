package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "DELETE",
        returns = "JSON"
)
public class ConcreteServletDelete implements MyServletInterface{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "delete";
    }
}
