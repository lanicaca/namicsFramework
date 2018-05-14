package app;

import annotations.Init;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class App {

    public static void main (String args[]) throws XPathExpressionException, IllegalAccessException, NoSuchFieldException, InstantiationException, JAXBException, IOException, ParserConfigurationException, SAXException {
        new Init().init();
        TestClass testClass = new TestClass();
        System.out.println(testClass.getColor());
        System.out.println(testClass.getAnInt());
        System.out.println(testClass.getADouble());
        System.out.println(testClass.getMyboolean());
        System.out.println(testClass.getString());


    }
}
