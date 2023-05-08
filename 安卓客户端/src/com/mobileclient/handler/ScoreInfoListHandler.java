package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ScoreInfo;
public class ScoreInfoListHandler extends DefaultHandler {
	private List<ScoreInfo> scoreInfoList = null;
	private ScoreInfo scoreInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (scoreInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("scoreId".equals(tempString)) 
            	scoreInfo.setScoreId(new Integer(valueString).intValue());
            else if ("studentNumber".equals(tempString)) 
            	scoreInfo.setStudentNumber(valueString); 
            else if ("courseNumber".equals(tempString)) 
            	scoreInfo.setCourseNumber(valueString); 
            else if ("scoreValue".equals(tempString)) 
            	scoreInfo.setScoreValue(new Float(valueString).floatValue());
            else if ("studentEvaluate".equals(tempString)) 
            	scoreInfo.setStudentEvaluate(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ScoreInfo".equals(localName)&&scoreInfo!=null){
			scoreInfoList.add(scoreInfo);
			scoreInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		scoreInfoList = new ArrayList<ScoreInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ScoreInfo".equals(localName)) {
            scoreInfo = new ScoreInfo(); 
        }
        tempString = localName; 
	}

	public List<ScoreInfo> getScoreInfoList() {
		return this.scoreInfoList;
	}
}
