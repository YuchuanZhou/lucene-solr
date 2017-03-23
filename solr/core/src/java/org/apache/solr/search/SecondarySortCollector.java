package org.apache.solr.search;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocsCollector;

public class SecondarySortCollector extends TopDocsCollector{

    private int numHits;
    private Sort sort;
    public SecondarySortCollector(int numHits, Sort sort) {
        this.numHits = numHits;
        this.sort = sort;
    }


}
