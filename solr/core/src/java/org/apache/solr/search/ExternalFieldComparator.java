package org.apache.solr.search;

import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.FieldComparator;

import java.io.IOException;

public abstract class ExternalFieldComparator {

  public static class ExternalFloatComparator<K> extends FieldComparator.FloatComparator {
    private final float[] values;
    private float bottom;
    private float topValue;
    ExternalDataSource<K, Float> dataSource;
    K ktype;

    public ExternalFloatComparator(int numHits, String field, Float missingValue, ExternalDataSource<K, Float> dataSource) {
      super(numHits, field, missingValue != null ? missingValue : 0.0f);
      values = new float[numHits];
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private float getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        return Float.intBitsToFloat((int) currentReaderValues.longValue());
      } else {
        return missingValue;
      }
    }

  @Override
  protected void doSetNextReader(LeafReaderContext context) throws IOException {
    if(ktype instanceof Number) {
      currentReaderValues = DocValues.getNumeric(context.reader(), field);
    } else if(ktype instanceof String) {
      currentReaderVal ues = DocValues.getSorted(context.reader(), field);
    }
  }

    @Override
    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }
  }


}
