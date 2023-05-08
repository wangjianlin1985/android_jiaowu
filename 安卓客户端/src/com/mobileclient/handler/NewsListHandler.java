package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.News;
public class NewsListHandler extends DefaultHandler {
	private List<News> newsList = null;
	private News news;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (news != null) { 
            String valueString = new String(ch, start, length); 
            if ("newsId".equals(tempString)) 
            	news.setNewsId(new Integer(valueString).intValue());
            else if ("newsTitle".equals(tempString)) 
            	news.setNewsTitle(valueString); 
            else if ("newsContent".equals(tempString)) 
            	news.setNewsContent(valueString); 
            else if ("newsDate".equals(tempString)) 
            	news.setNewsDate(Timestamp.valueOf(valueString));
            else if ("newsPhoto".equals(tempString)) 
            	news.setNewsPhoto(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("News".equals(localName)&&news!=null){
			newsList.add(news);
			news = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		newsList = new ArrayList<News>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("News".equals(localName)) {
            news = new News(); 
        }
        tempString = localName; 
	}

	public List<News> getNewsList() {
		return this.newsList;
	}
}
