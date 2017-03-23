package org.apache.solr.search;

import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.LeafFieldComparator;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

public abstract class ExternalFieldComparator {

  public static class ExternalIntComparator extends FieldComparator.IntComparator {
    private final int[] values;
    ExternalDataSource<Integer> dataSource;
    SortField.Type ktype;

    public ExternalIntComparator(int numHits, String field, Integer missingValue, ExternalDataSource<Integer> dataSource) {
      super(numHits, field, missingValue != null ? missingValue : 0);
      values = new int[numHits];
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private int getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        long key = currentReaderValues.longValue();
        if(ktype == SortField.Type.INT) key = (int) key;
        return dataSource.getValue(key);
      } else {
        return missingValue;
      }
    }

    @Override
    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }
  }

  public static class ExternalLongComparator extends FieldComparator.LongComparator {
    private final long[] values;
    ExternalDataSource<Long> dataSource;
    SortField.Type ktype;

    public ExternalLongComparator(int numHits, String field, Long missingValue, ExternalDataSource<Long> dataSource) {
      super(numHits, field, missingValue != null ? missingValue : 0L);
      values = new long[numHits];
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private long getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        long key = currentReaderValues.longValue();
        if(ktype == SortField.Type.INT) key = (int) key;
        return dataSource.getValue(key);
      } else {
        return missingValue;
      }
    }

    @Override
    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }
  }

  public static class ExternalFloatComparator extends FieldComparator.FloatComparator {
    private final float[] values;
    ExternalDataSource<Float> dataSource;
    SortField.Type ktype;

    public ExternalFloatComparator(int numHits, String field, Float missingValue, ExternalDataSource<Float> dataSource) {
      super(numHits, field, missingValue != null ? missingValue : 0.0f);
      values = new float[numHits];
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private float getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        long key = currentReaderValues.longValue();
        if(ktype == SortField.Type.INT) key = (int) key;
        return dataSource.getValue(key);
      } else {
        return missingValue;
      }
    }

    @Override
    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }
  }

  public static class ExternalDoubleComparator extends FieldComparator.DoubleComparator {
    private final double[] values;
    ExternalDataSource<Double> dataSource;
    SortField.Type ktype;

    public ExternalDoubleComparator(int numHits, String field, Double missingValue, ExternalDataSource<Double> dataSource) {
      super(numHits, field, missingValue != null ? missingValue : 0.0f);
      values = new double[numHits];
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private double getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        long key = currentReaderValues.longValue();
        if(ktype == SortField.Type.INT) key = (int) key;
        return dataSource.getValue(key);
      } else {
        return missingValue;
      }
    }

    @Override
    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }
  }

  public static class ExternalTermValComparator {
    private final String[] values;
    private NumericDocValues currentReaderValues;
    private String bottom;
    private String topValue;
    String missingValue;
    String field;
    ExternalDataSource<String> dataSource;
    SortField.Type ktype;

    public ExternalTermValComparator(int numHits, String field, String missingValue, ExternalDataSource<String> dataSource) {
      values = new String[numHits];
      this.field = field;
      this.missingValue = missingValue;
      this.dataSource = dataSource;
      ktype = dataSource.getKeyType();
    }

    private String getValueForDoc(int doc) throws IOException {
      if (currentReaderValues.advanceExact(doc)) {
        long key = currentReaderValues.longValue();
        if(ktype == SortField.Type.INT) key = (int) key;
        return dataSource.getValue(key);
      } else {
        return missingValue;
      }
    }

    public int compare(int slot1, int slot2) {
      return values[slot1].compareTo(values[slot2]);
    }

    public int compareBottom(int doc) throws IOException {
      return bottom.compareTo(getValueForDoc(doc));
    }

    public void copy(int slot, int doc) throws IOException {
      values[slot] = getValueForDoc(doc);
    }

    public void setBottom(final int bottom) {
      this.bottom = values[bottom];
    }

    public void setTopValue(String value) {
      topValue = value;
    }

    public Long value(int slot) {
      return Long.valueOf(values[slot]);
    }

    public int compareTop(int doc) throws IOException {
      return topValue.compareTo(getValueForDoc(doc));
    }
  }
}
