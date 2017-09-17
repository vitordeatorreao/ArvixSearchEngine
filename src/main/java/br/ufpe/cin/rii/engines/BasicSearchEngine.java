package br.ufpe.cin.rii.engines;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
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

public class BasicSearchEngine implements SearchEngine
{	
	private Directory directory;
	private DirectoryReader ireader;
	private IndexSearcher isearcher;
	private IndexWriter iwriter;
	private Analyzer analyzer;
	
	public
	BasicSearchEngine(String indexName,
					  Analyzer analyzer)
	throws IOException
	{
		this.directory = FSDirectory.open(FileSystems.getDefault()
									.getPath("indexes", indexName));
		this.analyzer = analyzer;
		IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
		this.iwriter = new IndexWriter(this.directory, config);
	}

	public void
	addDocument(Document doc)
	throws IOException
	{
		this.iwriter.addDocument(doc);
	}
	
	public void
	commit()
	throws IOException
	{
		this.iwriter.close();
		this.ireader = DirectoryReader.open(this.directory);
		this.isearcher = new IndexSearcher(this.ireader);
	}

	public QueryResult[]
	search(String fieldName,
		   String text)
	throws ParseException, IOException
	{
		return this.search(fieldName, text, 1000);
	}

	public QueryResult[]
	search(String fieldName,
		   String text,
		   int n)
	throws ParseException, IOException
	{
		if (this.isearcher == null)
		{
			this.commit();
		}
		QueryParser parser = new QueryParser(fieldName, this.analyzer);
		Query query = parser.parse(text);
		ScoreDoc[] hits = this.isearcher.search(query, n).scoreDocs;
		QueryResult[] result = new QueryResult[hits.length];
		for (int i = 0; i < hits.length; i++)
		{
			result[i] = new QueryResult(this.isearcher.doc(hits[i].doc),
										hits[i].score);
		}
		return result;
	}

	public void
	close()
	throws IOException
	{
		if (this.isearcher == null) this.iwriter.close();
		if (this.isearcher != null) this.ireader.close();
		this.directory.close();
	}
}
