package br.ufpe.cin.rii.engines;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

public class StemmingSearchEngine extends BasicSearchEngine
{
    public
    StemmingSearchEngine(String indexName)
    throws IOException
    {
        super(indexName, new EnglishAnalyzer(
                new CharArraySet(new ArrayList<String>(), false)
        ));
    }
}
