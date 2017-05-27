package com.panov.ilya.jexchangerates.Models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import static com.panov.ilya.jexchangerates.Utils.Helpers.*;


/**
 * Created by ilya on 24.05.17.
 */

public class CRXmlParser {
    private String xml;
    private CRResultStatus status = CRResultStatus.NOT_COMPLETED;
    private CurrencyRates result;

    public CRXmlParser(String xml) {
        this.xml = xml;
    }

    public String getXml() {
        return xml;
    }

    public CRResultStatus getStatus() {
        return status;
    }

    public CurrencyRates getResult() {
        return result;
    }

    public void parse() {
        result = new CurrencyRates();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));

            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList currencyNodes = root.getElementsByTagName("Valute");

            if (currencyNodes.getLength() == 0) {
                status = CRResultStatus.PARAMS_ERROR;
                result = null;
                return;
            }

            result.date = DateTime.parse(root.getAttribute("Date"), DateTimeFormat.forPattern("dd.MM.yyyy"));
            result.name = root.getAttribute("name");

            for (int i = 0; i < currencyNodes.getLength(); i++) {
                Element currencyNode = (Element)currencyNodes.item(i);
                Currency currency = new Currency();
                Map<String, String> map = getValues(currencyNode, new String[] {
                   "NumCode", "CharCode", "Nominal", "Name", "Value"
                });

                currency.id = currencyNode.getAttribute("ID");
                currency.numCode = Integer.parseInt(map.get("NumCode"));
                currency.charCode = map.get("CharCode");
                currency.nominal = Integer.parseInt(map.get("Nominal"));
                currency.name = map.get("Name");
                currency.value = parseDouble(map.get("Value"), DecimalSeparator.COMMA);

                result.rates.add(currency);
            }

            status = CRResultStatus.SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
            status = CRResultStatus.PARSING_ERROR;
            result = null;
        }
    }

    private Map<String, String> getValues(Element element, String[] keys) {
        Map<String, String> result = new HashMap<>();

        for (String key : keys) {
            String value = element.getElementsByTagName(key).item(0).getTextContent();
            result.put(key, value);
        }

        return result;
    }
}
