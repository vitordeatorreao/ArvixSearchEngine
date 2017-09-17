package br.ufpe.cin.rii.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufpe.cin.rii.engines.QueryResult;
import br.ufpe.cin.rii.engines.SearchEngine;
import br.ufpe.cin.rii.engines.StemmingSearchEngine;
import br.ufpe.cin.rii.test.utils.FolderUtils;

public class StemmingSearchEngineTest
{
	private static final String TEST_FOLDER = "testindex_st";
	private SearchEngine engine;

	@Before
	public void
	setUp()
	throws Exception
	{
		this.engine = new StemmingSearchEngine(TEST_FOLDER);
	}

	@After
	public void
	tearDown()
	throws Exception
	{
		this.engine.close();
		FolderUtils.DeleteRecursive("indexes/" + TEST_FOLDER);
	}

	@Test
	public void
	test()
	throws IOException, ParseException
	{
		Document doc = new Document();
        String text = "Evaluating features is not easy.";
        doc.add(new Field("body", text, TextField.TYPE_STORED));
        Document doc2 = new Document();
        String text2 = "Measuring attributes is easy.";
        doc2.add(new Field("body", text2, TextField.TYPE_STORED));
        this.engine.addDocument(doc);
        this.engine.addDocument(doc2);
        this.engine.commit();
        // test no return
        QueryResult[] result = this.engine.search("body", "absolute");
        assertEquals(0, result.length);
        // test query with stop words
        result = this.engine.search("body", "is");
        assertEquals(2, result.length);
        // test query for specific word
        result = this.engine.search("body", "attributes");
        assertEquals(1, result.length);
        assertEquals(text2,
        		     result[0].getDocument().getField("body").stringValue());
        result = this.engine.search("body", "evaluation");
        assertEquals(1, result.length);
        assertEquals(text,
   		     		 result[0].getDocument().getField("body").stringValue());
        result = this.engine.search("body", "measure");
        assertEquals(1, result.length);
        assertEquals(text2,
   		     		 result[0].getDocument().getField("body").stringValue());
        // test ranking
        result = this.engine.search("body", "is measure");
        assertEquals(2, result.length);
        assertEquals(text2,
        			 result[0].getDocument().getField("body").stringValue());
        assertEquals(text,
        			 result[1].getDocument().getField("body").stringValue());
        assertTrue(result[0].getScore() > result[1].getScore());
	}
}
