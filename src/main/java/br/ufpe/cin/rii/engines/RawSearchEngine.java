package br.ufpe.cin.rii.engines;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class RawSearchEngine extends BasicSearchEngine
{
	public
	RawSearchEngine(String indexName)
	throws IOException
	{
		super(indexName, new StandardAnalyzer(
				new CharArraySet(new ArrayList<String>(), false)
		));
	}
}
