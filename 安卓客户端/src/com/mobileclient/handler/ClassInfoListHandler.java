package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ClassInfo;
public class ClassInfoListHandler extends DefaultHandler {
	private List<ClassInfo> classInfoList = null;
	private ClassInfo classInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (classInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("classNumber".equals(tempString)) 
            	classInfo.setClassNumber(valueString); 
            else if ("className".equals(tempString)) 
            	classInfo.setClassName(valueString); 
            else if ("classSpecialFieldNumber".equals(tempString)) 
            	classInfo.setClassSpecialFieldNumber(valueString); 
            else if ("classBirthDate".equals(tempString)) 
            	classInfo.setClassBirthDate(Timestamp.valueOf(valueString));
            else if ("classTeacherCharge".equals(tempString)) 
            	classInfo.setClassTeacherCharge(valueString); 
            else if ("classTelephone".equals(tempString)) 
            	classInfo.setClassTelephone(valueString); 
            else if ("classMemo".equals(tempString)) 
            	classInfo.setClassMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ClassInfo".equals(localName)&&classInfo!=null){
			classInfoList.add(classInfo);
			classInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		classInfoList = new ArrayList<ClassInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ClassInfo".equals(localName)) {
            classInfo = new ClassInfo(); 
        }
        tempString = localName; 
	}

	public List<ClassInfo> getClassInfoList() {
		return this.classInfoList;
	}
}
