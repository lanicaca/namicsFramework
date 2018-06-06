package annotations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class NamicsFrameworkTest extends Mockito {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        NamicsFramework.init(getClass());
    }

    @Mock private XMLParser xmlParser;
    private static Field field;

    @Test
    public void namicsFrameWorkSetFieldGetParsedValueReturnsNull(){
        NamicsFramework.setXmlParser(xmlParser);
        System.out.println(xmlParser.toString());
        when(xmlParser.getParsedValue()).thenReturn(null);
        System.out.println(xmlParser.getParsedValue());
    }

    @Test
    public void injectMyValuesFromConfigToTestClass() {
        Assert.assertEquals(0.45, TestClass.getADouble(), 0.01);
        Assert.assertEquals(12345, TestClass.getAnInt());
        Assert.assertEquals("StringValue", TestClass.getString());
        Assert.assertEquals(6789, TestClass.getTestComplexObject().getAnInt());
        Assert.assertEquals("TestString", TestClass.getTestComplexObject().getString());
        Assert.assertFalse(TestClass.getTestComplexObject().isABoolean());
    }


}