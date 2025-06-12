package ColumnValuesMappers;


import org.apache.commons.collections.keyvalue.AbstractMapEntry;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public enum SpecificMapper {
    SM_SPECIFIKACE_PRICINA_NEHODY(new int[]
            {100,201, 202, 203, 204, 205, 206, 207, 208, 209,
                    301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311,
                    401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414,
                    501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516,
                    601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615
            }, new String[]{
            "nezavinenaRidicem",
            "rychlostNeprimerenaIntenzitaProvozu",
            "rychlostNeprimerenaViditelnost",
            "rychlostNeprimerenaVlastnostVozidla",
            "rychlostNeprimerenaStavVozovky",
            "rychlostNeprimerenaTechnickyStavVozovky",
            "rychlostPrekroceniPravidla",
            "rychlostPrekroceniZnacka",
            "rychlostNeprimerenaVitr",
            "rychlostPrekroceniOstatni",

            "predjizdeniVpravo",
            "predjizdeniBezOdstupu",
            "predjizdeniBezRohledu",
            "predjizdeniOhrozeniProtijedocihoVozidla",
            "predjizdeniOhrozeniPredjizdenehoVozidla",
            "predjizdeniVlevoOdboceniVlevo",
            "predjizdeniVZakazu",
            "predjizdeniPrejetaCaraSouvisla",
            "predjizdeniBraneni",
            "predjizdeniSoubezneVozidlo",
            "predjizdeniOstatni",

            "prednostSemaforCervena",
            "prednostZnackaStop",
            "prednostZnackaDejPrednost",
            "prednostVozidloZprava",
            "prednostOdbocovaniVlevo",
            "prednostTramvajOdbocovani",
            "prednostProtijedouciVozidloPrekazka",
            "prednostZarazeniDoProuduZeStanice",
            "prednostVjizdeniNaSilnici",
            "prednostOtaceniCouvani",
            "prednostPrejizdeniMeziPruhy",
            "prednostChodec",
            "prednostOdbocovaniVlevoSoubezneVozidlo",
            "prednostOstatni",

            "zjNespravnaStranaVozovkyProtismer",
            "zjVyhybaniBezBocnihoOdstupu",
            "zjNBezpecnaVzdalenost",
            "zjNespravneOtaceniCouvani",
            "zjUdaniSmeruJizdy",
            "zjBezohlednaJizda",
            "zjNahleZpomaleniZastaveni",
            "zjNevenovaniSeRizeni",
            "zjSamovolneRozjetiNezajistenehoVozidla",
            "zjVjezdNaNezpevnenouKomunikaci",
            "zjNezvladnutiRizeniVozidla",
            "zjJizdaJednosmerkouVProtismeru",
            "zjVDusledkuPouzitiProstredkuKZastaveni",
            "zjPouzitiSluzebniZbrane",
            "zjProvadeniSluzebnihoZakroku",
            "zjOstatni",

            "tzRizeni",
            "tzProvozdniBrzda",
            "tzNeucinnaParkovaciBrzda",
            "tzOpotrebeniBehounu",
            "tzDefektPneumatiky",
            "tzOsvetleni",
            "tzSpojovaciHadiceBrzdeniPripojVozidla",
            "tzNespravneUlozeniNakladu",
            "tzZtratakola",
            "tzZablokovaniKol",
            "tzLomZavesuKolaPruziny",
            "tzPrivesNezajistenaPoskozenaBocnice",
            "tzPrivesZaves",
            "tzUtrzenaSpojovaciHridel",
            "tzOstatni"
    }),
   


    SM_SPECIFIKACE_SMEROVY_POMER(Map.of(
            1, "primyUsek",
            2, "primyUsekPoZatacce",
            3, "zatacka",
            4, "krizovatkaPrusecnaCtyrramenna",
            5, "krizovatkaStykovaTriramenna",
            6, "krizovatkaPetiViceRamenna",
            7, "kruhovyObjezd"
    ));
    private Map<Integer, String> mapValues;

    private SpecificMapper(Map<Integer, String> mapValues) {
        this.mapValues = mapValues;
    }

    private SpecificMapper(int[] keys, String[] values) {
        mapValues=new HashMap<>();
        for(int i=0;i<keys.length;i++)
        {
           mapValues.put(keys[i],values[i]);
        }

    }

    public String getValue(int numValue) {
        return mapValues.getOrDefault(numValue, "null");

    }
}
