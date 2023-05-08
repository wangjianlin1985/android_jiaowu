package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SpecialFieldInfo;
public class SpecialFieldInfoListHandler extends DefaultHandler {
	private List<SpecialFieldInfo> specialFieldInfoList = null;
	private SpecialFieldInfo specialFieldInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (specialFieldInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("specialFieldNumber".equals(tempString)) 
            	specialFieldInfo.setSpecialFieldNumber(valueString); 
            else if ("specialFieldName".equals(tempString)) 
            	specialFieldInfo.setSpecialFieldName(valueString); 
            else if ("specialCollegeNumber".equals(tempString)) 
            	specialFieldInfo.setSpecialCollegeNumber(valueString); 
            else if ("specialBirthDate".equals(tempString)) 
            	specialFieldInfo.setSpecialBirthDate(Timestamp.valueOf(valueString));
            else if ("specialMan".equals(tempString)) 
            	specialFieldInfo.setSpecialMan(valueString); 
            else if ("specialTelephone".equals(tempString)) 
            	specialFieldInfo.setSpecialTelephone(valueString); 
            else if ("specialMemo".equals(tempString)) 
            	specialFieldInfo.setSpecialMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SpecialFieldInfo".equals(localName)&&specialFieldInfo!=null){
			specialFieldInfoList.add(specialFieldInfo);
			specialFieldInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		specialFieldInfoList = new ArrayList<SpecialFieldInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SpecialFieldInfo".equals(localName)) {
            specialFieldInfo = new SpecialFieldInfo(); 
        }
        tempString = localName; 
	}

	public List<SpecialFieldInfo> getSpecialFieldInfoList() {
		return this.specialFieldInfoList;
	}
}
