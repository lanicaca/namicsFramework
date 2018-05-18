package app;

import annotations.MyServletInterface;
import annotations.NamicsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServletGet implements MyServletInterface {
    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //we can do something with request or/and response here
        TestClassData testClassData = new TestClassData(5, "test", true);
        return testClassData;
    }
}
