package dk.esmann.DroidBugz.FogBugz;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {

    public static String getString(Document doc, String tagName) throws Exception {
        NodeList values = doc.getDocumentElement().getElementsByTagName(tagName);
        if (values.getLength() == 1) {
            return values.item(0).getTextContent();
        }
        throw new Exception("more than one " + tagName + " elements"); // TODO create custom exception
    }

    public static Integer getInteger(Document doc, String tagName) throws Exception {
        NodeList values = doc.getDocumentElement().getElementsByTagName(tagName);
        if (values.getLength() == 1) {
            return Integer.parseInt(values.item(0).getTextContent());  // TODO NumberFormatExceptionHandling
        }
        throw new Exception("more than one " + tagName + " elements"); // TODO create custom exception
    }

    public static String getStringValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

    public static Integer getIntegerValue(String tag, Element element) {
        return Integer.parseInt(getStringValue(tag, element), 10);
    }
}
