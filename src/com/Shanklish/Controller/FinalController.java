package com.Shanklish.Controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jdt.internal.compiler.parser.RecoveredAnnotation;

@Controller
public class FinalController 
{
	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model) throws ClientProtocolException, IOException, ParseException
	{
		ArrayList <String> list = new ArrayList<String>();
		
		for(int i =0; i < diceJobSearch().size(); i++)
		{
			String title = diceJobSearch().get(i).getJobTitle()+diceJobSearch().get(i).getCompany()+diceJobSearch().get(i).getLocation();
			list.add(title);
		}
		model.addAttribute("array",list);
		
		return new ModelAndView("welcome","message","huh");
	}
	
	
	
	public ArrayList<job> diceJobSearch() throws ClientProtocolException, IOException, ParseException
	{
		String url = "http://service.dice.com/api/rest/jobsearch/v1/simple.json?text=java&state=MI";

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
				newjob.setUrl((String)job.get("detailURL"));
				
				diceJobArray.add(newjob);
				
		    }
		
		return diceJobArray;
	}
}
