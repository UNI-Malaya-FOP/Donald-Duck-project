package edu.dataframe;

import edu.dataframe.column.*;

import java.util.Comparator;
import java.util.Iterator;

public interface DataFrame extends Iterable<DataFrameRow> {

    //<editor-fold desc="Get Column Class">
    /**
     *
     * @param name of column.
     * @return class of column.
     * @throws DataFrameException if column doesn't exist.
     */
    @SuppressWarnings("rawtypes")
    Class<? extends DataFrameColumn> getColumnClass(String name) throws DataFrameException;

    /**
     *
     * @param index of column.
     * @return class of column.
     * @throws DataFrameException if column doesn't exist.
     */
    @SuppressWarnings("rawtypes")
    Class<? extends DataFrameColumn> getColumnClass(int index) throws DataFrameException;
    //</editor-fold>

    //<editor-fold desc="Get Element Class">
    /**
     *
     * @param name of column.
     * @return class of element in the column.
     * @throws DataFrameException if column doesn't exist.
     */
    Class<?> getElementClass(String name) throws DataFrameException;

    /**
     *
     * @param index of column.
     * @return class of element in the column.
     * @throws DataFrameException if column doesn't exist.
     */
    Class<?> getElementClass(int index) throws DataFrameException;
    //</editor-fold>

    /**
     * Get header of DataFrame
     * @return DataFrameHeader
     */
    DataFrameHeader getHeader();

    /**
     * Get name of each column
     * @return header as string
     */
    String[] getHeaderName();

    int getColumnNumber();

    int getRowNumber();

    //<editor-fold desc="Get Column">
    /**
     * Get a single column.
     * @param name of column.
     * @return DataFrameColumn of specified name.
     * @throws DataFrameException if column doesn't exist.
     */
    DataFrameColumn<?> getColumn(String name) throws DataFrameException;

    /**
     *
     * @param name of column
     * @param type of column
     * @param <T> subclass of DataFrameColumn
     * @return <T> subclass of DataFrameColumn
     * @throws DataFrameException if column with type doesn't exist.
     */
    @SuppressWarnings("rawtypes")
    <T extends DataFrameColumn> T getColumn(String name, Class<T> type) throws DataFrameException;

    /**
     * Get a single string column.
     * @param name of column.
     * @return StringColumn of specified name.
     * @throws DataFrameException if string column doesn't exist.
     */
    IntegerColumn getIntegerColumn(String name) throws DataFrameException;

    /**
     * Get a single string column.
     * @param name of column.
     * @return StringColumn of specified name.
     * @throws DataFrameException if string column doesn't exist.
     */
    DoubleColumn getDoubleColumn(String name) throws DataFrameException;

    /**
     * Get a single string column.
     * @param name of column.
     * @return StringColumn of specified name.
     * @throws DataFrameException if string column doesn't exist.
     */
    StringColumn getStringColumn(String name) throws DataFrameException;
    //</editor-fold>

    /**
     * Get row at specified index;
     * @param index of row
     * @return elements in row as object
     * @throws DataFrameException if index is out of bound;
     */
    Object[] getRow(int index) throws DataFrameException;

    //<editor-fold desc="Add column">
    /**
     * Add an empty column with specified name, type.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if column already exist.
     */
    DataFrame addColumn(DataFrameColumn<?> column) throws DataFrameException;

    /**
     * Add an empty integer column.
     * @param name of column.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if column already exist.
     */
    DataFrame addIntegerColumn(String name) throws DataFrameException;

    /**
     * Add an empty double column.
     * @param name of column.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if column already exist.
     */
    DataFrame addDoubleColumn(String name) throws DataFrameException;

    /**
     * Add an empty string column.
     * @param name of column.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if column already exist.
     */
    DataFrame addStringColumn(String name) throws DataFrameException;
    //</editor-fold>

    //<editor-fold desc="Append Row">
    /**
     * Append row to the end of DataFrame.
     * @param elements of row.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if elements type doesn't match or size doesn't match.
     */
    DataFrame appendRow(Object... elements) throws DataFrameException;

    /**
     * Append row to the end of DataFrame. If type doesn't match, append null
     * @param elements of row.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if size doesn't match.
     */
    DataFrame appendMatchingRow(Object... elements) throws DataFrameException;

    /**
     * Append row to the end of the DataFrame.
     * @param row of DataFrame.
     * @return DataFrame for method chaining.
     * @throws DataFrameException if size doesn't match
     */
    DataFrame appendRow(DataFrameRow row)throws DataFrameException;
    //</editor-fold>

    DataFrame deleteRow(int index) throws DataFrameException;

    DataFrame deleteFirstRowWith(String name, Object element) throws DataFrameException;

    DataFrame deleteAllRowWith(String name, Object element) throws DataFrameException;

    DataFrame dropNull(String name) throws DataFrameException;

    /**
     * Sort the DataFrame by specified column in ascending order.
     * @param name of column to sort.
     * @return sorted DataFrame for method chaining.
     * @throws DataFrameException if column doesn't exist.
     */
    DataFrame sortBy(String name) throws DataFrameException;

    //<editor-fold desc="Sort with comparator, to fix later">
    /**
     * Sort the DataFrame by specified column in order specified by comparator.
     * @param name of column to sort.
     * @param comparator for the sorting order.
     * @return sorted DataFrame for method chaining.
     * @throws DataFrameException if column doesn't exist.
     */
    DataFrame sortBy(String name, Comparator<?> comparator) throws DataFrameException;
    //</editor-fold>

    /**
     * Create a new empty DataFrame.
     * @return DataFrame for method chaining.
     */
    static DataFrame create() {
        return new NewDataFrame();
    }

    /**
     * Change the other column base on baseColumn.
     * @param baseColumn of changed column.
     * @return DataFrame for method chaining.
     */
    DataFrame getColumnChange(DataFrameColumn<?> baseColumn);

    /**
     * Print the DataFrame debug version on the System output.
     */
    void printDebug();

    /**
     * Print the DataFrame on the System output.
     */
    void print();

    //<editor-fold desc="Iterator related">
    default Iterator<DataFrameRow> iterator() {
        return new DataFrameIterator(this);
    }

    default DataFrameRow getRow(DataFrame dataFrame, int index) {
        return new DataFrameRow(this, index);
    }

    default int size() {
        return getRowNumber();
    };

    //</editor-fold>
}
