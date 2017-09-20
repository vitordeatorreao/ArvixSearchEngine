package br.ufpe.cin.rii.apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufpe.cin.rii.engines.QueryResult;

public class CustomQueryApp extends BasicApp {

	
	public static void main(String[] args) throws ParseException, IOException, org.json.simple.parser.ParseException {
		
		CustomQueryApp app = new CustomQueryApp();
		app.initializeEngines();
        
        while(true){
        	QueryResult[] results = null;
        	try {
        		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        		System.out.print("Enter a query or type \"exit\" to finish\n");
        		String query = reader.readLine();
        		if("exit".equals(query.toLowerCase())){
        			System.exit(0);
        		}
        		if("".equals(query)){
        			continue;
        		}
        		int option = -1;
        		while(option < 1 || option > 4){
        			System.out.print("Choose a configuration:\n"
        					+ "1 - Without Stopwords removal and without stemming\n"
        					+ "2 - With Stopwords removal and without stemming\n"
        					+ "3 - Without Stopwords removal and with stemming\n"
        					+ "4 - With Stopwords removal and with stemming\n");
        			try{
        				option = Integer.parseInt(reader.readLine());
        				if(option < 1 || option > 4){
        					throw new NumberFormatException("Invalid parameter\n");
        				}
        				// Execute query
        				switch (option) {
        				case 1:
        					// 1 - Without Stopwords removal and without stemming
        					results = app.rawEngine.search("abstract", query);
        					break;
        				case 2:
        					// 2 - With Stopwords removal and without stemming
        					results = app.swEngine.search("abstract", query);
        					break;
        				case 3:
        					// 3 - Without Stopwords removal and with stemming
        					results = app.stEngine.search("abstract", query);
        					break;
        				case 4:
        					// 4 - With Stopwords removal and with stemming
        					results = app.fullEngine.search("abstract", query);
        					break;

        				default:
        					break;
        				}
        			}catch(NumberFormatException nfe) {
        				System.out.println("Invalid parameter");
        				option = -1;
        			}
        		}

        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
			}
        	
        	System.out.println(results.length + " documents found.");
        	System.out.println("Query results:\n");
        	for(QueryResult qr : results){
        		System.out.println("Title: " + qr.getDocument().getField("title").stringValue());
        		System.out.println("Authors: " + qr.getDocument().getField("author").stringValue());
        		System.out.println("Submission date: " + qr.getDocument().getField("date").stringValue());
        		System.out.println("Abstract: " + qr.getDocument().getField("abstract").stringValue().substring(0, 100) + "...\n\n");
        	}
        }
	}
}
