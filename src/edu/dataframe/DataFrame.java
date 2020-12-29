package edu.dataframe;

public interface DataFrame {

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
     * Create an empty new DataFrame.
     * @return DataFrame for method chain.
     */
    static DataFrame create() {
        return new NewDataFrame();
    }

    void printDebug();
}
