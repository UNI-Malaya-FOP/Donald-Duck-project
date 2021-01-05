package edu.dataframe;

import edu.dataframe.column.*;

import java.util.Comparator;

public interface DataFrame extends Iterable<DataFrameRow> {

    /**
     * Get number of row in DataFrame.
     * @return number of column.
     */
    int getRowNumber();

    /**
     * Get number of column in DataFrame.
     * @return number of column.
     */
    int getColumnNumber();

    /**
     * Get header of DataFrame
     * @return Header
     */
    DataFrameHeader getHeader();

    /**
     * Get Integer column of DataFrame.
     * @param name of Integer column.
     * @return Integer column.
     * @throws DataFrameException if Integer column does not exist.
     */
    IntegerColumn getIntegerColumn(String name) throws DataFrameException;

    /**
     * Get Float column of DataFrame.
     * @param name of Float column.
     * @return Float column.
     * @throws DataFrameException if Float column does not exist.
     */
    FloatColumn getFloatColumn(String name) throws DataFrameException;

    /**
     * Get String column of DataFrame.
     * @param name of column.
     * @return String column.
     * @throws DataFrameException if String column does not exist.
     */
    StringColumn getStringColumn(String name) throws DataFrameException;

    /**
     * Add a column to DataFrame.
     * @param column to add.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column size does not match or if column name already exist.
     */
    DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException;

    /**
     * Add an empty Integer column.
     * @param name of new Column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column name already exist.
     */
    DataFrame addIntegerColumn(String name) throws DataFrameException;

    /**
     * Add an empty Float column.
     * @param name of new Column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column name already exist.
     */
    DataFrame addFloatColumn(String name) throws DataFrameException;

    /**
     * Add an empty String column.
     * @param name of new Column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column name already exist.
     */
    DataFrame addStringColumn(String name) throws DataFrameException;


    /**
     * Delete column with specified name.
     * @param name of new column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist.
     */
    DataFrame deleteColumn(String name) throws DataFrameException;

    /**
     * Get row by its position.
     * @param position of row
     * @return row at specified index
     * @throws DataFrameException if position is more than row number.
     */
    DataFrameRow getRow(int position) throws DataFrameException;

    /**
     * Get row by indices.
     * @param index of row.
     * @return row with specified index.
     * @throws DataFrameException if index does not exist.
     */
    DataFrameRow getRowByIndex(int index) throws DataFrameException;

    /**
     * Append an element to each column in DataFrame.
     * @param elements to append in order.
     * @return DataFrame for method chain.
     * @throws DataFrameException if Object length does not match or type does not match.
     */
    DataFrame append(Object... elements) throws DataFrameException;

    /**
     * Delete row at specific index.
     * @param index of element to remove.
     * @return DataFrame for method chain.
     * @throws DataFrameException if index does not exist.
     */
    DataFrame deleteRowByIndex(int index) throws DataFrameException;

    /**
     * Delete first row with specified element in a specified column.
     * @param name of column.
     * @param element to delete.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or element does not exist.
     */
    DataFrame deleteFirstRow(String name, Object element) throws DataFrameException;

    /**
     * Delete all row with specified element in a specified column.
     * @param name of column.
     * @param element to delete.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or element does not exist
     */
    DataFrame deleteAllRow(String name, Object element) throws DataFrameException;

    /**
     * Delete all row with null in specified columns.
     * @param names of column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or null does not exist.
     */
    DataFrame dropNull(String... names) throws DataFrameException;

    /**
     * Delete all row with first duplicate at specified columns.
     * @param names of columns.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or duplicate does not exist.
     */
    DataFrame dropDuplicate(String... names) throws DataFrameException;

    /**
     * Delete all row except keep with first duplicate at specified column.
     * @param name of column.
     * @param keep number of column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or duplicate does not exist.
     */
    DataFrame dropDuplicate(String name, int keep) throws DataFrameException;

    /**
     * Delete all row except keep with first duplicate at specified columns.
     * @param names of columns.
     * @param keep number of column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or duplicate does not exist.
     */
    DataFrame dropDuplicate(String[] names, int keep) throws DataFrameException;

    /**
     * Delete all row except keep with first duplicate at specified column.
     * @param name of column.
     * @param keep first or last of duplicate.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or duplicate does not exist.
     * @throws UnsupportedOperationException if ordinal is not valid or less than 1 or more than 10.
     */
    DataFrame dropDuplicate(String name, String keep) throws DataFrameException, UnsupportedOperationException;

    /**
     * Delete all row except keep with first duplicate at specified columns.
     * @param names of columns.
     * @param keep first or last of duplicate.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or duplicate does not exist.
     * @throws UnsupportedOperationException if ordinal is not valid or less than 1 or more than 10.
     */
    DataFrame dropDuplicate(String[] names, String keep) throws DataFrameException, UnsupportedOperationException;

    /**
     * Replace element at certain index in specified column with specified value.
     * @param name of column.
     * @param index to do the replace.
     * @param element to replace at index.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or index does not exist.
     */
    DataFrame replaceByIndex(String name, int index, Object element) throws DataFrameException;

    /**
     * Replace first null in specified column with specified value.
     * @param name of column.
     * @param element to replace null.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist or null does not exist.
     */
    DataFrame replaceNull(String name, Object element) throws DataFrameException;

    /**
     * Concat another DataFrame to the left of current DataFrame.
     * @param dataFrame to concat to the left.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column in both DataFrame hs the same name or row number does not match.
     */
    DataFrame concatX(DataFrame dataFrame) throws DataFrameException;

    /**
     * Concat another DataFrame to the below of current DataFrame.
     * @param dataFrame to concat below.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column number does not match or column has different properties.
     */
    DataFrame concatY(DataFrame dataFrame) throws DataFrameException;

    /**
     * Sort DataFrame by specified column and comparator.
     * @param name of column.
     * @param comparator for order.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist.
     */
    DataFrame sortBy(String name, Comparator<?> comparator) throws DataFrameException;

    /**
     * Sort DataFrame by specified column by ascending order.
     * @param name of column.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist.
     */
    DataFrame sortBy(String name) throws DataFrameException;

    /**
     * Sort DataFrame by specified column by ascending or descending order.
     * @param name of column.
     * @param ascending order.
     * @return DataFrame for method chain.
     * @throws DataFrameException if column does not exist.
     */
    DataFrame sortBy(String name, Boolean ascending) throws DataFrameException;

    /**
     * Reset indices of the dataframe.
     */
    void resetIndices();

    /**
     * Create an empty new DataFrame.
     * @return DataFrame for method chain.
     */
    static DataFrame create() {
        return new NewDataFrame();
    }

    void print();

    /**
     * Print debug version for debugging
     */
    void printDebug();
}
