package tests.examples;

import org.apache.log4j.Logger;

public class MainApp {
    private static final Logger log = Logger.getLogger(MainApp.class);

    public static void main(String args[]) {
        InitMyFramework.getInstance();
    }
}
