package annotationsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/myServletInterface",
        method = "GET",
        returns = "JSON"
)
public class TestServletXml implements MyServletInterface {
    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return TestClass.getColor();
    }
}
