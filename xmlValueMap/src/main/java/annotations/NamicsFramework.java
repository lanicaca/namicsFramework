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

public class NamicsFramework {
    private static final Logger log = Logger.getLogger(NamicsFramework.class);
    @Getter
    @Setter
    private static XMLParser xmlParser;
    @Getter
    boolean isComplex;
    //initialization : finding annotated fields in a specified class's package

    public void init(Class c) {
        log.info("Initialization started");
        xmlParser = XMLParser.getInstance(c);
        log.debug(" WHAT?   "+xmlParser.getParsedValue() + "    parsed value in init");
        //making a field reflection on class's "c" package
        Reflections reflections = new Reflections(c.getPackage().getName(), new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotated = reflections.getFieldsAnnotatedWith(NamicsXmlValueMap.class);
        for (Field field : fieldsAnnotated) {
            log.debug(" WHAT?   "+xmlParser.getParsedValue() + "    fields annotated");
            isComplex = xmlParser.IsComplex(field.getAnnotation(NamicsXmlValueMap.class).key());
            setField(field);
        }
        log.info("Initialization ended");
    }

    public void setField(Field field){
        try {
            log.debug(" WHAT?   "+xmlParser.getParsedValue() + "     try");
            //only static fields
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {

                log.debug(" WHAT?   "+xmlParser.getParsedValue() + "     static");
                if (!isComplex) {
                    // xmlParser.setParsedValue();
                    log.debug(" WHAT?   "+xmlParser.getParsedValue() + "      isNOTcomplex???");
                    //Set field using XPath - simple fields
                    if (xmlParser.getParsedValue() == null) {
                        log.error("Bad parsing XML - ParsedValue is null");
                        log.info("Initialization failed");
                        return;
                    }
                    field.setAccessible(true);
                    field.set(field.getType(), ConvertUtils.convert(xmlParser.getParsedValue(), field.getType()));

                } else {
                    log.debug(" WHAT?   "+xmlParser.getParsedValue() + "    IS COMPLEX");
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

