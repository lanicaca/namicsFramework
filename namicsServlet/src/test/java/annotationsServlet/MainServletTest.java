package annotationsServlet;

import annotations.NamicsFramework;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@RunWith(MockitoJUnitRunner.class)
public class MainServletTest extends Mockito {

    @Mock
    private AnnotatedClass annotatedClass;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    private String mockMethod = "anymethod";
    private String mockURI = "anyURI";
    private String mockString = "anyOtherString";
    private MainServlet mainServlet;
    public static final String projectPath= System.getProperty("user.dir").replaceFirst("/namicsServlet", "");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainServlet = new MainServlet(getClass());
        mainServlet.init();
        NamicsFramework.init(getClass());
    }

    @Test
    public void mainServletTests() {
        //no xmlValueMap initialized - annotated field get default values
        Assert.assertTrue(mainServletTestWithMockito("GET", "/test/ConcreteServlet", "\"anInt\":6,\"string\":\"get\",\"aboolean\":true"));
        Assert.assertTrue(mainServletTestWithMockito("POST", "/test/ConcreteServlet", "<TestClassData><anInt>123</anInt><string>post</string><aboolean>false</aboolean></TestClassData>"));
        Assert.assertTrue(mainServletTestWithMockito("PUT", "/test/ConcreteServlet", "put"));
        Assert.assertTrue(mainServletTestWithMockito("DELETE", "/test/ConcreteServlet", "delete"));
        Assert.assertTrue(mainServletTestWithMockito("GET", "/test/myServletInterface", "\"r\":34,\"g\":5543,\"b\":254"));
        Assert.assertTrue(mainServletTestWithMockito("GET", projectPath+"/examples/src/index.html", "My html example"));
        Assert.assertTrue(mainServletTestWithMockito(mockMethod, mockURI, ""));
    }

    @Test(expected = NullPointerException.class)
    public void invokeMethodNullException() {
        when(mainServlet.invokeMethod(annotatedClass, mockMethod, request, response)).thenThrow(NullPointerException.class);
    }


    @Test
    public void convertResponse() throws IOException {
            Assert.assertNull(mainServlet.convertResponse(response, mockString, mockString));
            Assert.assertEquals(mainServlet.convertResponse(response, mockString, "XML"), "<String>anyOtherString</String>");
            Assert.assertEquals(mainServlet.convertResponse(response, mockString, "JSON"), "\"anyOtherString\"");
    }

    @Test
    public void destroy() {
        mainServlet.destroy();
    }


    private StringWriter createResponseWriterMockup() throws IOException {
        StringWriter servletResult = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(servletResult);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        return servletResult;
    }

    private boolean mainServletTestWithMockito(String method, String path, String result) {

        when(request.getMethod()).thenReturn(method);
        when(request.getRequestURI()).thenReturn(path);
        StringWriter servletResult = null;
        try {
            servletResult = createResponseWriterMockup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainServlet.service(request, response);
        System.out.println("servlet result for : "+  path + " for method "  + method + " result "  +servletResult.toString());
        return servletResult.toString().contains(result);
    }

}