package com.Conor.Final;
import 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class ReaderXML {

	public static void main(String argv[]) {

		try {
			// need to add String url=http://jsx.monster.com/query.ashx
			// bfba883a8457bd797debf24a3c820acd api key
			
			File fXmlFile = new File("NewFile.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			//NodeList nList = doc.getFirstChild().getNodeName();
			

			System.out.println("----------------------------:" + doc.getElementsByTagName("category").item(0));
			//System.out.println(nList.item(0).getChildNodes().item(0).getNodeName());
			//for (int count = 0; count < nList.getLength(); count++) {

				//Node nNode = nList.item(count);
				
				//NodeList nListing = nNode.getChildNodes();
				
//				for (int count2 = 0; count2 < nListing.getLength(); count2++ ){
//					
//					Node title = nListing.item(count2);
//					System.out.println(title);
//				}

				//System.out.println("\nCurrent Element :" + nNode.);

				//if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					//Element eElement = (Element) nNode;

//					System.out.println(
//							"Company Name : " + eElement.getElementsByTagName("").item(0).getTextContent());
//					System.out.println(
//							"Location : " + eElement.getElementsByTagName("state").item(0).getTextContent());
//					System.out.println(
//							"Job Title : " + eElement.getElementsByTagName("city").item(0).getTextContent());

				//}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}