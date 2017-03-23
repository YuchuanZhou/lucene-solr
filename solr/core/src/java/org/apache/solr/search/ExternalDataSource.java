package org.apache.solr.search;

public interface ExternalDataSource<Number, V> {

    public V getValue(Number key);
}
