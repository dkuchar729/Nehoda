package com.mycompany.nehoda.ColumnsIndexing;

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
    PRICINA_NEHODY(new String[]{"p12", "pricinaNehody"}),
    DRUH_POVRCHU(new String[]{"p15", "druhPovrchu"}),
   POVRCH_VOZOVKY(new String[]{"p16", "povrchVozovky"}),
    STAV_VOZOVKY(new String[]{"p17", "stavVozovky"}),
    POVETRNOSTNI_PODMINKY(new String[]{"p18", "povetrnostniPodminky"}),
    VIDITELNOST(new String[]{"p19", "viditelnost"}),
    ROZHLED(new String[]{"p20", "rozhled"}),
    DELENI_KOMUNIKACE(new String[]{"p21", "deleniKomunikace"}),
    SITUOVANI_NEHODY(new String[]{"p22", "situovaniNehody"}),
    SPECIFICKE_MISTO(new String[]{"p27", "specifickeMisto"}),
    SMEROVY_POMER(new String[]{"p28", "smerovyPomer"}),
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
    SPECIFIKACE_SMEROVY_POMER(new String[]{"specifikaceSmerovyPomer"}),
    ZAVAZNOST_NEHODY(new String[]{"zavaznostNehody"});




    private final String[] columnNames;

    private AvailableColumns(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames() {
        return columnNames;
    }
}
