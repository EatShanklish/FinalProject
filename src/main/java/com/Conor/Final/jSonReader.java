package com.Conor.Final;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jdt.internal.compiler.parser.RecoveredAnnotation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class jSonReader
    {

	public static void main( String[] args ) throws FileNotFoundException, IOException, org.json.simple.parser.ParseException 
	{
		//create user input in varaible 
		//so concat the variable into location along with the rest of api
		//leave blanks in the spot where you want user input
		//^^
	    	String url = "http://service.dice.com/api/rest/jobsearch/v1/simple.json?text=java";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
	    
	 
	    //------------------------------------------
		
		JSONParser parser = new JSONParser();
		Object object = parser.parse(result.toString());
		
		JSONObject jsonObject = (JSONObject) object;
		
		//JSONObject data = (JSONObject)jsonObject.get("data");
		JSONArray posts = (JSONArray)jsonObject.get("resultItemList");
		
		
		Iterator<JSONObject> iterator = posts.iterator();
		int count =0;
		
		while(iterator.hasNext())
		    {
			JSONObject job = iterator.next();
			String jTitle = (String)job.get("jobTitle");
			System.out.println(jTitle);			
			
			String company = (String)job.get("company");
			System.out.println(company);
			
			String location = (String)job.get("location");
			System.out.println(location);	
			count++;

		    }
		
		System.out.println(count);
	}
	
    }