package br.ufpe.cin.rii.engines;

import java.io.IOException;

import org.apache.lucene.analysis.en.EnglishAnalyzer;

public class FullSearchEngine extends BasicSearchEngine
{
    public
    FullSearchEngine(String indexName)
    throws IOException
    {
        super(indexName, new EnglishAnalyzer());
    }
}
