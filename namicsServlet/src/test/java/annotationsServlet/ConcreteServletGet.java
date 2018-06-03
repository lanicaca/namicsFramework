package annotationsServlet;

import annotations.NamicsXmlValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServletGet implements MyServletInterface {
    @NamicsXmlValueMap(key = "newTestInt")
    private int myTestInt;

    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //we can do something with request or/and response here
        return new TestClassData(myTestInt, "get", true);
    }
}
