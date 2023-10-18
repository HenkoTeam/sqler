package dev.henko.sqler.codec;

import dev.henko.sqler.codec.error.InvalidArrayTypeException;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

/**
 * Class used to read values from a result set.
 */
public final class CodecReader {

    private final ResultSet resultSet;

    public CodecReader(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Reads a value from the result set using the given key.
     *
     * @param key the column to read the value from.
     * @return the value from the result set.
     * @throws SQLException if the column is not found
     */
    public Object read(final @NotNull String key) throws SQLException {
        return resultSet.getObject(key);
    }

    /**
     * Reads a value from the result set using the given key and type.
     *
     * @param key the column to read the value from.
     * @param type the type of the value.
     * @param <T> the type of the value.
     * @return the value from the result set.
     * @throws SQLException if the column is not found
     */
    public <T> T read(
            final @NotNull String key,
            final @NotNull Class<T> type
    ) throws SQLException {
        return type.cast(resultSet.getObject(key));
    }

    /**
     * Reads a list from the result set using the given key and list supplier.
     * The list supplier is used to create the list that will be returned.
     * Only can be used with sql types. see {@link java.sql.Types}
     *
     * @param key the column to read the list from.
     * @param listSupplier the function used to create the list.
     * @return the list with the values from the result set.
     * @param <T> the type of the list.
     * @throws SQLException if the column is not found
     * @throws InvalidArrayTypeException if the list type is not supported.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> readList(
            final @NotNull String key,
            final @NotNull Function<Integer, List<T>> listSupplier
    ) throws SQLException {
        final var sqlArray = resultSet.getArray(key);

        // just get the type to throw an exception if it is not supported
        TypeParser.parse(sqlArray.getBaseType());

        final var array = (Object[]) sqlArray.getArray();
        final var list = listSupplier.apply(array.length);
        for (int i = 0; i < array.length; i++) {
            list.set(i, (T) array[i]);
        }
        return list;
    }
}
