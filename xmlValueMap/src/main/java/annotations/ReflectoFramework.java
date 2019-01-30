package annotations;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Set;

public class ReflectoFramework {
    private static final Logger log = Logger.getLogger(ReflectoFramework.class);
    @Getter
    @Setter
    private static XMLParser xmlParser;

    //initialization : finding annotated fields in a specified class's package
    public static void init(Class c) {
        log.info("Initialization started");
        xmlParser = XMLParser.getInstance(c);
        //making a field reflection on class's "c" package
        Reflections reflections = new Reflections(c.getPackage().getName(), new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotated = reflections.getFieldsAnnotatedWith(ReflectoXmlValueMap.class);
        for (Field field : fieldsAnnotated) {
            boolean isComplex = xmlParser.IsComplex(field.getAnnotation(ReflectoXmlValueMap.class).key());
            setField(field, isComplex);
        }
        log.info("Initialization ended");
    }

    public static void setField(Field field, boolean isComplex){
        try {
            //only static fields
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (!isComplex) {
                    //Set field using XPath - simple fields
                    if (xmlParser.getParsedValue() == null) {
                        log.error("Bad parsing XML - ParsedValue is null");
                        log.info("Initialization failed");
                        return;
                    }
                    field.setAccessible(true);
                    field.set(field.getType(), ConvertUtils.convert(xmlParser.getParsedValue(), field.getType()));
                } else {
                    // Set field using JAXB - complex fields
                    JAXBContext context = JAXBContext.newInstance(field.getType());
                    Unmarshaller um = context.createUnmarshaller();
                    Object o = um.unmarshal(new StringReader(xmlParser.getParsedValue()));
                    field.setAccessible(true);
                    field.set(field.getType(), o);
                }
            }
        } catch (IllegalAccessException | JAXBException e) {
            log.error("!Initialization failed!",e);
        }
    }
}
