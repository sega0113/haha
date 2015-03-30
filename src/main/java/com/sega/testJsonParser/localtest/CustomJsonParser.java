package com.sega.testJsonParser.localtest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.hl7.fhir.instance.formats.IParser;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.formats.XmlParser;
import org.hl7.fhir.instance.model.Narrative;
import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.hl7.fhir.utilities.xhtml.XhtmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class CustomJsonParser extends XmlParser {

	public org.hl7.fhir.utilities.xhtml.XhtmlNode getXhtmlNode(Resource resource) throws JsonSyntaxException, Exception{
		IParser xmlParser = new XmlParser();
		System.out.println(xmlParser.composeString(resource));
		XmlPullParser xpp = loadXml(new ByteArrayInputStream(xmlParser.composeBytes(resource)));
		XhtmlParser p = new XhtmlParser();
		XhtmlNode xml = p.parseHtmlNode(xpp);
		System.out.println(xml.allText());
		System.out.println(xml.getContent());
		return xml;
	}
	
}
