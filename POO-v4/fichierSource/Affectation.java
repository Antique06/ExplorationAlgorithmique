package SAE202;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 


/**
 * Classe représentant l'affectation automatique de paires d'adolescents sur une plateforme.
 * Chaque adolescent est associé à un autre selon un calcul d'affinité.
 *
 * @version 1.0
 * @since 2025
 */
public class Affectation implements Serializable {

    /**
     * Dictionnaire contenant les paires d'adolescents affectés entre eux.
     * Chaque adolescent est une clé et la valeur correspond à son binôme.
     */
    public Map<Adolescent, Adolescent> plateforme_Affectation;
    
    /**
     * Constructeur par défaut de la classe Affectation.
     * Initialise la structure de données pour les appariements.
     */
    public Affectation() {
        this.plateforme_Affectation = new HashMap<Adolescent, Adolescent>();
    }

    /**
     * Réalise une affectation automatique entre les adolescents passés en paramètre.
     * Chaque adolescent est apparié avec celui qui a le plus grand score d'affinité parmi les non encore affectés.
     * Une fois apparié, un adolescent n'est plus pris en compte dans les appariements suivants.
     *
     * @param listAdolescents Tableau contenant les adolescents à apparier.
     */
    public void affectationAutomatique(Adolescent[] listAdolescents) {
        int max;
        int indice;
        Adolescent a1, a2;
        for(int i=0; i<listAdolescents.length; i++) {
            if(!this.plateforme_Affectation.containsKey(listAdolescents[i])) {
                a1 = listAdolescents[i];
                max = 0;
                indice = -1;
                for(int j=i+1; j<listAdolescents.length; j++) {
                    if(!this.plateforme_Affectation.containsKey(listAdolescents[j])) {
                        a2 = listAdolescents[j];
                        if(a1.calculAffinite(a2) > max) {
                            max = a1.calculAffinite(a2);
                            indice = j;
                        }
                    }
                }
                if(indice != -1) {
                    this.plateforme_Affectation.put(a1, listAdolescents[indice]);
                    this.plateforme_Affectation.put(listAdolescents[indice], a1);
                }
            }
        }
    }

    
    /**
     * Retourne le binôme associé à un adolescent donné.
     *
     * @param a L'adolescent dont on cherche l'appariement.
     * @return L'adolescent apparié, ou {@code null} s'il n'est pas encore affecté.
     */
    public Adolescent getAppariement(Adolescent a) {
        if(this.plateforme_Affectation.containsKey(a)) {
            return this.plateforme_Affectation.get(a);
        } else {
            return null;
        }
    }



    /**
     * Sérialise l'objet Affectation actuel dans un fichier situé à l'adresse :
     * {@code ../res/Object/Affectation.ser}.
     */
    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/Affectation.ser")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Désérialise un objet Affectation depuis un fichier.
     *
     * @return L'objet Affectation désérialisé, ou null en cas d'erreur.
     */
    public static Affectation deserialisation() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("../res/Object/Affectation.java")))) {
            Affectation tmp = (Affectation)ois.readObject();
            return tmp;
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}