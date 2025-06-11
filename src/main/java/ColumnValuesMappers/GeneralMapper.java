package ColumnValuesMappers;

import org.apache.commons.math3.util.Pair;

import java.util.List;
import java.util.Map;

public enum GeneralMapper {

    GM_SPECIFIKACE_SMEROVY_POMER(
            List.of(createPair(initIntArray(1,2),"primyUsek")

                    )
    );
    /*new Integer[]{1,2},"primyUsek",
                new Integer[]{3},"zatacka",
                new Integer[]{4,5,6},"krizovatka",
                new Integer[]{7},"kruhovyObjezd"*/
    private Map<Integer, String> mapValues;

    private GeneralMapper(List<Pair<int[], String>> pairsList) {
        this.mapValues = mapValues;

    }

    public String getValue(int numValue) {
        return mapValues.getOrDefault(numValue, "null");

    }

    private void initalizateMap(List<Pair<int[], String>> pairsList) {
        for (Pair<int[], String> pair : pairsList) {
            for (int key : pair.getKey()) {
                mapValues.put(key, pair.getValue());
            }
        }

    }

    private static Pair<int[],String> createPair(int[]keys,String value)
    {
       return new Pair<>(keys,value);
    }

    private static Pair<int[],String> createPair(int key,String value)
    {
        return new Pair<>(new int []{key},value);
    }

    private static int[] initIntArray(int... numbers)
    {
        return numbers;
    }

}



