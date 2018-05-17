package annotations;

import sun.net.www.content.text.Generic;

import java.util.Collection;

@NamicsServlet(
        path = "/test",
        selector = "/ConcreteClass",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServlet implements MyServletInterface {

    @Override
    public void handleRequest() {
        System.out.println("ConcreteClass invoked");
    }

}
