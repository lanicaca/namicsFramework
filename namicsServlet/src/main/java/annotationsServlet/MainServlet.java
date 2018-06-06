package annotationsServlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class MainServlet extends HttpServlet {
    private static final String METHOD_NAME = "handleRequest";
    private static final Logger log = Logger.getLogger(MainServlet.class);
    @Getter
    private final String packageName;
    @Getter
    private Set<Class<?>> annotatedClassesReflection;
    private ArrayList<AnnotatedClass> annotatedClasses;

    //Constructor defines on which package the reflection is done
    //is called when jetty server is started
    public MainServlet() {
        super();
        this.packageName = getClass().getPackage().getName();
    }

    public MainServlet(Class class_package) {
        super();
        this.packageName = class_package.getPackage().getName();
    }


    @Override
    //Is automatically called just once, when the first HTTP request is called
    public void init() {
        try {
            super.init();
        } catch (ServletException e) {
            log.error("Couldn't initialize MainServlet",e);
        }
        annotatedClasses = new ArrayList<>();
        Reflections reflections = new Reflections(this.getPackageName());
        //Get all annotated classes with NamicsServlet
        annotatedClassesReflection = reflections.getTypesAnnotatedWith(NamicsServlet.class);
        if (annotatedClassesReflection.isEmpty()) log.warn("No classes annotated with NamicsServlet.class were found");
        //Make a list of -AnnotatedClass objects- from this -- see the class for attributes
        for (Class<?> c : annotatedClassesReflection) {
            NamicsServlet annotation = c.getAnnotation(NamicsServlet.class);
            annotatedClasses.add(new AnnotatedClass(annotation.path(), annotation.selector(), annotation.method(), annotation.returns(), c));
        }
        log.info("Main servlet initialized");
    }

    @Override
    //Is automatically called by the servlet container each time the new HTTP request is sent,
    // to allow the servlet to respond to a request
    public void service(HttpServletRequest request, HttpServletResponse response) {
        //The flag that checks if the URI is wrong at the end of function
        boolean wrongURI = true; //TODO replace all of this with Java Streams
        //the path and the selector must be written starting with "/",
        //this can be changed easily if there is need for any other format
        for (AnnotatedClass c : annotatedClasses) { //TODO replace all of this with Java Streams
            String URI = c.getPath() + c.getSelector();
            if (URI.equals(request.getRequestURI())) {
                wrongURI = false; //at least one class is annotated with this URI
                forwardToHttpMethod(c.getMethod(), request, response, c); //this function chooses the request method
            }
        }
        if (wrongURI) { //check for the filepath
            //If the URI is wrong, try to write file from that filepath as response
            response.setContentType("text/html");
            if (request.getRequestURI() != null) {
                File myFile = new File(request.getRequestURI());
                try {
                    response.getWriter().print(FileUtils.readFileToString(myFile));
                } catch (IOException e) {
                    log.error("No such file : " + request.getRequestURI() , e);
                    log.warn("Wrong URI : " + request.getRequestURI());
                    response.setStatus(404);
                }
            }
        }
        log.warn("Wrong URI : " + request.getRequestURI());
    }

    private void doGet(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        //method that invokes handleRequest() method from annotated class and returns whatever that method returns
        Object returnValue = invokeMethod(annotatedClass, METHOD_NAME, req, resp);
        try {
            //Writing in body of response the converted value in json or xml
            //see the function convertResponse
            resp.getWriter().append(convertResponse(resp, returnValue, annotatedClass.getReturns()));
        } catch (IOException e) {
            log.error("Error in converting and writing response: " , e);
        }
    }

    private void doPost(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }

    private void doPut(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }

    private void doDelete(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }

    Object invokeMethod(AnnotatedClass annotatedClass, String myMethodName,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            //Implementation of reflection (invoking method from annotated class)
            Method method = annotatedClass.getMyClass().getDeclaredMethod(myMethodName,
                    HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            //this method can have arguments "request" and "response" and do something with it
            return method.invoke(annotatedClass.getMyClass().newInstance(), request, response);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error in reflection - invoking method: " ,e);

        }
        log.error("Invoke method returns null");
        return null;
    }

    String convertResponse(HttpServletResponse response, Object returnValue, String returns) throws IOException {
        switch (returns) {
            //use of Jackson library for both conversions
            case "JSON": {
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(returnValue);
            }
            case "XML": {
                response.setContentType("application/xml");
                XmlMapper xmlMapper = new XmlMapper();
                return xmlMapper.writeValueAsString(returnValue);
            }
        }
        log.warn("Return type is neither XML nor JSON");
        return null;
    }

    private void forwardToHttpMethod(String methodName, HttpServletRequest request, HttpServletResponse response,
                                     AnnotatedClass annotatedClass) {
        //checks if the class is annotated with the same method that is requested
        if (annotatedClass.getMethod().equals(request.getMethod())) {
            switch (methodName) {
                case "DELETE": {
                    doDelete(request, response, annotatedClass);
                    log.debug("doDelete method called");
                    break;
                }
                case "PUT": {
                    doPut(request, response, annotatedClass);
                    log.debug("doPut method called");
                    break;
                }
                case "POST": {
                    doPost(request, response, annotatedClass);
                    log.debug("doPost method called");
                    break;
                }
                case "GET": {
                    doGet(request, response, annotatedClass);
                    log.debug("doGet method called");
                    break;
                }
            }
        } else {
            //HTTP request method is different than the one annotated in the class
            log.warn("Can not call " + request.getMethod() + " request, only " + annotatedClass.getMethod() + " requests allowed");
        }
    }

    @Override
    //Called by the servlet container to indicate to a servlet that the servlet is being taken out of service.
    public void destroy() {
        super.destroy();
        log.info("Servlet is being taken out of service");
    }
}
