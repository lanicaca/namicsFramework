package annotations;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

//singleton
public class XMLParser {

    private static final String CONFIG_FILE = "/config.xml";
    private static final Logger log = Logger.getLogger(XMLParser.class);
    @Getter
    private static XMLParser instance = null;
    InputStream inputFile;
    @Getter
    private XPath xPath;
    @Getter
    private Document doc;
    @Getter
    @Setter
    private String parsedValue;
    @Getter
    private Node myNode;

    private XMLParser(Class c) {
        try {
            //XPath definition and initialization
            InputStream inputFile = c.getResourceAsStream(CONFIG_FILE);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            xPath = XPathFactory.newInstance().newXPath();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Couldn't initialized XMLParser - file doesn't exist or file path is wrong : ", e);

        }
    }

    public static XMLParser getInstance(Class c) {
        if (instance == null) {
            instance = new XMLParser(c);
        }
        return instance;
    }

    // make string exactly the same as in .xml file
    public StringBuffer getTextValueOfXmlNode(Node node) {
        //String buffer contains <node_name>
        StringBuffer textValue = new StringBuffer("<" + node.getNodeName() + ">");
        for (int j = 0; j < node.getChildNodes().getLength(); j++) {
            //for each subnode add <key>value</key> to string buffer
            Node curr = node.getChildNodes().item(j);
            //skipp the "#text" nodes
            if (!curr.getNodeName().equals("#text")) {
                textValue.append("<").append(curr.getNodeName()).append(">").
                        append(curr.getTextContent()).append("</").
                        append(curr.getNodeName()).append(">");
            }
        }
        //close opened tag with </node_name>
        textValue.append("</").append(node.getNodeName()).append(">");

        return textValue;
    }

    //check if attribute is complex and put it's value in "value"
    public boolean IsComplex(String key) {
        NodeList myNodeList = null;
        try {
            myNodeList = ((NodeList) xPath
                    .compile("entries")
                    .evaluate(doc, XPathConstants.NODESET))
                    .item(0)
                    .getChildNodes();
        } catch (XPathExpressionException e) {
            log.error("Xml entries must be under tag <entries>", e);
        }

        for (int i = 0; i < myNodeList.getLength(); i++) {
            Node n = myNodeList.item(i);
            //find the key in xml which is given in function
            if (key.equals(n.getNodeName())) {
                // is complex check
                if (n.getChildNodes().getLength() > 1) {
                    // get the subnode of complex node
                    n = n.getChildNodes().item(0).getNextSibling();
                    // value is string xml code of that exact node , see function below
                    parsedValue = getTextValueOfXmlNode(n).toString();
                    return true; // is complex
                }
                parsedValue = n.getTextContent(); //get the value of key node
                return false; // is simple
            }
        }
        parsedValue = null;
        return false;
    }

}
