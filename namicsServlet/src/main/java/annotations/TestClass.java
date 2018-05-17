package annotations;

import sun.net.www.content.text.Generic;

import java.util.Collection;
@NamicsServlet(
        path = "/test",
        selector = "/TestClass",
        method = "POST",
        returns = "XML"
)
public class TestClass implements MyServletInterface{

    @Override
    public void handleRequest() {
        System.out.println("Test invoked");
    }

}
