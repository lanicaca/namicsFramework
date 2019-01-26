package annotations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class XMLParserTest extends Mockito {
    private XMLParser testXmlParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testXmlParser = XMLParser.getInstance(getClass());
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(testXmlParser.getDoc());
        Assert.assertNotNull(testXmlParser.getXPath());
    }

    @Test
    public void isComplex() {
        Assert.assertTrue(testXmlParser.IsComplex("keyComplexObject"));
        Assert.assertEquals(testXmlParser.getParsedValue(),"<complexObject><testInt>6789</testInt><testString>" +
                "TestString</testString><testBool>false</testBool></complexObject>");
        Assert.assertTrue(!testXmlParser.IsComplex("keyInt"));
        Assert.assertEquals(testXmlParser.getParsedValue(), "12345");
        Assert.assertTrue(!testXmlParser.IsComplex("NoSuchKey"));
        Assert.assertNull(testXmlParser.getParsedValue());
    }

}