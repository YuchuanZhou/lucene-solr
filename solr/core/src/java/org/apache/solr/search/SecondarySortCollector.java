package org.apache.solr.search;

import org.apache.lucene.search.FieldValueHitQueue;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocsCollector;

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


  public static SecondarySortCollector create() {

  }
}
