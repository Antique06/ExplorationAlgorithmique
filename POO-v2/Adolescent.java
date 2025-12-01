package SAE202;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import java.util.HashMap;

    /**
     * Représente un adolescent avec ses informations personnelles et critères de compatibilité.
     * Permet d'évaluer la compatibilité entre deux adolescents selon divers critères.
     * @version 1.0
     * @since 2025
     */
public class Adolescent {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String genre;
    private String pays;
    private Map<Critere, String> critere;

    /**
     * Constructeur de la classe Adolescent.
     * @param nom Nom de l'adolescent
     * @param prenom Prénom de l'adolescent
     * @param dateNaissance Date de naissance
     * @param genre Genre
     * @param pays Pays d'origine
     * @param critere Map contenant les critères personnels
     */
    public Adolescent(String nom, String prenom, LocalDate dateNaissance, String genre, String pays, Map<Critere, String> critere) {
        try {
            controleCritere(critere);
            this.nom = nom;
            this.prenom = prenom;
            this.dateNaissance = dateNaissance;
            this.genre = genre;
            this.pays = pays;
            this.critere = critere;
        } catch(CritereInvalideException e) {
            System.err.println("Critere invalide");
            e.printStackTrace();
        }
    }

    //Calcule l'âge en mois.
    public long getAge() {
        return this.dateNaissance.until(LocalDate.now()).toTotalMonths();
    }

    /**
     * Retourne les critères de l’adolescent.
     * @return Map des critères
     */
    public Map<Critere, String> getCritere() {
        return this.critere;
    }

    /**
     * Vérifie la compatibilité avec un autre adolescent.
     * @param a l'autre adolescent
     * @return true si le score d’affinité est inférieur ou égal à 5
     * @see #calculAffinite(Adolescent)
     */
    public boolean estCompatibleAvec(Adolescent a) {
        return this.calculAffinite(a)<=5;
    }

    /**
     * Calcule un score d’affinité avec un autre adolescent selon les critères.
     * @param a l'autre adolescent
     * @return score d'affinité, un int qui permet de savoir si il sont compatible 
     */
    public int calculAffinite(Adolescent a) {
        int scoreAffinite=0;
        if(this.getAge()-a.getAge() > 18 || this.getAge()-a.getAge() < -18) {
            scoreAffinite++;
        }
        if(this.critere.get("GUEST_ANIMAL_ALLERGY").equals("true")) {
            if(a.critere.get("HOST_HAS_ANIMAL").equals("true")) {
                scoreAffinite += 100;
            }
        }
        if(this.critere.get("Guest_FOOD")!=null && a.critere.get("HOST_FOOD")!=null) {
            if(!this.critere.get("GUEST_FOOD").equals(a.critere.get("HOST_FOOD"))) {
                scoreAffinite += 100;
            }
        }
        if(!this.critere.get("HOBBIES").equals(a.critere.get("HOBBIES"))) {
            if(this.pays.equals("France")) {
                scoreAffinite += 100;
            } else {
                scoreAffinite += 2;
            }
        }
        if(!this.critere.get("PAIR_GENDER").equals(a.critere.get("GENDER"))) {
            scoreAffinite += 2;
        }
        if(this.critere.get("GENDER").equals(a.critere.get("PAIR_GENDER"))) {
            scoreAffinite += 2;
        }
        return scoreAffinite;
    }

    public static void controleCritere(Map<Critere, String> critereMap) throws CritereInvalideException {
        if(critereMap == null) {
            throw new CritereInvalideException("Critere invalide : critere null");
        } else {
            if (!critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("végétarien") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans gluten") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans lactose") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("végan") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans noix") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("")) {
                throw new CritereInvalideException("Critere Invalide : critere GUEST_FOOD_CONSTRAINT invalide");
            }
            if (!critereMap.get(Critere.GENDER).equals("femme") &&
                !critereMap.get(Critere.GENDER).equals("homme") &&
                !critereMap.get(Critere.GENDER).equals("autre")) {
                throw new CritereInvalideException("Critere invalide : critere GENDER invalide");
            }
            if (!critereMap.get(Critere.GUEST_ANIMAL_ALLERGY).equals("oui") &&
                !critereMap.get(Critere.GUEST_ANIMAL_ALLERGY).equals("non")) {
                throw new CritereInvalideException("Critere invalide : critere GUEST_ANIMAL_ALLERGY invalide");
            }
            if (!critereMap.get(Critere.HOST_HAS_ANIMAL).equals("oui") &&
                !critereMap.get(Critere.HOST_HAS_ANIMAL).equals("non")) {
                throw new CritereInvalideException("Critere invalide : critere HOST_HAS_ANIMAL invalide");
            }
            if (!critereMap.get(Critere.HOST_FOOD).equals("végétarien") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans gluten") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans lactose") &&
                !critereMap.get(Critere.HOST_FOOD).equals("végan") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans noix") &&
                !critereMap.get(Critere.HOST_FOOD).equals("")) {
                throw new CritereInvalideException("Critere Invalide : critere HOST_FOOD invalide");
            }
            if (!critereMap.get(Critere.PAIR_GENDER).equals("femme") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("homme") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("other") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("")) {
                throw new CritereInvalideException("Critere invalide : critere PAIR_GENDER invalide");
            }
            if (!critereMap.get(Critere.HISTORY).equals("same") &&
                !critereMap.get(Critere.HISTORY).equals("other") &&
                !critereMap.get(Critere.HISTORY).equals("")) {
                throw new CritereInvalideException("Critere invalide : critere HISTORY invalide");
            }
        }
    }
}