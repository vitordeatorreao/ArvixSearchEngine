package br.ufpe.cin.rii.input;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class ReadDocuments
{
    public static List<Document>
    fromJson(String folder)
    throws IOException, ParseException
    {
        List<Document> documents = new ArrayList<Document>();
        File dataFolder = new File(folder);
        if (!dataFolder.exists()) return documents;
        File[] files = dataFolder.listFiles();
        if (files == null) return documents;
        for (File file : files)
        {
            Document document = readSingleFile(file);
            if (document != null) documents.add(document);
        }
        return documents;
    }
    
    private static Document
    readSingleFile(File file) throws IOException, ParseException
    {
        if (!file.isFile()) return null;
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        JSONObject jsonDocument = (JSONObject) parser.parse(reader);
        Document document = new Document();
        document.add(new Field(
                "id",
                (String) jsonDocument.get("paper_code"),
                TextField.TYPE_STORED
        ));
        document.add(new Field(
                "title",
                (String) jsonDocument.get("paper_title"),
                TextField.TYPE_STORED
        ));
        document.add(new Field(
                "abstract",
                (String) jsonDocument.get("paper_abstract"),
                TextField.TYPE_STORED
        ));
        document.add(new Field(
                "date",
                (String) jsonDocument.get("paper_submission_date"),
                TextField.TYPE_STORED
        ));
        JSONArray authors = (JSONArray) jsonDocument.get("paper_authors_list");
        for (int i = 0; i < authors.size(); i++)
        {
            document.add(new Field(
                    "author",
                    (String) authors.get(i),
                    TextField.TYPE_STORED
            ));
        }
        return document;
    }
}
