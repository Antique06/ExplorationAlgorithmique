package SAE202;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe représentant une plateforme de séjour
 * Permet l’ajout et la suppression d’adolescents.
 * @version 1.0
 * @since 2025
 */
public class PlateformeSejour {
    private String[][] adolescents;

    static int nbAdo = 0;
    static char separator = '/';
    static String chemin = "../res/donnees.csv";

    /**
     * Constructeur de plateformeSejour.
     * @param adolescents Liste des adolescents
     */
    public PlateformeSejour() {
        PlateformeSejour.nbAdo = this.calculNombreAdo();
        this.adolescents = PlateformeSejour.importer();
    }

    /**
     * Ajoute un adolescent à la plateforme.
     * @param a Adolescent à ajouter
     * @return true si l’ajout a réussi
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

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String genre;
    private String pays;
    private Map<Critere, String> critere;

    /**
     * Ajoute plusieurs adolescents à la plateforme.
     * @param adolescents Liste d’adolescents à ajouter
     * @return true si tous les ajouts ont réussi
     */
    public boolean addAdolescent(ArrayList<Adolescent> adolescents) {
        for(Adolescent a : adolescents) {
            this.addAdolescent(a);
        }
        return true;
    }

    /**
     * Supprime un adolescent de la plateforme.
     * @param a Adolescent à supprimer
     * @return true si la suppression a réussi
     */
    public boolean removeAdolescent(Adolescent a) {
        int i=0;
        while(i<this.adolescents.length && !a.getPrenom().equals(this.adolescents[i][0]) || !a.getNom().equals(this.adolescents[i][1])) {
            i++;
        }
        return this.removeLine(i);
    }

    /**
     * Supprime un adolescent à un index donné.
     * @param i Index de l’adolescent à supprimer
     * @return L’adolescent supprimé
     */
    public String[] removeAdolescent(int i) {
        String[] tmp = this.adolescents[i];
        this.removeLine(i);
        return tmp;
    }

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
     * Supprime une liste d’adolescents.
     * @param adolescents Liste à supprimer
     * @return true si la suppression a réussi
     */
    public boolean removeAdolescent(ArrayList<Adolescent> adolescents) {
        for(Adolescent a : adolescents) {
            removeAdolescent(a);
        }
        return true;
    }

    public int calculNombreAdo() {
        int compteur = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(chemin)))) {
            br.readLine();
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

    public static String[][] importer() {
        try(BufferedReader br = new BufferedReader(new FileReader(new File(chemin)))) {
            String[][] tab = new String[PlateformeSejour.nbAdo][12];
            String line;
            br.readLine(); 
            int k = 0;
            while(br.ready()) { 
                line = br.readLine();
                System.out.println(line);
                int j = 0;
                tab[k][j] = "";
                for(int i=0; i<line.length(); i++) {
                    if(line.charAt(i) != PlateformeSejour.separator) {
                        tab[k][j] = "" + tab[k][j] + line.charAt(i);
                    } else {
                        j++;
                        tab[k][j] = "";
                    }
                }
                k++;
            }
            for(String s : tab[0]) {
                System.out.println(s);
            }
            return tab;
        } catch(IOException e) {
            System.err.println("IO failure");
            e.printStackTrace();
        }
        return null;
    }

    public void exporter() {
        File f = new File("../res/donnees.csv");
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

    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/PlateformeSejour.java")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }
    public static PlateformeSejour deserialisation() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("../res/Object/PlateformeSejour.java")))) {
            PlateformeSejour tmp = (PlateformeSejour)ois.readObject();
            return tmp;
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }

    public void addNullLine() {
        String[][] tmp = new String[this.adolescents.length+1][this.adolescents[0].length];
        for(int i=0; i<this.adolescents.length; i++) {
            tmp[i] = this.adolescents[i];
        }
        this.adolescents = tmp;
    }
}
