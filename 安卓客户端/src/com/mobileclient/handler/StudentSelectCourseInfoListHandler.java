package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.StudentSelectCourseInfo;
public class StudentSelectCourseInfoListHandler extends DefaultHandler {
	private List<StudentSelectCourseInfo> studentSelectCourseInfoList = null;
	private StudentSelectCourseInfo studentSelectCourseInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (studentSelectCourseInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("selectId".equals(tempString)) 
            	studentSelectCourseInfo.setSelectId(new Integer(valueString).intValue());
            else if ("studentNumber".equals(tempString)) 
            	studentSelectCourseInfo.setStudentNumber(valueString); 
            else if ("courseNumber".equals(tempString)) 
            	studentSelectCourseInfo.setCourseNumber(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("StudentSelectCourseInfo".equals(localName)&&studentSelectCourseInfo!=null){
			studentSelectCourseInfoList.add(studentSelectCourseInfo);
			studentSelectCourseInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		studentSelectCourseInfoList = new ArrayList<StudentSelectCourseInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("StudentSelectCourseInfo".equals(localName)) {
            studentSelectCourseInfo = new StudentSelectCourseInfo(); 
        }
        tempString = localName; 
	}

	public List<StudentSelectCourseInfo> getStudentSelectCourseInfoList() {
		return this.studentSelectCourseInfoList;
	}
}
