package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une plateforme de séjour permettant de gérer une base d'adolescents.
 * Elle permet l'ajout, la suppression, l'import/export et la sérialisation d'adolescents.
 *
 * @version 1.0
 * @since 2025
 */
public class PlateformeSejour {
    private String[][] adolescents;

    static int nbAdo = 0;
    static char separator = '/';
    static String chemin = null;

    /**
     * Constructeur de la classe PlateformeSejour.
     *
     * @param chemin Le chemin vers le fichier CSV contenant les adolescents à importer.
     */
    public PlateformeSejour(String chemin) {
        PlateformeSejour.chemin = chemin;
        PlateformeSejour.nbAdo = this.calculNombreAdo();
        this.adolescents = PlateformeSejour.importer();
    }

    /**
     * Ajoute un adolescent à la plateforme.
     *
     * @param a L'adolescent à ajouter.
     * @return true si l'ajout a réussi.
     */
    public boolean addAdolescent(Adolescent a) {
        this.addNullLine();
        int derniereLigne = this.adolescents.length-1;
        this.adolescents[derniereLigne][0] = a.getPrenom();
        this.adolescents[derniereLigne][1] = a.getNom();
        this.adolescents[derniereLigne][2] = a.getPays();
        this.adolescents[derniereLigne][3] = a.getDateNaissance().toString();
        this.adolescents[derniereLigne][4] = a.getCritere().get("GUEST_ANIMAL_ALLERGY");
        this.adolescents[derniereLigne][5] = a.getCritere().get("HOST_HAS_ANIMAL");
        this.adolescents[derniereLigne][6] = a.getCritere().get("GUEST_FOOD_CONSTRAINT");
        this.adolescents[derniereLigne][7] = a.getCritere().get("HOST_FOOD");
        this.adolescents[derniereLigne][8] = a.getCritere().get("HOBBIES");
        this.adolescents[derniereLigne][9] = a.getCritere().get("GENDER");
        this.adolescents[derniereLigne][10] = a.getCritere().get("PAIR_GENDER");
        this.adolescents[derniereLigne][11] = a.getCritere().get("HISTORY");
        return true;
    }

    /**
     * Ajoute une liste d'adolescents à la plateforme.
     *
     * @param adolescents Liste d'adolescents à ajouter.
     * @return true si tous les ajouts ont réussi.
     */
    public boolean addAdolescent(ArrayList<Adolescent> adolescents) {
        for(Adolescent a : adolescents) {
            this.addAdolescent(a);
        }
        return true;
    }

    /**
     * Supprime un adolescent spécifique de la plateforme.
     *
     * @param a L'adolescent à supprimer.
     * @return true si la suppression a réussi.
     */
    public boolean removeAdolescent(Adolescent a) {
        int i=0;
        while(i<this.adolescents.length && !a.getPrenom().equals(this.adolescents[i][0]) || !a.getNom().equals(this.adolescents[i][1])) {
            i++;
        }
        return this.removeLine(i);
    }

    /**
     * Renvoie le tableau des adolescents actuellement présents dans la plateforme.
     *
     * @return Un tableau 2D représentant les adolescents.
     */
    public String[][] getAdolescents() {
        return this.adolescents;
    }

    /**
     * Supprime un adolescent par son index dans le tableau.
     *
     * @param i L'index de l'adolescent à supprimer.
     * @return Les données de l'adolescent supprimé.
     */
    public String[] removeAdolescent(int i) {
        String[] tmp = this.adolescents[i];
        this.removeLine(i);
        return tmp;
    }

    /**
     * Supprime une ligne dans le tableau d’adolescents.
     *
     * @param line L’index de la ligne à supprimer.
     * @return true si la suppression a réussi.
     */
    public boolean removeLine(int line) {
        String[][] tmp = new String[this.adolescents.length-1][this.adolescents[0].length];
        for(int i=0; i<line; i++) {
            tmp[i] = this.adolescents[i];
        }
        for(int i=line+1; i<this.adolescents.length; i++) {
            tmp[i] = this.adolescents[i];
        }
        this.adolescents = tmp;
        return true;
    }

    /**
     * Supprime une liste d’adolescents de la plateforme.
     *
     * @param adolescents La liste d’adolescents à supprimer.
     * @return true si la suppression a réussi.
     */
    public boolean removeAdolescent(ArrayList<Adolescent> adolescents) {
        for(Adolescent a : adolescents) {
            removeAdolescent(a);
        }
        return true;
    }

