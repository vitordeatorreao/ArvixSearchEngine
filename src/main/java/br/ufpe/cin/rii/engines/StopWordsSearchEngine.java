package br.ufpe.cin.rii.engines;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class StopWordsSearchEngine extends BasicSearchEngine
{
    public
    StopWordsSearchEngine(String indexName)
    throws IOException
    {
        super(indexName, new StandardAnalyzer());
    }
}
