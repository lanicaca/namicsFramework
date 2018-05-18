package annotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyServletInterface {
      Object handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
