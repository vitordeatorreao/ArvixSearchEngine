package br.ufpe.cin.rii.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import br.ufpe.cin.rii.input.ReadDocuments;

public class ReadDocumentsTest
{
    @Test
    public void
    test()
    throws IOException, ParseException
    {
        List<Document> documents  = ReadDocuments.fromJson("data/stat.ML");
        List<Document> documents2 = ReadDocuments.fromJson("data/cs.CV");
        assertEquals(109, documents.size());
        assertEquals(96, documents2.size());
        documents.addAll(documents2);
        assertEquals(205, documents.size());
        for (Document doc : documents)
        {
            assertNotNull(doc.getField("id"));
            assertNotNull(doc.getField("title"));
            assertNotNull(doc.getField("abstract"));
            assertNotNull(doc.getField("date"));
            assertTrue(doc.getFields("author").length > 0);
        }
    }
}
