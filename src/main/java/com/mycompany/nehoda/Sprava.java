/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nehoda;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dorgu
 */
public class Sprava {
    public Nastaveni lastNastaveni;
    List<Nastaveni> seznam;

    public Sprava() {
        seznam = new LinkedList<>();
    }


    public void pridej(String[] row) {
        lastNastaveni = new Nastaveni(row);
        lastNastaveni.porovnej(row);
        seznam.add(lastNastaveni);
    }
    public void pridejDen(String[] row) {
        lastNastaveni = new Nastaveni(row);
        lastNastaveni.porovnejDen(row);
        seznam.add(lastNastaveni);
    }
public boolean porovnejDen(String[]row)
{
    return lastNastaveni.porovnejDen(row);
}
    public boolean porovnej(String[] row) {
        return lastNastaveni.porovnej(row);
    }
}
