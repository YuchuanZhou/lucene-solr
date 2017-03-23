package org.apache.solr.search;

import org.apache.lucene.search.*;

import java.util.Map;
import java.util.PriorityQueue;

public class SecondarySortCollector extends TopDocsCollector{

  private Map<String, ExternalDataSource> dataSourceMap;
  private Sort sort;
  private PriorityQueue pq = new FieldValueHitQueue<>();

  public SecondarySortCollector(Sort sort, Map<String, ExternalDataSource> dataSourceMap) {
    this.sort = sort;
    this.dataSourceMap = dataSourceMap;
  }


  public static SecondarySortCollector create(Sort sort, int numHits, Map<String, ExternalDataSource> dataSourceMap,
                                         boolean fillFields, boolean trackDocScores, boolean trackMaxScore) {
    if (numHits <= 0) {
      throw new IllegalArgumentException("numHits must be > 0; please use TotalHitCountCollector if you just need the total hit count");
    }

    FieldValueHitQueue<FieldValueHitQueue.Entry> queue = FieldValueHitQueue.create(sort.fields, numHits);

    return new SecondarySortCollector(sort, dataSourceMap);
  }
}
