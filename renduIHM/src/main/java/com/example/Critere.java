package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Énumération des différents critères utilisés pour évaluer l'affinité entre adolescents.
 * @version 1.0
 * @since 2025
 */
public enum Critere {
    BIRTH_DATE('d', 10),
    GUEST_ANIMAL_ALLERGY('b', 100),
    HOST_HAS_ANIMAL('b', 100),
    GUEST_FOOD_CONSTRAINT('t', 100),
    HOST_FOOD('t', 100),
    HOBBIES('t', 10),
    GENDER('t', 20),
    PAIR_GENDER('t', 20),
    HISTORY('t', 100);

    private char type;
    private int poids;

    /**
     * Constructeur de l’énumération Critere.
     * @param type Type associé au critère
     * @param i 
     */
    private Critere(char type) {
        this.type = type;
}
    private Critere(char type, int poids) {
        this.type = type;
        this.poids = poids;
    }

    //Renvoie le type associé au critère.
    private char getType() {
        return this.type;
    }

    //Renvoie le poids associé au critère
    public int getPoids() {
        return poids;
    }

    //permet de mettre un poids à un critère
    public void setPoids(int poids) {
        this.poids = poids;
    }

/**
 * Sérialise l'objet courant (instance de Critere) dans un fichier.
 * Le fichier de sortie est situé dans le répertoire "../src/SAE202" et est nommé "Critere.java".
 */
    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/Critere.java")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }
}
