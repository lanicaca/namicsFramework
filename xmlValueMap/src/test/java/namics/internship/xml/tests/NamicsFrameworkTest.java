package namics.internship.xml.tests;

import annotations.NamicsFramework;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NamicsFrameworkTest {
    @Before
    public void initFramework() {
        NamicsFramework.init(getClass());
    }

    @Test
    public void injectMyTestClass() {
        Assert.assertEquals(TestClass.getADouble(), 0.45, 0.01);
        Assert.assertEquals(TestClass.getAnInt(), 12345);
        Assert.assertEquals(TestClass.getString(), "StringValue");
        Assert.assertEquals(TestClass.getTestComplexObject().getAnInt(), 6789);
        Assert.assertEquals(TestClass.getTestComplexObject().getString(), "TestString");
        Assert.assertFalse(TestClass.getTestComplexObject().isABoolean());
    }
}

