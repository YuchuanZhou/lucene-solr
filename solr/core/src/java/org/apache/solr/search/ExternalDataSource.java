package org.apache.solr.search;

import org.apache.lucene.search.SortField;

public interface ExternalDataSource<K, V> {

  public K getKeyType();

  public V getValue(K key);
}
