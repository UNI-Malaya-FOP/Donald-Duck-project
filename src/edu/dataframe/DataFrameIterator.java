package edu.dataframe;

import java.util.Iterator;

class DataFrameIterator implements Iterator<DataFrameRow> {

    private int index;

    DataFrame dataFrame;
    public DataFrameIterator(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    @Override
    public boolean hasNext() {
        return index < dataFrame.getRowNumber();
    }

    @Override
    public DataFrameRow next() {
        return new DataFrameRow(dataFrame, index++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}