package annotationsServlet;

import annotations.NamicsXmlValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "POST",
        returns = "XML"
)
public class ConcreteServletPost implements MyServletInterface {
    @NamicsXmlValueMap(key = "keyTest")
    private static TestClassData testClassData;

    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //we can do something with request or/and response here
        return new TestClassData(123, "post", false);
    }
}
