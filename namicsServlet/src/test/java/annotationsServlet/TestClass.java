package annotationsServlet;

import annotations.NamicsXmlValueMap;
import lombok.Data;
import lombok.Getter;

@Data
public class TestClass {
    @Getter
    @NamicsXmlValueMap(key = "keyColor")
    public static Color color;
    @Getter
    @NamicsXmlValueMap(key = "keyInt")
    public static int anInt;
    @Getter
    @NamicsXmlValueMap(key = "keyString")
    public static String string;
    @Getter
    @NamicsXmlValueMap(key = "keyDouble")
    public static Double aDouble;
    @Getter
    @NamicsXmlValueMap(key = "keyBoolean")
    public static boolean myBoolean;

}
