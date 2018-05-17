package annotations;

import lombok.Getter;
import org.reflections.Reflections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class MainServlet extends HttpServlet {
    @Getter
    private Set<Class<?>> annotatedClasses;
    private static final String METHOD_NAME = "handleRequest";

    @Override
    public void init() throws ServletException {
        super.init();
        Reflections reflections = new Reflections("annotations.*");
        annotatedClasses = reflections.getTypesAnnotatedWith(NamicsServlet.class);
        System.out.println("initialized");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        for (Class<?> c:annotatedClasses) {
            NamicsServlet annotation =c.getAnnotation(NamicsServlet.class);
            String URI =  annotation.path() + annotation.selector();
            if (URI.equals(request.getRequestURI())){
                try {
                    System.out.println("CallMethod called");
                    callMethod(annotation.method(),request,response,c);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                    //prebaci u throws nemoj da catchujes ovde
                }
            }
        }
    }

    private void callMethod(String methodName,HttpServletRequest request,HttpServletResponse response,Class<?> annotatedClass) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        switch (methodName){
            case "DELETE": {doDelete(request,response,annotatedClass); System.out.println("doDelete called"); break;}
            case "PUT": {doPut(request,response,annotatedClass); System.out.println("doPut called"); break;}
            case "POST": {doPost(request,response,annotatedClass);System.out.println("doPost called");break;}
            case "GET": {doGet(request,response,annotatedClass);System.out.println("doGet called");break;}
            case "HEAD": {doHead(request,response,annotatedClass);System.out.println("doHead called");break;}
            case "OPTION": {doOptions(request,response,annotatedClass);System.out.println("doOption called");break;}
            case "TRACE": {doTrace(request,response,annotatedClass);System.out.println("doTrace called");break;}
            //loguj i handluje gresku ako ne moze da se pozove neka metoda
        }
    }

    private void invokeMethod(Class<?> myClass, String myMethodName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object o = myClass.newInstance();
        Method method = myClass.getDeclaredMethod(myMethodName);
        method.setAccessible(true);
        method.invoke(o);
        System.out.println("invokeMethod finished");
        //lgouj i handluj gresku ako ne moze se invokuje metoda
    }
    private void doGet(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("doGet start");
        invokeMethod(annotatedClass,METHOD_NAME);
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("<h1> in doGet Method</h1>");
        resp.getWriter().println("YEY!");
        System.out.println("doGet end");
        //dodaj logiku za doGet konvertuj u response
    }
    private void doPost(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("doPost start");
        doGet(req,resp, annotatedClass);
        System.out.println("doPost end");
        //napravi posebnu logiku za doPost,citaj hedere
    }
    private void doPut(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass){

    }

    private void doDelete(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass){

    }
    private void doHead(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass){

    }
    private void doOptions(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass){

    }
    private void doTrace(HttpServletRequest req, HttpServletResponse resp, Class<?> annotatedClass){
    }

}
