package annotationsServlet;

import annotations.ReflectoXmlValueMap;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ReflectoServlet(
        path = "/test",
        selector = "/ConcreteServlet",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServletGet implements MyServletInterface {
    @Getter
    @ReflectoXmlValueMap(key = "newTestInt")
    private static int myTestInt;

    @Override
    public TestClassData handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return new TestClassData(myTestInt, "get", true);
    }
}
