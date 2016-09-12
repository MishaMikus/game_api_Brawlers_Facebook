package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XPathTools {
    private DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    private XPath xPath = XPathFactory.newInstance().newXPath();
    private Document doc;

    public XPathTools(String xml) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public String getText(String xpathExpresion) throws XPathExpressionException {
        XPathExpression expr = xPath.compile(xpathExpresion);
        return (String) expr.evaluate(doc, XPathConstants.STRING);
    }

    public List<String> getTextList(String xpathExpresion) throws XPathExpressionException {
        List<String> res = new ArrayList<>();
        NodeList nodeList = (NodeList) xPath.compile(xpathExpresion).evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            res.add(nodeList.item(i).getFirstChild().getNodeValue());
        }
        return res;
    }

    public List<Node> getList(String xpathExpresion) throws XPathExpressionException {
        List<Node> res = new ArrayList<>();
        NodeList nodeList = (NodeList) xPath.compile(xpathExpresion).evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            res.add(nodeList.item(i));
        }
        return res;
    }

}
