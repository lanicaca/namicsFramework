package annotations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReflectoFrameworkTest extends Mockito {

    @Mock private XMLParser xmlParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectoFramework.init(getClass());
        ReflectoFramework.setXmlParser(xmlParser);
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