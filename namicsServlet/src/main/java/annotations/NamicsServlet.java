package annotations;
import com.sun.deploy.net.HttpRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Formatter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NamicsServlet {
    String path ();
    String selector ();
    String returns ();
    String method ();
}
