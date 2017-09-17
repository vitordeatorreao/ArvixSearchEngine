package br.ufpe.cin.rii.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import br.ufpe.cin.rii.input.ReadRelevanceMap;

public class ReadRelevanceMapTest
{
    @Test
    public void
    test() throws IOException
    {
        List<HashMap<String, Boolean>> relevanceMap =
                ReadRelevanceMap.fromCSV("data/RelevanceMatrix.csv");
        assertEquals(205, relevanceMap.get(0).size());
        assertEquals(false, relevanceMap.get(0).get("0907.1020"));
        assertEquals(false, relevanceMap.get(1).get("0907.1020"));
        assertEquals(true, relevanceMap.get(0).get("1703.10893"));
        assertEquals(false, relevanceMap.get(1).get("1703.10893"));
        assertEquals(false, relevanceMap.get(0).get("1701.05847"));
        assertEquals(true, relevanceMap.get(1).get("1701.05847"));
        assertEquals(true, relevanceMap.get(0).get("1709.04343"));
        assertEquals(true, relevanceMap.get(1).get("1709.04343"));
    }
}
