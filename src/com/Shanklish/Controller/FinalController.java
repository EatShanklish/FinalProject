package com.Shanklish.Controller;


import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.catalina.connector.Request;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jdt.internal.compiler.ast.ContinueStatement;
import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FinalController 
{
	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model,@RequestParam("query") String keyword,@RequestParam("state") String location) throws ClientProtocolException, IOException, ParseException
	{
		
		ArrayList<job> jobList = diceJobSearch(keyword,location);
		ArrayList<job> indeedJobList = indeedJobSearch(keyword,location);
		//ArrayList<job> usaJobList = usaJobSearch(keyword, location);

		
		model.addAttribute("array",jobList);
		
			for(int i =0; i < jobList.size(); i++)
				{
					
					model.addAttribute("jobListCompanyName", jobList.get(i).getCompany());
					model.addAttribute("jobListLocation", jobList.get(i).getLocation());	
					model.addAttribute("jobListjobTitle",jobList.get(i).getJobTitle());
					model.addAttribute("jobUrl", jobList.get(i).getUrl());
					
				}
			
		
		model.addAttribute("indeedArray", indeedJobList);
		
			for(int i=0; i< indeedJobList.size();i++)
				{
					model.addAttribute("indeedJobTitle",indeedJobList.get(i).getJobTitle());
					model.addAttribute("indeedCompanyName", indeedJobList.get(i).getCompany());
					model.addAttribute("indeedLocation", indeedJobList.get(i).getLocation());
					model.addAttribute("indeedURL", indeedJobList.get(i).getLocation());
				}
			
		/*model.addAttribute("usaArray", usaJobList);
			
			for(int i=0; i< indeedJobList.size();i++)
			{
				model.addAttribute("usaJobTitle",indeedJobList.get(i).getJobTitle());
				model.addAttribute("usaCompanyName", indeedJobList.get(i).getCompany());
				model.addAttribute("usaLocation", indeedJobList.get(i).getLocation());
				model.addAttribute("usaURL", indeedJobList.get(i).getLocation());
			}*/
			
		
		
		return new ModelAndView("welcome","message","JOBS");
	}
	
	
	//Dice Job Parser - Returns ArrayList of Jobs from Dice
	@RequestMapping("dice")
	public ArrayList<job> diceJobSearch(String pKeyword, String pLocation) throws ClientProtocolException, IOException, ParseException

	{
		String keyword = pKeyword.replaceAll("\\s","+"); //encode(String s, String enc)
		String location = pLocation.replaceAll("\\s","+");
		
		String url = "http://service.dice.com/api/rest/jobsearch/v1/simple.json?text="+keyword+"&state="+location+"";
	
		HttpClient client = HttpClientBuilder.create().build();
		
		HttpGet request = new HttpGet(url);
		
		
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		while ((line = rd.readLine()) != null) 
		{
			result.append(line);
		}
	    
		
	 
	    //------------------------------------------
		
		JSONParser parser = new JSONParser();
		Object object = parser.parse(result.toString());
		
		JSONObject jsonObject = (JSONObject) object;
		
		
		JSONArray posts = (JSONArray)jsonObject.get("resultItemList");
		
		
		Iterator<JSONObject> iterator = posts.iterator();
		
		ArrayList<job> diceJobArray = new ArrayList<job>();
		
		
		job newjob = null;
												
		while(iterator.hasNext())
		    {
				newjob = new job();
				
				JSONObject job = iterator.next();
				
				newjob.setJobTitle((String)job.get("jobTitle"));
				newjob.setCompany((String)job.get("company"));
				newjob.setLocation((String)job.get("location"));
				newjob.setUrl((String)job.get("detailUrl"));
				
				diceJobArray.add(newjob);
				
		    }
		
		
		
		return diceJobArray;
	}

	//Indeed Job Parser - Returns ArrayList of Jobs from Indeed
public ArrayList<job> indeedJobSearch(String pkeyword, String plocation) throws ClientProtocolException, IOException, ParseException
{
	String keyword = pkeyword.replaceAll("\\s","+");
	String location = plocation.replaceAll("\\s","+");
	String url = "http://api.indeed.com/ads/apisearch?publisher=2945076701195809&q="+keyword+"&l="+location+"&format=json&sort=&radius=&st=&jt=&start=&limit=50&fromage=&filter=&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	

	HttpClient client = HttpClientBuilder.create().build();
	HttpGet request = new HttpGet(url);

	HttpResponse response = client.execute(request);


	
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
	
	
	JSONArray posts = (JSONArray)jsonObject.get("results");
	
	
	Iterator<JSONObject> iterator = posts.iterator();
	
	ArrayList<job> indeedJobArray = new ArrayList<job>();
	
	
	job newjob = null;
											//TO-DO - Need to make URL print alongside job.
	while(iterator.hasNext())
	    {
			newjob = new job();
			
			JSONObject job = iterator.next();
			
			newjob.setJobTitle((String)job.get("jobtitle"));
			newjob.setCompany((String)job.get("company"));
			newjob.setLocation((String)job.get("formattedLocation"));
			newjob.setUrl((String)job.get("url"));
			
			indeedJobArray.add(newjob);
			
	    }
	
	return indeedJobArray;	
}

/*
public ArrayList<job> usaJobSearch(String pKeyword, String pLocation) throws ClientProtocolException, IOException, ParseException 
{
	String keyword = pKeyword.replaceAll("\\s","+");
	String location = pLocation.replaceAll("\\s","+");
	
	String url = "https://data.usajobs.gov/api/search?keyword="+keyword+"";
	

	HttpClient client = HttpClientBuilder.create().build();
	
	HttpGet request = new HttpGet(url);
	
	request.setHeader("Host", "data.usajobs.gov");
	request.setHeader("User-Agent", "hussein.m.abrahim@gmail.com");
	request.setHeader("Authorization-Key", "8xKGRy1e4ky7M/0JY75hiQWajtYZT7751NYrV7aEExI=");

	HttpResponse response = client.execute(request);

	
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
	
	JSONObject posts = (JSONObject)jsonObject.get("SearchResult");
	
	JSONArray object2 = (JSONArray) posts.get("SearchResultItems");
	Iterator<JSONObject> iterator = object2.iterator();
	
	
	//Iterator<JSONObject> iterator2 = object3.iterator();
	
	ArrayList<job> usaJobArray = new ArrayList<job>();
	
	job newjob = null;
	
	while(iterator.hasNext())
	    {
			newjob = new job();
			
			JSONObject job = iterator.next();
			
			JSONObject fuck =(JSONObject) job.get("MatchedObjectDescriptor");
			newjob.setJobTitle((String)fuck.get("PositionTitle"));
			newjob.setUrl((String)fuck.get("PositionURI"));		

			JSONArray object3 = (JSONArray)job.get("PositionLocation");
			JSONObject usaLocation = (JSONObject)object3.get(0);
			String ayre = ((String)usaLocation.get("LocationName"));
			
			newjob.setLocation(ayre);
			
			newjob.setCompany((String)job.get("OrganizationName"));
			
			
			usaJobArray.add(newjob);
			
	    }
	
	return usaJobArray;	
}
*/

class LexicographicComparator implements Comparator<job> 
{
    @Override
    public int compare(job a, job b) 
    {
        return a.getLocation().compareToIgnoreCase(b.getLocation());
    }
}

}
