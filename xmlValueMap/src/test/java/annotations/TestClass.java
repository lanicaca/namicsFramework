package annotations;

import lombok.Data;
import lombok.Getter;

@Data
class TestClass {
    @Getter
    @ReflectoXmlValueMap(key = "keyComplexObject")
    public static TestComplexObject testComplexObject;
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
