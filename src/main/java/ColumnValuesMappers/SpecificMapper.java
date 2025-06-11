package ColumnValuesMappers;


import java.util.Map;

public enum SpecificMapper {
    SM_SPECIFIKACE_SMEROVY_POMER(Map.of(
            1,"primyUsek",
            2,"primyUsekPoZatacce",
            3,"zatacka",
            4,"krizovatkaPrusecnaCtyrramenna",
            5,"krizovatkaStykovaTriramenna",
            6,"krizovatkaPetiViceRamenna",
            7,"kruhovyObjezd"
    ));
    private Map<Integer, String> mapValues;

    private SpecificMapper(Map<Integer, String> mapValues) {
        this.mapValues = mapValues;
    }

    public String getValue(int numValue)
    {
        return mapValues.getOrDefault(numValue,"null");

    }
}
