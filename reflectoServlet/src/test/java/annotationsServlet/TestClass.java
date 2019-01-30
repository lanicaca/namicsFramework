package annotationsServlet;

import annotations.ReflectoXmlValueMap;
import lombok.Data;
import lombok.Getter;

@Data
public class TestClass {
    @Getter
    @ReflectoXmlValueMap(key = "keyColor")
    public static Color color;
    @Getter
    @ReflectoXmlValueMap(key = "keyInt")
    public static int anInt;
    @Getter
    @ReflectoXmlValueMap(key = "keyString")
    public static String string;
    @Getter
    @ReflectoXmlValueMap(key = "keyDouble")
    public static Double aDouble;
    @Getter
    @ReflectoXmlValueMap(key = "keyBoolean")
    public static boolean myBoolean;
}
