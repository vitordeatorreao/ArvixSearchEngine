package br.ufpe.cin.rii.engines;

import org.apache.lucene.document.Document;

public final class QueryResult implements Comparable<QueryResult>
{
	private Document document;
	private float score;
	
	public
	QueryResult(Document doc,
			    float score)
	{
		this.document = doc;
		this.score = score;
	}
	
	public Document
	getDocument()
	{
		return this.document;
	}
	
	public float
	getScore()
	{
		return this.score;
	}

	public int
	compareTo(QueryResult result)
	{
		if (this.score < result.score) return -1;
		else if (this.score == result.score) return 0;
		else return 1;
	}
}
