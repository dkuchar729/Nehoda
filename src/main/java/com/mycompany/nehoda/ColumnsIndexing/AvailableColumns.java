package com.mycompany.nehoda.ColumnsIndexing;

import ColumnValuesMappers.GeneralMapper;
import ColumnValuesMappers.SpecificMapper;

public enum AvailableColumns {

    ID(new String[]{"p1", "id"}),
    DATUM(new String[]{"p2a", "datum"}),
    HODINA(new String[]{"p2b", "hodina"}),
    KRAJ(new String[]{"p4a", "kraj"}),
    OKRES(new String[]{"p4b", "okres"}),
    UMISTENI_OBEC(new String[]{"p5a", "umisteniObec"}),
    DRUH_NEHODY(new String[]{"p6", "druhNehody"}),
    TYP_SRAZKY_VOZIDLO(new String[]{"p7", "typSrazkyVozidlo"}),
    TYP_SRAZKY_PREKAZKA(new String[]{"p8", "typSrazkyPrekazka"}),
    ZAVINENI_NEHODY(new String[]{"p10", "zavineniNehody"}),
    PRICINA_NEHODY(new String[]{"p12", "pricinaNehody"},SpecificMapper.SM_SPECIFIKACE_PRICINA_NEHODY),
    DRUH_POVRCHU(new String[]{"p15", "druhPovrchu"}),
   POVRCH_VOZOVKY(new String[]{"p16", "povrchVozovky"}),
    STAV_VOZOVKY(new String[]{"p17", "stavVozovky"}),
    POVETRNOSTNI_PODMINKY(new String[]{"p18", "povetrnostniPodminky"}),
    VIDITELNOST(new String[]{"p19", "viditelnost"}),
    ROZHLED(new String[]{"p20", "rozhled"}),
    DELENI_KOMUNIKACE(new String[]{"p21", "deleniKomunikace"}),
    SITUOVANI_NEHODY(new String[]{"p22", "situovaniNehody"}),
    SPECIFICKE_MISTO(new String[]{"p27", "specifickeMisto"}),
    SMEROVY_POMER(new String[]{"p28", "smerovyPomer"},SpecificMapper.SM_SPECIFIKACE_SMEROVY_POMER),
    POCET_VOZIDEL(new String[]{"p34", "pocetVozidel"}),
    DRUH_KOMUNIKACE(new String[]{"p36", "druhKomunikace"}),
    CISLO_KOMUNIKACE(new String[]{"p37", "cisloKomunikace"}),
    TYP_VOZIDLA(new String[]{"p44", "typVozidla"}),
    OBEC(new String[]{"h", "obec"}),
    SOURADNICE_X(new String[]{"d", "souradniceX"}),
    SOURADNICE_Y(new String[]{"e", "souradniceY"}),
    USMRCENO_OSOB(new String[]{"p13a", "usmrcenoOsob"}),
    TEZCE_ZRANENO_OSOB(new String[]{"p13b", "tezceZranenoOsob"}),
    LEHCE_ZRANENO_OSOB(new String[]{"p13c", "lehceZranenoOsob"}),

    ZAVAZNOST_NEHODY(new String[]{"zavaznostNehody"}),
    SPECIFIKACE_SMEROVY_POMER(new String[]{"specifikaceSmerovyPomer"},SpecificMapper.SM_SPECIFIKACE_SMEROVY_POMER)
    ;




    private final String[] columnNames;
    private final SpecificMapper sm;

    private AvailableColumns(String[] columnNames, SpecificMapper sm) {
        this.columnNames = columnNames;
        this.sm=sm;
    }
    private AvailableColumns(String[] columnNames) {
        this.columnNames = columnNames;
        sm=null;

    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String getMapppedSpecificValue(int value)
    {
       if(sm!=null)
       {
           return sm.getValue(value);
       }
        return "null";
    }
}
