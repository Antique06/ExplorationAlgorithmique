package com.example;

public class mainPOO {
    public static void main(String[] args) {
        PlateformeSejour ps = new PlateformeSejour("IHM/res/donnees.csv");
        String[][] csv = ps.getAdolescents();
        System.out.println(csv[0][0]);
    }
}
