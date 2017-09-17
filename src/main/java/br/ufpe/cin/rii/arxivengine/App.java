package br.ufpe.cin.rii.arxivengine;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException, IOException
    {
    	// with stop words:
    	// Analyzer analyzer = new StandardAnalyzer();
    	// without stop words:
    	Analyzer analyzer = new StandardAnalyzer(new CharArraySet(new ArrayList<String>(), false));
    	// with stop words and stemming:
    	// Analyzer analyzer = new EnglishAnalyzer();
    	// without stop words, but with stemming
    	// Analyzer analyzer = new EnglishAnalyzer(new CharArraySet(new ArrayList<String>(), false));

        // Store the index in memory:
        //Directory directory = new RAMDirectory();
        // To store an index on disk, use this instead:
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("indexes", "testindex"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        Document doc = new Document();
        String text = "Evaluating features is not easy.";
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        iwriter.addDocument(doc);
        iwriter.close();
        
        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("is");
        ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
          Document hitDoc = isearcher.doc(hits[i].doc);
          System.out.println(hitDoc.toString());
        }
        ireader.close();
        directory.close();
    }
}
