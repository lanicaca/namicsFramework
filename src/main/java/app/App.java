package app;

import annotations.NamicsFramework;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

public class App {

    public static void main (String args[]){
        NamicsFramework.init();
        TestClass testClass = new TestClass();
        System.out.println(testClass.getColor());
        System.out.println(testClass.getAnInt());
        System.out.println(testClass.getADouble());
        System.out.println(testClass.getMyboolean());
        System.out.println(testClass.getString());


    }
}
