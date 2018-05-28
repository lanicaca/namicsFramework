package namics.internship.xml.tests;

import annotations.NamicsXmlValueMap;
import lombok.Data;
import lombok.Getter;

@Data
class TestClass {
        @Getter
        @NamicsXmlValueMap(key = "keyComplexObject")
        public static TestComplexObject testComplexObject;
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
