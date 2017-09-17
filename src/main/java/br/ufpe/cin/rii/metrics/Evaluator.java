package br.ufpe.cin.rii.metrics;

import java.util.HashMap;

import br.ufpe.cin.rii.engines.QueryResult;

public class Evaluator
{
    private QueryResult[] documents;
    private HashMap<String, Boolean> relevanceMap;
    private double returnedRelevant;
    private double totalRelevant;
    private double totalReturned;
    private double topRelevantReturned;
    private int topSize;
    
    public
    Evaluator(HashMap<String, Boolean> relevanceMap,
              QueryResult[] returnedDocuments)
    {
        this(relevanceMap, returnedDocuments, 10);
    }

    public
    Evaluator(HashMap<String, Boolean> relevanceMap,
              QueryResult[] returnedDocuments,
              int topSize)
    {
        this.topSize = topSize;
        this.documents = returnedDocuments;
        this.relevanceMap = relevanceMap;
        this.returnedRelevant = 0.0;
        this.totalRelevant = 0.0;
        this.totalReturned = 0.0;
        this.topRelevantReturned = 0.0;
        for (int i = 0; i < this.documents.length; i++)
        {
            String code = this.documents[i].getDocument()
                                           .getField("id")
                                           .stringValue();
            if (this.relevanceMap.get(code)) this.returnedRelevant++;
            if (this.relevanceMap.get(code) && i < this.topSize)
            {
                this.topRelevantReturned++;
            }
            this.totalReturned++;
        }
        for (String code : this.relevanceMap.keySet())
        {
            if (this.relevanceMap.get(code)) this.totalRelevant++;
        }
    }
    
    public double
    getRelativePrecision()
    {
        return this.topRelevantReturned / this.topSize;
    }

    public double
    getPrecision()
    {
        return this.returnedRelevant / this.totalReturned;
    }
    
    public double
    getRecall()
    {
        return this.returnedRelevant / this.totalRelevant;
    }
    
    public double
    getFMeasure()
    {
        double p = this.getPrecision();
        double c = this.getRecall();
        return (2 * p * c) / (p + c);
    }
}
