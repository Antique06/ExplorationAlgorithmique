package com.example;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 


/**
 * Classe d’affectation d’adolescents à une plateforme.
 * @version 1.0
 * @since 2025
 */
public class Affectation implements Serializable {

    //tableaux d'ado pour les affectation
    public Map<Adolescent, Adolescent> plateforme_Affectation;
    
    /**
     * Constructeur par défaut de la classe Affectation.
     * Initialise la liste des adolescents affectés.
     */
    public Affectation() {
        this.plateforme_Affectation = new HashMap<Adolescent, Adolescent>();
    }


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

    
        

    public Adolescent getAppariement(Adolescent a) {
        if(this.plateforme_Affectation.containsKey(a)) {
            return this.plateforme_Affectation.get(a);
        } else {
            return null;
        }
    }



    /**
     * Sérialise l'objet courant (instance de Affectation) dans un fichier.
     * Le fichier de sortie est situé dans le répertoire "../src/SAE202" et est nommé "Affectation.java".
     */


    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/Affectation.java")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }
}