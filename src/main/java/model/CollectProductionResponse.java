package model;

import org.xml.sax.SAXException;
import utils.XPathTools;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class CollectProductionResponse {
    private String xml;

    public CollectProductionResponse(String xml) throws IOException, SAXException, ParserConfigurationException {
        this.xml = xml;
    }

    public String getCollectValue() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        XPathTools xpathTools = new XPathTools(xml);
        return xpathTools.getText("//events/event/count");
    }
}
