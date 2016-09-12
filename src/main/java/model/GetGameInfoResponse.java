package model;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import utils.XPathTools;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetGameInfoResponse {
    private static final Logger LOGGER = Logger.getLogger(GetGameInfoResponse.class);
    private String xml;

    private String sid;
    private XPathTools xpathTools;

    public GetGameInfoResponse(String xml) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        this.xml = xml;
        parseXML_SID();
        this.xpathTools = new XPathTools(xml);
    }

    private void parseXML_SID() throws ParserConfigurationException, IOException, SAXException {
        String pattern = "sid=\"([^\".]*)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(xml);
        if (m.find()) {
            sid = m.group(1);
        } else {
            LOGGER.warn("parseXML() : NO MATCH");
        }
    }

    @Override
    public String toString() {
        return "GetGameInfoResponse{" +
                "xml=" + xml +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public Map<String, Set<String>> getBuildingMap() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        Map<String, Set<String>> res = new HashMap<>();
        for (String id : xpathTools.getTextList("//building/id")) {
            xpathTools.getTextList("//building/id[text()=" + id + "]/../int_properties/property/value[text()!='0']/../type").
                    stream().filter(type -> !type.contains("upgrade") && !type.contains("counter")).forEach(type -> {
                res.putIfAbsent(id, new HashSet<>());
                res.get(id).add(type);
            });
        }
        return res;
    }

    public Map<String, Set<Map<String, String>>> getBuildingResStatistic() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        Map<String, Set<Map<String, String>>> res = new HashMap<>();
        XPathTools xpathTools = new XPathTools(xml);
        for (String id : xpathTools.getTextList("//building/id")) {
            for (String type : xpathTools.getTextList("//building/id[text()=" + id + "]/../int_properties/property/value[text()!='0']/../type")) {
                if (!type.contains("upgrade") && !type.contains("counter")) {
                    res.putIfAbsent(id, new HashSet<>());
                    HashMap resMap = new HashMap<>();
                    String value = xpathTools.getText("//building/id[text()=" + id + "]/../int_properties/property/type[text()='" + type + "']/../value");
                    resMap.put(type, value);
                    res.get(id).add(resMap);
                }
            }
        }
        return res;
    }

    public Map<String, String> getItemMap() throws XPathExpressionException {
        Map<String, String> res = new TreeMap<>();
        for (Node item : xpathTools.getList("//user/items/item")) {
            String type = String.valueOf(item.getAttributes().getNamedItem("type").getNodeValue());
            String value = String.valueOf(item.getAttributes().getNamedItem("value").getNodeValue());
            res.put(type, value);
        }
        return res;
    }
}
