package annotationsServlet;

import annotations.NamicsXmlValueMap;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServletGet implements MyServletInterface {
    @Getter
    @NamicsXmlValueMap(key = "newTestInt")
    private static int myTestInt;

    @Override
    public TestClassData handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return new TestClassData(myTestInt, "get", true);
    }
}