    /**
     * Calcule le nombre d’adolescents dans le fichier d’import.
     *
     * @return Le nombre d’adolescents.
     */
    public int calculNombreAdo() {
        int compteur = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(chemin)))) {
            br.readLine(); // Ignore la première ligne (en-tête)
            while(br.ready()) {
                compteur++;
                br.readLine();
            }
        } catch(IOException e) {
            System.err.println("IO failure");
            e.printStackTrace();
        }
        return compteur;
    }

    /**
     * Importe les adolescents depuis le fichier défini par {@code chemin}.
     *
     * @return Un tableau 2D des adolescents, ou null en cas d’erreur.
     */
    public static String[][] importer() {
        return importer(PlateformeSejour.chemin);
    }

    /**
     * Importe les adolescents depuis un fichier donné.
     *
     * @param chemin Le chemin du fichier à importer.
     * @return Un tableau 2D des adolescents, ou null en cas d’erreur.
     */
    public static String[][] importer(String chemin) {
        try(BufferedReader br = new BufferedReader(new FileReader(new File(chemin)))) {
            String[][] tab = new String[PlateformeSejour.nbAdo][12];
            String line;
            br.readLine();  // Ignore la première ligne (en-tête).
            int k = 0;
            Map critereMap;
            Critere[] critereValues = Critere.values();
            while(br.ready()) { 
                line = br.readLine();
                int j = 0;
                tab[k][j] = "";
                critereMap = new HashMap<Critere, String>();
                for(int i=0; i<line.length(); i++) {
                    if(line.charAt(i) != PlateformeSejour.separator) {
                        tab[k][j] = "" + tab[k][j] + line.charAt(i);
                    } else {
                        if(j>=3) {
                            critereMap.put(critereValues[j-3], tab[k][j]);
                        }
                        j++;
                        tab[k][j] = "";
                    }
                }
                critereMap.put(critereValues[j-3], tab[k][j]);
                try {
                    Adolescent.controleCritere(critereMap);
                } catch(CritereInvalideException e) {
                    System.out.println("CritereInvalideException " + e);
                    tab[k] = new String[12];
                    k--;
                    PlateformeSejour.nbAdo--;
                    tab = PlateformeSejour.retirerLigne(tab);
                }
                k++;
            }
            return tab;
        } catch(IOException e) {
            System.err.println("IO failure");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Exporte les données des adolescents vers un fichier CSV.
     * Le fichier est situé dans "POO/res/donneesExporter.csv".
     */
    public void exporter() {
        File f = new File("POO/res/donneesExporter.csv");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("FORENAME/NAME/COUNTRY/BIRTH_DATE/GUEST_ANIMAL_ALLERGY/HOST_HAS_ANIMAL/GUEST_FOOD_CONSTRAINT/HOST_FOOD/HOBBIES/GENDER/PAIR_GENDER/HISTORY\n");
            String chaine;
            for(int i=0; i<this.adolescents.length; i++) {
                chaine = this.adolescents[i][0];
                for(int j=1; j<this.adolescents[i].length; j++) {
                    chaine += '/' + this.adolescents[i][j];
                }
                bw.write(chaine + "\n");
            }
            f.createNewFile();
        } catch (IOException e) {
            System.err.println("IO failure");
            e.printStackTrace();
        }
    }

    /**
     * Sérialise l’objet {@code PlateformeSejour} dans un fichier.
     * Le fichier est situé dans "../res/Object/PlateformeSejour.ser".
     */
    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/PlateformeSejour.ser")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Désérialise une instance de {@code PlateformeSejour} depuis le fichier par défaut.
     *
     * @return L’instance désérialisée ou null en cas d’erreur.
     */
    public static PlateformeSejour deserialisation() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("../res/Object/PlateformeSejour.java")))) {
            PlateformeSejour tmp = (PlateformeSejour)ois.readObject();
            return tmp;
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }


    /**
     * Ajoute une ligne vide (null) à la matrice d’adolescents.
     */
    public void addNullLine() {
        String[][] tmp = new String[this.adolescents.length+1][this.adolescents[0].length];
        for(int i=0; i<this.adolescents.length; i++) {
            tmp[i] = this.adolescents[i];
        }
        this.adolescents = tmp;
    }


    /**
     * Retire une ligne du tableau (utile lors de l'import).
     *
     * @param tab Le tableau source.
     * @return Un nouveau tableau sans la ligne invalide.
     */
    private static String[][] retirerLigne(String[][] tab) {
        String[][] newTab = new String[nbAdo][12];
        int i=0;
        while(tab[i][0] != null) {
            newTab[i] = tab[i];
            i++;
        }
        return newTab;
    }
}
