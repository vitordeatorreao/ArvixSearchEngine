package br.ufpe.cin.rii.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ReadRelevanceMap
{

    public static List<HashMap<String, Boolean>>
    fromCSV(String path)
    throws IOException
    {
        List<HashMap<String, Boolean>> relevance =
                new ArrayList<HashMap<String, Boolean>>();
        File file = new File(path);
        if (!file.exists()) return relevance;
        if (!file.isFile()) return relevance;
        relevance.add(new HashMap<String, Boolean>());
        relevance.add(new HashMap<String, Boolean>());
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine(); // jump header
        String nextLine;
        while ((nextLine = reader.readLine()) != null)
        {
            String[] data = nextLine.split(",");
            relevance.get(0).put(data[0], data[1].equals("1"));
            relevance.get(1).put(data[0], data[2].equals("1"));
        }
        reader.close();
        return relevance;
    }
}
