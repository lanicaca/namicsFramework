package annotations;

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
//@Init
public class Init {
    private XMLParser XMLParser;

    public Init() {
        XMLParser = new XMLParser("src/main/resources/config.xml");
    }

    public void init() throws XPathExpressionException, IllegalAccessException, JAXBException {
        Reflections reflections = new Reflections("app.*", new FieldAnnotationsScanner());
        Set<Field> fieldsAnnotated = reflections.getFieldsAnnotatedWith(NamicsXmlValueMap.class);
        for (Field field : fieldsAnnotated) {
            boolean isComplex = XMLParser.IsComplex(field.getAnnotation(NamicsXmlValueMap.class).key());
            //only static fileds
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (!isComplex) {
                    //Set field using XPath - simple fields
                    field.setAccessible(true);
                    field.set(field.getType(), ConvertUtils.convert(XMLParser.getValue(), field.getType()));
                } else {
                    // Set field using JAXB - complex fields
                    JAXBContext context = JAXBContext.newInstance(field.getType());
                    Unmarshaller um = context.createUnmarshaller();
                    Object o = um.unmarshal(new StringReader(XMLParser.getValue()));
                    field.set(field.getType(), o);
                }
            }
        }
    }
}

