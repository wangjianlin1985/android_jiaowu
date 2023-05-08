package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.CollegeInfo;
public class CollegeInfoListHandler extends DefaultHandler {
	private List<CollegeInfo> collegeInfoList = null;
	private CollegeInfo collegeInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (collegeInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("collegeNumber".equals(tempString)) 
            	collegeInfo.setCollegeNumber(valueString); 
            else if ("collegeName".equals(tempString)) 
            	collegeInfo.setCollegeName(valueString); 
            else if ("collegeBirthDate".equals(tempString)) 
            	collegeInfo.setCollegeBirthDate(Timestamp.valueOf(valueString));
            else if ("collegeMan".equals(tempString)) 
            	collegeInfo.setCollegeMan(valueString); 
            else if ("collegeTelephone".equals(tempString)) 
            	collegeInfo.setCollegeTelephone(valueString); 
            else if ("collegeMemo".equals(tempString)) 
            	collegeInfo.setCollegeMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("CollegeInfo".equals(localName)&&collegeInfo!=null){
			collegeInfoList.add(collegeInfo);
			collegeInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		collegeInfoList = new ArrayList<CollegeInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("CollegeInfo".equals(localName)) {
            collegeInfo = new CollegeInfo(); 
        }
        tempString = localName; 
	}

	public List<CollegeInfo> getCollegeInfoList() {
		return this.collegeInfoList;
	}
}
