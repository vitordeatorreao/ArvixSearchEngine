package br.ufpe.cin.rii.engines;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

public interface SearchEngine
{	
	public void
	addDocument(Document doc)
	throws IOException;
	
	public void
	commit()
	throws IOException;
	
	public QueryResult[]
	search(String fieldName,
		   String text)
	throws ParseException, IOException;
	
	public QueryResult[]
	search(String fieldName,
		   String text,
		   int n)
	throws ParseException, IOException;
	
	public void
	close()
	throws IOException;
}
