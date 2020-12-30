package edu.dataframe;

import edu.dataframe.column.*;

public interface DataFrame {

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
     * Create an empty new DataFrame.
     * @return DataFrame for method chain.
     */
    static DataFrame create() {
        return new NewDataFrame();
    }

    /**
     * Print debug version for debugging
     */
    void printDebug();
}
