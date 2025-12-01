package SAE202;

import java.util.ArrayList;

/**
 * Classe représentant une plateforme de séjour
 * Permet l’ajout et la suppression d’adolescents.
 * @version 1.0
 * @since 2025
 */
public class PlateformeSejour {
    private ArrayList<Adolescent> adolescents;

    /**
     * Constructeur de plateformeSejour.
     * @param adolescents Liste des adolescents
     */
    public PlateformeSejour(ArrayList<Adolescent> adolescents) {
        this.adolescents = adolescents;
    }

    /**
     * Constructeur de plateformeSejour.
     */
    public PlateformeSejour() {
        this.adolescent = new ArrayList<Adolescent>;
   }

    /**
     * Ajoute un adolescent à la plateforme.
     * @param a Adolescent à ajouter
     * @return true si l’ajout a réussi
     */
    public boolean addAdolescent(Adolescent a) {
        return this.adolescents.add(a);
    }

    /**
     * Ajoute plusieurs adolescents à la plateforme.
     * @param adolescents Liste d’adolescents à ajouter
     * @return true si tous les ajouts ont réussi
     */
    public boolean addAdolescent(ArrayList<Adolescent> adolescents) {
        return this.adolescents.addAll(adolescents);
    }

    /**
     * Supprime un adolescent de la plateforme.
     * @param a Adolescent à supprimer
     * @return true si la suppression a réussi
     */
    public boolean removeAdolescent(Adolescent a) {
        return this.adolescents.remove(a);
    }

    /**
     * Supprime un adolescent à un index donné.
     * @param i Index de l’adolescent à supprimer
     * @return L’adolescent supprimé
     */
    public Adolescent removeAdolescent(int i) {
        return this.adolescents.remove(i);
    }

    /**
     * Supprime une liste d’adolescents.
     * @param adolescents Liste à supprimer
     * @return true si la suppression a réussi
     */
    public boolean removeAdolescent(ArrayList<Adolescent> adolescents) {
        return this.adolescents.removeAll(adolescents);
    }
    
}
