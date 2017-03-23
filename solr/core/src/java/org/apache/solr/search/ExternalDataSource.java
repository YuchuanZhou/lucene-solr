package org.apache.solr.search;

import org.apache.lucene.search.SortField;

/**
 * For retrieving data from source outside solr index, key must be of type int or long
 * @param <T> data value type
 */
public interface ExternalDataSource<T> {

  public SortField.Type getKeyType();

  public T getValue(Number key);
}
