package annotations;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPathExpressionException;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;

import java.io.StringReader;

public class NamicsFramework {
    private static final Logger log = Logger.getLogger(NamicsFramework.class);
    private static XMLParser xmlParser = XMLParser.getInstance();

    public static void init() {
        log.info("Initialization started");
        Reflections reflections = new Reflections("app.*", new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotated = reflections.getFieldsAnnotatedWith(NamicsXmlValueMap.class);
        for (Field field : fieldsAnnotated) {
            try {
                boolean isComplex = xmlParser.IsComplex(field.getAnnotation(NamicsXmlValueMap.class).key());
                //only static fileds
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
                        field.set(field.getType(), o);
                    }
                }

            } catch (IllegalAccessException e) {
                log.error("Couldn't set field, IllegalAccessException");
                log.info("Initialization failed");
            } catch (JAXBException e) {
                log.error("JAXB parser failed");
                log.info("Initialization failed");
            } catch (XPathExpressionException e) {
                log.error("XPath parser failed");
                log.info("Initialization failed");
            }
        }
        log.info("Initialization ended");
    }
}

