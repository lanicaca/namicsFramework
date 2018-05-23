package tests.examples;

import annotations.MyServletInterface;
import annotations.NamicsFramework;
import annotations.NamicsServlet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/myServletInterface",
        method = "GET",
        returns = "JSON"
)
public class TestServletXml implements MyServletInterface {
    private static final Logger log = Logger.getLogger(TestServletXml.class);

    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        NamicsFramework.init(getClass());
        return TestClass.getColor();
    }
}
