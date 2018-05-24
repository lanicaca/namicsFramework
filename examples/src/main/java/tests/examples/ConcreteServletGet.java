package tests.examples;

import annotations.MyServletInterface;
import annotations.NamicsServlet;
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
    @NamicsXmlValueMap(key = "newtestint")
    private static int mytestint;

    @Override
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response) {
        //we can do something with request or/and response here
        TestClassData testClassData = new TestClassData(mytestint, "test", true);
        return testClassData;
    }
}
