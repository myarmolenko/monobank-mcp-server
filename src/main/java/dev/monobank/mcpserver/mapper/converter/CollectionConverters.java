package dev.monobank.mcpserver.mapper.converter;

import dev.monobank.mcpserver.mapper.qualifier.ArrayToList;

import java.util.Arrays;
import java.util.List;

public class CollectionConverters {

    @ArrayToList
    public static List<String> arrayToList(final String[] array) {
        return array == null ? List.of() : Arrays.asList(array);
    }
}
