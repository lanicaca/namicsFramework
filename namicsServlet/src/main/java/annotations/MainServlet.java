package annotations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class MainServlet extends HttpServlet {
    private static final String METHOD_NAME = "handleRequest";
    private static final Logger log = Logger.getLogger(MainServlet.class);
    @Getter
    private Set<Class<?>> annotatedClassesReflection;
    private ArrayList<AnnotatedClass> annotatedClasses;

    @Override
    //Is automatically called just once, when the first HTTP request is called
    public void init() {
        try {
            super.init();
        } catch (ServletException e) {
            log.error("Couldn't initialize MainServlet");
        }
        annotatedClasses = new ArrayList<>();
        Reflections reflections = new Reflections("app.*");
        //Get all annotated classes with NamicsServlet
        annotatedClassesReflection = reflections.getTypesAnnotatedWith(NamicsServlet.class);
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
        log.debug("Service started");
        //The flag that checks if the URI is wrong at the end of function
        boolean wrongURI = true;
        //the path and the selector must be written starting with "/",
        //this can be changed easily if there is need for any other format
        for (AnnotatedClass c : annotatedClasses) {
            String URI = c.getPath() + c.getSelector();
            if (URI.equals(request.getRequestURI())) {
                wrongURI = false; //at least one class is annotated with this URI
                callMethod(c.getMethod(), request, response, c); //this function chooses the request method
            }
        }
        if (wrongURI) log.warn("Wrong URI : " + request.getRequestURI());
    }

    private void doGet(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        //method that invokes handleRequest() method from annotated class and returns whatever that method returns
        Object returnValue = invokeMethod(annotatedClass, METHOD_NAME, req, resp);
        try {
            //Writing in body of response the converted value in json or xml
            //see the function convertResponse
            resp.getWriter().append(convertResponse(resp, returnValue, annotatedClass.getReturns()));
        } catch (IOException e) {
            log.error("Error in coverting and writing response");
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

    private void doHead(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }

    private void doOptions(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }

    private void doTrace(HttpServletRequest req, HttpServletResponse resp, AnnotatedClass annotatedClass) {
        doGet(req, resp, annotatedClass);
    }


    private Object invokeMethod(AnnotatedClass annotatedClass, String myMethodName,
                                HttpServletRequest request, HttpServletResponse response) {
        try {
            //Implementation of reflection (invoking method from annotated class)
            Object o = annotatedClass.getMyClass().newInstance();
            Method method = annotatedClass.getMyClass().getDeclaredMethod(myMethodName,
                    HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            //this method can have arguments "request" and "response" and do something with it
            return method.invoke(o, request, response);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            log.error("Error in reflection - invoking method");
        }
        log.error("Invoke method returns null");
        return null;
    }

    private String convertResponse(HttpServletResponse response, Object returnValue, String returns) throws IOException {
        switch (returns) {
            //use of Jackson for both conversions
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

    private void callMethod(String methodName, HttpServletRequest request, HttpServletResponse response,
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
                case "HEAD": {
                    doHead(request, response, annotatedClass);
                    log.debug("doHead method called");
                    ;
                    break;
                }
                case "OPTION": {
                    doOptions(request, response, annotatedClass);
                    log.debug("doOption method called");
                    break;
                }
                case "TRACE": {
                    doTrace(request, response, annotatedClass);
                    log.debug("doTrace method called");
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
