package br.ufpe.cin.rii.apps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufpe.cin.rii.engines.QueryResult;
import br.ufpe.cin.rii.engines.SearchEngine;
import br.ufpe.cin.rii.input.ReadRelevanceMap;
import br.ufpe.cin.rii.metrics.Evaluator;

public final class EvaluationApp extends BasicApp
{
    public static final String QUERY_ONE =
            "evaluating the performance of predictors";
    public static final String QUERY_TWO = "extracting the right features";
    
    public static Evaluator
    evaluate(SearchEngine engine,
             String query,
             HashMap<String, Boolean> relevanceMap)
    throws ParseException, IOException
    {
        QueryResult[] result = engine.search("abstract", query);
        return new Evaluator(relevanceMap, result);
    }

    public static void
    printEvaluation(EvaluationApp app,
                    String query,
                    HashMap<String, Boolean> relevanceMap)
    throws ParseException, IOException
    {
        System.out.println("Without Stopwords and Without Stemming");
        System.out.println("Query: " + query);
        Evaluator evaluator = evaluate(app.rawEngine, query,
                                       relevanceMap);
        System.out.println("Precision: " + evaluator.getPrecision());
        System.out.println("Relative Precision: "
                           + evaluator.getRelativePrecision());
        System.out.println("Recall: " + evaluator.getRecall());
        System.out.println("F-Measure: " + evaluator.getFMeasure());
        System.out.println("================================================");
        System.out.println("With Stopwords and Without Stemming");
        System.out.println("Query: " + query);
        evaluator = evaluate(app.swEngine, query, relevanceMap);
        System.out.println("Precision: " + evaluator.getPrecision());
        System.out.println("Relative Precision: "
                           + evaluator.getRelativePrecision());
        System.out.println("Recall: " + evaluator.getRecall());
        System.out.println("F-Measure: " + evaluator.getFMeasure());
        System.out.println("================================================");
        System.out.println("Without Stopwords and With Stemming");
        System.out.println("Query: " + query);
        evaluator = evaluate(app.stEngine, query, relevanceMap);
        System.out.println("Precision: " + evaluator.getPrecision());
        System.out.println("Relative Precision: "
                           + evaluator.getRelativePrecision());
        System.out.println("Recall: " + evaluator.getRecall());
        System.out.println("F-Measure: " + evaluator.getFMeasure());
        System.out.println("================================================");
        System.out.println("With Stopwords and With Stemming");
        System.out.println("Query: " + query);
        evaluator = evaluate(app.fullEngine, query, relevanceMap);
        System.out.println("Precision: " + evaluator.getPrecision());
        System.out.println("Relative Precision: "
                           + evaluator.getRelativePrecision());
        System.out.println("Recall: " + evaluator.getRecall());
        System.out.println("F-Measure: " + evaluator.getFMeasure());
        System.out.println("================================================");
    }

    public static void
    main(String[] args)
    throws ParseException, IOException, org.json.simple.parser.ParseException
    {
        EvaluationApp app = new EvaluationApp();
        app.initializeEngines();
        
        List<HashMap<String, Boolean>> relevanceMaps =
                ReadRelevanceMap.fromCSV("data/RelevanceMatrix.csv");
        
        printEvaluation(app, QUERY_ONE, relevanceMaps.get(0));
        printEvaluation(app, QUERY_TWO, relevanceMaps.get(1));
    }
}
