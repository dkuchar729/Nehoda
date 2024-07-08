/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dorgu
 */
public class Nastaveni {

    String[] row;
    List<Map<String, Map<String, Boolean>>> seznam;
    private   List<Map<String, Map<String, Boolean>>> seznamDen;
    Map<String, Map<String, Boolean>> primyUsek;
    Map<String, Map<String, Boolean>> zatacka;
    Map<String, Map<String, Boolean>> krizovatka;

    Map<String, Map<String, Boolean>> kruhovyObjezd;

    Map<String, Map<String, Boolean>> havarie;
    Map<String, Map<String, Boolean>> prekazka;
    Map<String, Map<String, Boolean>> zvire;
    Map<String, Map<String, Boolean>> srazkaVozidlo;
    Map<String, Map<String, Boolean>> tramvaj;
    Map<String, Map<String, Boolean>> srazkaVlak;
    Map<String, Map<String, Boolean>> srazkaOdstaveneVozidlo;
    Map<String, Map<String, Boolean>> chodec;
    Map<String, Map<String, Boolean>> ostatni;

    int cas = 0;
    int umisteni=1;
    int druhNehody = 2;
    int zavineniNehody = 3;
    int pricinaNehody = 4;
    int povrchVozovky = 5;
    int stavKomunikacde= 6;
    int povetrnostniPodminky = 7;
    int viditelnost= 8;
    int rozhledovePomery= 9;
    int deleniKomunikace= 10;
    int situovaniNehody= 11;
    int speficikeMisto= 12;
    int smerovyPomer = 13;
    int druhKomunikace = 14;
    int vysledek = 19;


    public Nastaveni(String[] row) {

        this.row = row;
        seznam = new LinkedList<>();
        primyUsek = vytvorMapuSmerovehoPomeru("primyUsek");
        zatacka = vytvorMapuSmerovehoPomeru("zatacka");
        krizovatka = vytvorMapuSmerovehoPomeru("krizovatka");
        kruhovyObjezd=vytvorMapuSmerovehoPomeru("kruhovyObjezd");

seznamDen=vytvorSeznam(new String[]{"srazkaVozidlo","srazkaOdstaveneVOzidlo","prekazka","chodec","zvire","srazkaVlak","srazkaTramvaj","havarie","ostatni"});

        seznam.add(primyUsek);
        seznam.add(zatacka);
        seznam.add(krizovatka);
        seznam.add(kruhovyObjezd);
    }

    public List<String[]> vratRadky() {

        List<String[]> seznamRadek = new LinkedList<>();
        for (Map<String, Map<String, Boolean>> mapa : seznam) {

            for (Map.Entry<String, Map<String, Boolean>> entry : mapa.entrySet()) {

                for (Map.Entry<String, Boolean> komunikace : entry.getValue().entrySet()) {

                    if (komunikace.getValue() == false) {
                        String[] newRow = row.clone();
                        newRow[smerovyPomer] = entry.getKey();
                        newRow[druhKomunikace] = komunikace.getKey();
                        newRow[vysledek]="0";

                        seznamRadek.add(newRow);


                    }
                }
            }

        }
        return seznamRadek;
    }

    public List<String[]> vratDnyRadky(int druhNehody,int druhKomunikace,int pocet) {

        List<String[]> seznamRadek = new LinkedList<>();
        for (Map<String, Map<String, Boolean>> mapa : seznamDen) {

            for (Map.Entry<String, Map<String, Boolean>> entry : mapa.entrySet()) {

                for (Map.Entry<String, Boolean> komunikace : entry.getValue().entrySet()) {

                    if (komunikace.getValue() == false) {
                        String[] newRow = row.clone();
                        newRow[druhNehody] = entry.getKey();
                        newRow[druhKomunikace] = komunikace.getKey();
                        newRow[pocet]="0";

                        seznamRadek.add(newRow);


                    }
                }
            }

        }
        return seznamRadek;
    }

