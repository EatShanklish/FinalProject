package com.Shanklish.Controller;


import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.catalina.connector.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
public class FinalController 
{
	
	private static SessionFactory factory;

	//--------------------------------------HIBERNATE CONFIGURATION----------------------------------
	private static void setupFactory() 
	{
		try {
				Class.forName("com.mysql.jdbc.Driver");
			} 
		
		catch (Exception e) 
		{
			;//this is silliness!
		}
	    
		 Configuration configuration = new Configuration();

		 // Pass hibernate configuration file
		 configuration.configure("hibernate.cfg.xml");
		 
		 // pass in setup file for Product class
		 configuration.addResource("users.hbm.xml");
		 
		 // Since version 4.x, service registry is being used
		 ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
		 applySettings(configuration.getProperties()).build(); 

		 // Create session factory instance
		 factory = configuration.buildSessionFactory(serviceRegistry);

	}
	
	//--------------------------------------RETRIEVES LIST OF USERS----------------------------------
	public static List<User> getAllUsers(){
		if (factory == null)
			setupFactory();
		 // Get current session
		 Session hibernateSession = factory.openSession();

		 // Begin transaction
		 hibernateSession.getTransaction().begin();
		 
		 //deprecated method & unsafe cast
         List<User> users = hibernateSession.createQuery("FROM User").list(); 
		 
         // Commit transaction
         hibernateSession.getTransaction().commit();
      		 
      	 hibernateSession.close();  
      	System.out.println(users.size());	    
		return users;
		
	}
	
	
	//--------------------------------------LOGIN CREDENTIAL HANDLER----------------------------------
	@RequestMapping("/login")
	public String submitLogin(@ModelAttribute("command") User user, Model model) 
	{
		model.addAttribute("email", user.getEmail());
		model.addAttribute("password", user.getPassword());
		return "login";
	}
	
	
	//-------------------------------RETRIEVES LIST OF USERS FROM DB-----------------------------------------
	@RequestMapping("/listUsers")
	public ModelAndView getUsers(Model model) 
	{
		
		return new ModelAndView("listUsers");
		
	}

	
	//							addUser() METHOD WILL GO HERE. IT WILL ENCRYPT PASSWORDS AND SAVE TO DB
	
	
	//--------------------------------RETRIEVES AND DISPLAYS ALL QUERIED JOBS FROM VARIOUS APIS---------------------------------------
	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model,@RequestParam("query") String keyword,@RequestParam("state") String location) throws ClientProtocolException, IOException, ParseException
	{
		
		ArrayList<job> jobList = diceJobSearch(keyword,location);
		ArrayList<job> indeedJobList = indeedJobSearch(keyword,location);
		

		
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
			
		
		return new ModelAndView("welcome","message","Jobs matching " + keyword +" in " + location);
	}
	
	
	//--------------------------------------DICE JOB PARSER----------------------------------
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

	
	
	//--------------------------------------INDEED JOB PARSER----------------------------------
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


//--------------------------------------PASSWORD ENCRYPTION----------------------------------
private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
{
    int iterations = 1000;
    char[] chars = password.toCharArray();
    byte[] salt = getSalt();
     
    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = skf.generateSecret(spec).getEncoded();
    return iterations + ":" + toHex(salt) + ":" + toHex(hash);
}

private static byte[] getSalt() throws NoSuchAlgorithmException
{
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt;
}
 
private static String toHex(byte[] array) throws NoSuchAlgorithmException
{
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if(paddingLength > 0)
    {
        return String.format("%0"  +paddingLength + "d", 0) + hex;
    }
    else
    {
        return hex;
    }
}



//--------------------------------------PASSWORD VALIDATION----------------------------------

@RequestMapping("/verifyPassword")
public ModelAndView VerifyPassword(@RequestParam("password") String pWord,@RequestParam("email") String eMail) throws NoSuchAlgorithmException, InvalidKeySpecException
{
	String storedPass= getStoredPassword(eMail, pWord);
	
	boolean matched = validatePassword(pWord, storedPass);
	
	System.out.println(matched);
	
	if(matched==true)
		return new ModelAndView("welcome", "message", "welcome back BUT You should not have been redirected here. TO-DO, asshole");
	else
		return new ModelAndView(new RedirectView("login.html"));
	
}

public String getStoredPassword(String userEmail, String iPass)
{
	 
	if (factory == null)
		setupFactory();
	
	 // Get current session
	 Session hibernateSession = factory.openSession();

	 // Begin transaction
	 hibernateSession.getTransaction().begin();
	 
	 
	 //deprecated method & unsafe cast
	 List<String> query = hibernateSession.createQuery("select password from com.Shanklish.Controller.User where email = '"+userEmail+"'").list();
	 
	 
	 String pass = query.get(0);
	 
	
     // Commit transaction
     hibernateSession.getTransaction().commit();
  		 
  	 hibernateSession.close();  
  				    
	return pass;	
}

private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
{
	
	System.out.println(originalPassword + " " + storedPassword);
	
    String[] parts = storedPassword.split(":");
    int iterations = Integer.parseInt(parts[0]);
    byte[] salt = fromHex(parts[1]);
    byte[] hash = fromHex(parts[2]);
    
   
     
    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] testHash = skf.generateSecret(spec).getEncoded();
     
    int diff = hash.length ^ testHash.length;
    for(int i = 0; i < hash.length && i < testHash.length; i++)
    {
        diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
}

private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
{
    byte[] bytes = new byte[hex.length() / 2];
    for(int i = 0; i<bytes.length ;i++)
    {
        bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bytes;
}

//--------------------------------------SORTING----------------------------------
class LexicographicComparator implements Comparator<job> 
{
    @Override
    public int compare(job a, job b) 
    {
        return a.getLocation().compareToIgnoreCase(b.getLocation());
    }
}

}
