package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Teacher;
public class TeacherListHandler extends DefaultHandler {
	private List<Teacher> teacherList = null;
	private Teacher teacher;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (teacher != null) { 
            String valueString = new String(ch, start, length); 
            if ("teacherNumber".equals(tempString)) 
            	teacher.setTeacherNumber(valueString); 
            else if ("teacherName".equals(tempString)) 
            	teacher.setTeacherName(valueString); 
            else if ("teacherPassword".equals(tempString)) 
            	teacher.setTeacherPassword(valueString); 
            else if ("teacherSex".equals(tempString)) 
            	teacher.setTeacherSex(valueString); 
            else if ("teacherBirthday".equals(tempString)) 
            	teacher.setTeacherBirthday(Timestamp.valueOf(valueString));
            else if ("teacherArriveDate".equals(tempString)) 
            	teacher.setTeacherArriveDate(Timestamp.valueOf(valueString));
            else if ("teacherCardNumber".equals(tempString)) 
            	teacher.setTeacherCardNumber(valueString); 
            else if ("teacherPhone".equals(tempString)) 
            	teacher.setTeacherPhone(valueString); 
            else if ("teacherPhoto".equals(tempString)) 
            	teacher.setTeacherPhoto(valueString); 
            else if ("teacherAddress".equals(tempString)) 
            	teacher.setTeacherAddress(valueString); 
            else if ("teacherMemo".equals(tempString)) 
            	teacher.setTeacherMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Teacher".equals(localName)&&teacher!=null){
			teacherList.add(teacher);
			teacher = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		teacherList = new ArrayList<Teacher>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Teacher".equals(localName)) {
            teacher = new Teacher(); 
        }
        tempString = localName; 
	}

	public List<Teacher> getTeacherList() {
		return this.teacherList;
	}
}
