package annotations;

import java.util.Collection;

@NamicsServlet(
        path = "/path",
        selector = "/file",
        method = "GET",
        returns = "JSON"
)
public class ConcreteServlet implements MyServletInterface {

    @Override
    public Collection<?> HandleRequest() {
        return null;
    }
}
