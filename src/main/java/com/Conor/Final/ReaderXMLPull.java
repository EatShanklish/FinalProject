package com.Conor.Final;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.Shanklish.Controller.job;

public class ReaderXMLPull {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://www.postjobfree.com/RssJob.ashx?q=java&l=detroit%2c+MI&radius=25"); // Reading
		URLConnection yc = url.openConnection();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line, json = "";
		//while ((line = bReader.readLine()) != null) {
		//	json += line;
		//}
		//bReader.close();

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xmlParser = factory.newPullParser();
			
			
			//How to write to file as a String? Ask Peter
			//FileReader reader = new FileReader(json);
			//xmlParser.setInput(reader);
			xmlParser.setInput(bReader);
			
			int eventType = xmlParser.getEventType();
			job job1 = new job();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					//System.out.println("Start document");
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					//System.out.println("End document");
				} else if (eventType == XmlPullParser.START_TAG) {
					//System.out.println(xmlParser.getName());
					getJob(xmlParser, job1);
				} else if (eventType == XmlPullParser.END_TAG) {
					//System.out.println(xmlParser.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					System.out.println(xmlParser.getText());
				}
				eventType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void getJob(XmlPullParser xmlParser, job job1) {
		// TODO Auto-generated method stub
		switch (xmlParser.getName()) {
		case "listings":

			break;

		case "listing":
			job1.setJobTitle(xmlParser.getAttributeValue("", "title"));
			System.out.println(xmlParser.getAttributeValue("", "title"));
			break;

		case "company":
			job1.setCompany(xmlParser.getAttributeValue("", "name"));
			job1.setUrl(xmlParser.getAttributeValue("", "url"));
			System.out.println(xmlParser.getAttributeValue("", "name"));
			System.out.println(xmlParser.getAttributeValue("", "url"));
			break;

		case "location":
			job1.setLocation(xmlParser.getAttributeValue("", "city"));
			job1.setLocation(xmlParser.getAttributeValue("", "country"));
			//System.out.println(xmlParser.getAttributeValue("", "city"));
			//System.out.println(xmlParser.getAttributeValue("", "country"));
			break;

		default:
			break;
		}
	}

}
