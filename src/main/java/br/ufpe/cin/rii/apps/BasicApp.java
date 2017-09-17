package br.ufpe.cin.rii.apps;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import br.ufpe.cin.rii.engines.FullSearchEngine;
import br.ufpe.cin.rii.engines.RawSearchEngine;
import br.ufpe.cin.rii.engines.SearchEngine;
import br.ufpe.cin.rii.engines.StemmingSearchEngine;
import br.ufpe.cin.rii.engines.StopWordsSearchEngine;
import br.ufpe.cin.rii.input.ReadDocuments;

public abstract class BasicApp 
{
    private static final String RAW_FOLDER  = "index_raw";
    private static final String SW_FOLDER   = "index_sw";
    private static final String ST_FOLDER   = "index_st";
    private static final String FULL_FOLDER = "index_full";
    
    protected SearchEngine rawEngine;
    protected SearchEngine swEngine;
    protected SearchEngine stEngine;
    protected SearchEngine fullEngine;
    
    private void
    addDocumentsToEngine(List<Document> documents, SearchEngine engine)
    throws IOException
    {
        if (documents == null)
        {
            engine.commit();
            return;
        }
        for (Document document : documents)
        {
            engine.addDocument(document);
        }
        engine.commit();
    }

    protected void
    initializeEngines()
    throws ParseException, IOException, org.json.simple.parser.ParseException
    {
        File raw_folder     = FileSystems.getDefault()
                                         .getPath("indexes", RAW_FOLDER)
                                         .toFile();
        File sw_folder      = FileSystems.getDefault()
                                         .getPath("indexes", SW_FOLDER)
                                         .toFile();
        File st_folder      = FileSystems.getDefault()
                                         .getPath("indexes", ST_FOLDER)
                                         .toFile();
        File full_folder    = FileSystems.getDefault()
                                         .getPath("indexes", FULL_FOLDER)
                                         .toFile();
        boolean create_raw  = false;
        boolean create_sw   = false;
        boolean create_st   = false;
        boolean create_full = false;

        if (!raw_folder.exists()) create_raw = true;
        if (!sw_folder.exists()) create_sw = true;
        if (!st_folder.exists()) create_st = true;
        if (!full_folder.exists()) create_full = true;

        this.rawEngine  = new RawSearchEngine(RAW_FOLDER);
        this.swEngine   = new StopWordsSearchEngine(SW_FOLDER);
        this.stEngine   = new StemmingSearchEngine(ST_FOLDER);
        this.fullEngine = new FullSearchEngine(FULL_FOLDER);
        
        List<Document> documents = null;
        if (create_raw || create_sw || create_st || create_full)
        {
            documents = ReadDocuments.fromJson("data/stat.ML");
            documents.addAll(ReadDocuments.fromJson("data/cs.CV"));
        }
        if (create_raw) addDocumentsToEngine(documents, this.rawEngine);
        if (create_sw) addDocumentsToEngine(documents, this.swEngine);
        if (create_st) addDocumentsToEngine(documents, this.stEngine);
        if (create_full) addDocumentsToEngine(documents, this.fullEngine);
    }
}
