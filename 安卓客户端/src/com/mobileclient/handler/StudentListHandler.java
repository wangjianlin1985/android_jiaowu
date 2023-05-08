package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Student;
public class StudentListHandler extends DefaultHandler {
	private List<Student> studentList = null;
	private Student student;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (student != null) { 
            String valueString = new String(ch, start, length); 
            if ("studentNumber".equals(tempString)) 
            	student.setStudentNumber(valueString); 
            else if ("studentName".equals(tempString)) 
            	student.setStudentName(valueString); 
            else if ("studentPassword".equals(tempString)) 
            	student.setStudentPassword(valueString); 
            else if ("studentSex".equals(tempString)) 
            	student.setStudentSex(valueString); 
            else if ("studentClassNumber".equals(tempString)) 
            	student.setStudentClassNumber(valueString); 
            else if ("studentBirthday".equals(tempString)) 
            	student.setStudentBirthday(Timestamp.valueOf(valueString));
            else if ("studentState".equals(tempString)) 
            	student.setStudentState(valueString); 
            else if ("studentPhoto".equals(tempString)) 
            	student.setStudentPhoto(valueString); 
            else if ("studentTelephone".equals(tempString)) 
            	student.setStudentTelephone(valueString); 
            else if ("studentEmail".equals(tempString)) 
            	student.setStudentEmail(valueString); 
            else if ("studentQQ".equals(tempString)) 
            	student.setStudentQQ(valueString); 
            else if ("studentAddress".equals(tempString)) 
            	student.setStudentAddress(valueString); 
            else if ("studentMemo".equals(tempString)) 
            	student.setStudentMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Student".equals(localName)&&student!=null){
			studentList.add(student);
			student = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		studentList = new ArrayList<Student>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Student".equals(localName)) {
            student = new Student(); 
        }
        tempString = localName; 
	}

	public List<Student> getStudentList() {
		return this.studentList;
	}
}