    public boolean porovnej(String[] radka) {

        if (porovnejZaznam(row, radka)) {


            if (radka[smerovyPomer].equals("primyUsek")) {
                primyUsek.get(radka[smerovyPomer]).put(radka[druhKomunikace], true);
                return true;
            } else if (radka[smerovyPomer].equals("krizovatka")) {
                krizovatka.get(radka[smerovyPomer]).put(radka[druhKomunikace], true);
                return true;
            } else if (radka[smerovyPomer].equals("zatacka")) {

                zatacka.get(radka[smerovyPomer]).put(radka[druhKomunikace], true);
                return true;
            }
            else if(radka[smerovyPomer].equals("kruhovyObjezd"))
            {
                kruhovyObjezd.get(radka[smerovyPomer]).put(radka[druhKomunikace], true);
                return true;
            }
        }
        return false;
    }
    public boolean porovnejDen(String[] radka) {

        if (porovnejDenDvaZaznam(row, radka,2,3,4)) {

            for(int i=0;i<seznamDen.size();i++)
            {
                if(seznamDen.get(i).containsKey(radka[1]))
                {
                    seznamDen.get(i).get(radka[1]).put(radka[5],true);
                    return porovnejDenJednaZaznam(row,radka,1,5);
                }
            }


        }
        return false;
    }

    public boolean porovnejZaznam(String[] radka, String[] novaRadka) {


        return porovnejRadku(radka,novaRadka,cas)  && porovnejRadku(radka,novaRadka,druhNehody) && porovnejRadku(radka,novaRadka,zavineniNehody)
               && porovnejRadku(radka,novaRadka,pricinaNehody) && porovnejRadku(radka,novaRadka,viditelnost)
                && porovnejRadku(radka,novaRadka,deleniKomunikace) && porovnejRadku(radka,novaRadka,povrchVozovky)
                && porovnejRadku(radka,novaRadka,povetrnostniPodminky) && porovnejRadku(radka,novaRadka,umisteni)
                && porovnejRadku(radka,novaRadka,stavKomunikacde) &&porovnejRadku(radka,novaRadka,rozhledovePomery)
                && porovnejRadku(radka,novaRadka,situovaniNehody) && porovnejRadku(radka,novaRadka,speficikeMisto);
    }
    public static boolean porovnejDenDvaZaznam(String[] row1, String[] row2, int povrchKomunikace, int stavKomunikace, int viditelnost) {
      return Nastaveni.porovnejRadku(row1, row2, povrchKomunikace)  && Nastaveni.porovnejRadku(row1, row2, stavKomunikace)&&
                            Nastaveni.porovnejRadku(row1, row2, viditelnost);
    }
    public static boolean porovnejDenJednaZaznam(String[] row1, String[] row2, int druhNehody,int druhKomunikace) {
        return Nastaveni.porovnejRadku(row1, row2, druhNehody) && Nastaveni.porovnejRadku(row1, row2, druhKomunikace);
    }



    public static boolean porovnejRadku(String[]radka1,String[]radka2,int index)
    {
        return radka1[index].equals(radka2[index]);
    }

    public Map<String, Boolean> vytvorMapuDruhuSilnice() {
        Map<String, Boolean> mapa = new HashMap<>();


        mapa.put("1.trida", Boolean.FALSE);
        mapa.put("2.trida", Boolean.FALSE);
        mapa.put("3.trida", Boolean.FALSE);
        mapa.put("ucelova", Boolean.FALSE);
        mapa.put("mistni", Boolean.FALSE);
        mapa.put("dalnice", Boolean.FALSE);

        return mapa;
    }

public List<Map<String, Map<String, Boolean>>> vytvorSeznam(String[]nazvy)
{
    List<Map<String, Map<String, Boolean>>> list=new LinkedList<>();
    for(int i=0;i<nazvy.length;i++)
    {
list.add(vytvorMapuSmerovehoPomeru(nazvy[i]));
    }

    return list;
}
    public Map<String, Map<String, Boolean>> vytvorMapuSmerovehoPomeru(String nazev) {
        Map<String, Map<String, Boolean>> mapa = new HashMap<>();

        mapa.put(nazev, vytvorMapuDruhuSilnice());

        return mapa;

    }

}
