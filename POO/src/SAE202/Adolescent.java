package SAE202;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

    /**
     * Représente un adolescent avec ses informations personnelles et ses critères de compatibilité.
     * Permet d'évaluer la compatibilité entre deux adolescents à l'aide d'un score d'affinité.
     * Les adolescents peuvent également être sérialisés et désérialisés pour la persistance des données.
     *
     * @version 1.0
     * @since 2025
     */
public class Adolescent implements Serializable {
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String pays;
    private Map<Critere, String> critere;

    /**
     * Constructeur de la classe Adolescent.
     * @param prenom Prénom de l'adolescent
     * @param nom Nom de l'adolescent
     * @param dateNaissance Date de naissance
     * @param genre Genre
     * @param pays Pays d'origine
     * @param critere Map contenant les critères personnels
     */
    public Adolescent(String prenom, String nom, LocalDate dateNaissance, String genre, String pays, Map<Critere, String> critere) {
        boolean critereValide = true;
        try {
            controleCritere(critere);
        } catch(CritereInvalideException e) {
            System.out.println("CritereInvalideException " + e);
            critereValide = false;
        }
        if(critereValide == true) {
            this.prenom = prenom;
            this.nom = nom;
            this.dateNaissance = dateNaissance;
            this.genre = genre;
            this.pays = pays;
            this.critere = critere;
        }
    }

    /**
     * Retourne le prénom.
     * @return Le prénom.
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Retourne le nom.
     * @return Le nom.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne le pays d'origine.
     * @return Le pays.
     */
    public String getPays() {
        return this.pays;
    }

    /**
     * Calcule l'âge de l'adolescent en mois.
     * @return L'âge en mois.
     */
    public long getAge() {
        return this.dateNaissance.until(LocalDate.now()).toTotalMonths();
    }

    /**
     * Retourne la date de naissance.
     * @return La date de naissance.
     */
    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    /**
     * Retourne le genre de l'adolescent.
     * @return Le genre.
     */
    public String getGenre() {
        return this.genre;
    }

    /**
     * Retourne les critères de l’adolescent.
     * @return Map des critères
     */
    public Map<Critere, String> getCritere() {
        return this.critere;
    }

    /**
     * Supprime un adolescent de la liste donnée à l'index spécifié.
     * 
     * @param listAdolescents Le tableau d'adolescents.
     * @param n               L'index à supprimer.
     * @return Le nouveau tableau sans l'adolescent supprimé.
     */
    public static Adolescent[] remove(Adolescent[] listAdolescents, int n) {
        if (listAdolescents == null || listAdolescents.length == 0 || n < 0 || n >= listAdolescents.length) {
            return listAdolescents;
        }
        Adolescent[] nvListAdolescents = new Adolescent[listAdolescents.length - 1];
        for (int i = 0; i < n; i++) {
            nvListAdolescents[i] = listAdolescents[i];
        }
        for (int i = n + 1; i < listAdolescents.length; i++) {
            nvListAdolescents[i - 1] = listAdolescents[i];
        }
        return nvListAdolescents;
    }

    /**
     * Vérifie la compatibilité avec un autre adolescent.
     * @param a l'autre adolescent
     * @return true si le score d’affinité est inférieur ou égal à 5
     * @see #calculAffinite(Adolescent)
     */
    public boolean estCompatibleAvec(Adolescent a) {
        //int[] importanceCritere = new int[]{10, 100, 100, 10, 20, 20};
        int[] importanceCritere = new int[]{Critere.BIRTH_DATE.getPoids(), Critere.GUEST_ANIMAL_ALLERGY.getPoids(), Critere.GUEST_FOOD_CONSTRAINT.getPoids(), Critere.HOBBIES.getPoids(), Critere.GENDER.getPoids(), Critere.PAIR_GENDER.getPoids()};
        return this.calculAffinite(a, importanceCritere)>0;
    }

    /**
     * Calcule un score d’affinité avec un autre adolescent selon les critères.
     * @param a l'autre adolescent
     * @param importanceCritere le tableau définissant le niveau d'importance du critère
     * @return score d'affinité, un int qui permet de savoir si il sont compatible
     */
    public int calculAffinite(Adolescent a, int[] importanceCritere) {
        int scoreAffinite = 100;

        // Vérification de la différence d'âge
        scoreAffinite -= this.affiniteAge(a, importanceCritere);

        // Gestion des allergies aux animaux
        scoreAffinite -= this.affiniteAllergie(a, importanceCritere);

        // Comparaison des préférences alimentaires
        scoreAffinite -= this.affinitePreferenceAlimentaire(a, importanceCritere);

        // Gestion des hobbies
        ArrayList<String> tabHobbies1 = Adolescent.creationListeHobbies(this);

        ArrayList<String> tabHobbies2 = Adolescent.creationListeHobbies(a);
        
        int nbHobbiesCommun = calculHobbiesCommun(tabHobbies1, tabHobbies2);
        
        // Gestion du score basé sur les hobbies en commun
        scoreAffinite -= affiniteHobbies(a, importanceCritere, nbHobbiesCommun);

        // Comparaison des genres
        scoreAffinite -= affiniteGenre(a, importanceCritere);

        return scoreAffinite;
    }

    /**
     * Évalue l'affinité entre deux adolescents selon leur différence d'âge.
     * Si la différence d'âge est strictement supérieure à 18 mois, une pénalité est appliquée.
     *
     * @param a L'autre adolescent à comparer.
     * @param importanceCritere Tableau des poids pour chaque critère.
     * @return Pénalité appliquée au score d'affinité si différence d'âge trop importante.
     */
    public int affiniteAge(Adolescent a, int[] importanceCritere) {
        if (this.getAge() - a.getAge() > 18 || this.getAge() - a.getAge() < -18) {
            return importanceCritere[0];
        } else {
            return 0;
        }
    }

    /**
     * Évalue l'affinité selon l'allergie aux animaux de l'adolescent invité
     * et la présence d'animaux chez l'hôte.
     *
     * @param a L'autre adolescent (l'hôte) à comparer.
     * @param importanceCritere Tableau des poids pour chaque critère.
     * @return Pénalité appliquée au score d'affinité si allergie et animaux présents.
     */
    public int affiniteAllergie(Adolescent a, int[] importanceCritere) {
        if ("oui".equals(this.critere.get(Critere.GUEST_ANIMAL_ALLERGY))) {
            if ("oui".equals(a.critere.get(Critere.HOST_HAS_ANIMAL))) {
                return importanceCritere[1];
            }
        }
        return 0;
    }

    /**
     * Évalue l'affinité entre les préférences alimentaires de l'invité
     * et les habitudes de l'hôte.
     *
     * @param a L'autre adolescent (l'hôte) à comparer.
     * @param importanceCritere Tableau des poids pour chaque critère.
     * @return Pénalité appliquée si les préférences ne correspondent pas.
     */
    public int affinitePreferenceAlimentaire(Adolescent a, int[] importanceCritere) {
        if (this.critere.get(Critere.GUEST_FOOD_CONSTRAINT) != null && a.critere.get(Critere.HOST_FOOD) != null) {
            if (!this.critere.get(Critere.GUEST_FOOD_CONSTRAINT).equals(a.critere.get(Critere.HOST_FOOD))) {
                return importanceCritere[2];
            }
        }
        return 0;
    }

    /**
     * Évalue l'affinité basée sur les hobbies partagés.
     * Aucune affinité peut entraîner une forte pénalité, surtout si le pays est la France.
     *
     * @param a L'autre adolescent à comparer.
     * @param importanceCritere Tableau des poids pour chaque critère.
     * @param nbHobbiesCommun Nombre de hobbies en commun entre les deux adolescents.
     * @return Pénalité appliquée selon le nombre de loisirs partagés.
     */
    public int affiniteHobbies(Adolescent a, int[] importanceCritere, int nbHobbiesCommun) {
        if (nbHobbiesCommun == 0) {
            if ("France".equals(this.pays)) {
                return 100;
            } else {
                return importanceCritere[3] * 2;
            }
        } else if (nbHobbiesCommun == 1) {
            return importanceCritere[3];
        }
        return 0;
    }

    /**
     * Évalue l'affinité entre les genres souhaités et réels des deux adolescents.
     * Compare le genre souhaité pour un pair avec le genre réel de l'autre adolescent et inversement.
     *
     * @param a L'autre adolescent à comparer.
     * @param importanceCritere Tableau des poids pour chaque critère.
     * @return Pénalité appliquée selon la compatibilité des genres souhaités et réels.
     */
    public int affiniteGenre(Adolescent a, int[] importanceCritere) {
        int retour = 0;
        if (!this.critere.getOrDefault(Critere.PAIR_GENDER, "").equals(a.critere.getOrDefault(Critere.GENDER, ""))) {
            retour += importanceCritere[4];
        }
        if (this.critere.getOrDefault(Critere.GENDER, "").equals(a.critere.getOrDefault(Critere.PAIR_GENDER, ""))) {
            retour += importanceCritere[5];
        }
        return retour;
    }

    /**
     * Calcule le nombre de hobbies en commun entre deux listes.
     * L'égalité est déterminée sans tenir compte de la casse.
     *
     * @param tabHobbies1 Liste de hobbies du premier adolescent.
     * @param tabHobbies2 Liste de hobbies du second adolescent.
     * @return Le nombre de hobbies communs.
     */
    public int calculHobbiesCommun(ArrayList<String> tabHobbies1, ArrayList<String> tabHobbies2) {
        int nbHobbiesCommun = 0;
        for (String h1 : tabHobbies1) {
            for (String h2 : tabHobbies2) {
                if (h1.equalsIgnoreCase(h2)) {
                    nbHobbiesCommun++;
                }
            }
        }
        return nbHobbiesCommun;
    }

    /**
     * Crée une liste des hobbies à partir des critères de l’adolescent.
     *
     * @param a L’adolescent.
     * @return Une liste de hobbies.
     */
    public static ArrayList<String> creationListeHobbies(Adolescent a) {
        ArrayList<String> tabHobbies = new ArrayList<>();
        int debut = 0;
        String hobbiesA = a.critere.get(Critere.HOBBIES);
        if (hobbiesA != null) {
            for (int i = 0; i < hobbiesA.length(); i++) {
                if (hobbiesA.charAt(i) == ',') {
                    tabHobbies.add(hobbiesA.substring(debut, i).trim());
                    debut = i + 1;
                }
            }
            tabHobbies.add(hobbiesA.substring(debut).trim());
        }
        return tabHobbies;
    }

    /**
     * Calcule un score d’affinité avec un autre adolescent selon les critères.
     * @param a l'autre adolescent
     * @return score d'affinité, un int qui permet de savoir si il sont compatible
     */
    /**new int[]{10, 100, 100, 10, 20, 20}*/
    public int calculAffinite(Adolescent a) {
        return this.calculAffinite(a, new int[]{Critere.BIRTH_DATE.getPoids(), Critere.HOST_HAS_ANIMAL.getPoids(), Critere.HOST_FOOD.getPoids(), Critere.HOBBIES.getPoids(), Critere.GENDER.getPoids(), Critere.PAIR_GENDER.getPoids()} );
    }

    /**
     * Vérifie la validité des critères fournis dans une carte de critères.
     * Les valeurs doivent correspondre à des choix pré-définis pour être valides.
     *
     * @param critereMap Une carte contenant les critères à valider.
     * @throws CritereInvalideException Si l'un des critères est invalide.
     */
    public static void controleCritere(Map<Critere, String> critereMap) throws CritereInvalideException {
        if(critereMap == null) {
            throw new CritereInvalideException("Critere invalide : critere null");
        } else {
            if (critereMap.get(Critere.GUEST_FOOD_CONSTRAINT) == null ||
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("vegetarien") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans gluten") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans lactose") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("vegan") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans noix") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("vegetalien") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("sans arachides") &&
                !critereMap.get(Critere.GUEST_FOOD_CONSTRAINT).equals("")) {
                throw new CritereInvalideException("Critere Invalide : critere GUEST_FOOD_CONSTRAINT invalide");
            }
            if (critereMap.get(Critere.GENDER) == null ||
                !critereMap.get(Critere.GENDER).equals("femme") &&
                !critereMap.get(Critere.GENDER).equals("homme") &&
                !critereMap.get(Critere.GENDER).equals("autre")) {
                throw new CritereInvalideException("Critere invalide : critere GENDER invalide");
            }
            if (critereMap.get(Critere.GUEST_ANIMAL_ALLERGY) == null ||
                !critereMap.get(Critere.GUEST_ANIMAL_ALLERGY).equals("oui") &&
                !critereMap.get(Critere.GUEST_ANIMAL_ALLERGY).equals("non")) {
                throw new CritereInvalideException("Critere invalide : critere GUEST_ANIMAL_ALLERGY invalide");
            }
            if (critereMap.get(Critere.HOST_HAS_ANIMAL) == null ||
                !critereMap.get(Critere.HOST_HAS_ANIMAL).equals("oui") &&
                !critereMap.get(Critere.HOST_HAS_ANIMAL).equals("non")) {
                throw new CritereInvalideException("Critere invalide : critere HOST_HAS_ANIMAL invalide");
            }
            if (critereMap.get(Critere.HOST_FOOD) == null ||
                !critereMap.get(Critere.HOST_FOOD).equals("vegetarien") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans gluten") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans lactose") &&
                !critereMap.get(Critere.HOST_FOOD).equals("vegan") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans noix") &&
                !critereMap.get(Critere.HOST_FOOD).equals("vegetalien") &&
                !critereMap.get(Critere.HOST_FOOD).equals("sans arachides") &&
                !critereMap.get(Critere.HOST_FOOD).equals("")) {
                throw new CritereInvalideException("Critere Invalide : critere HOST_FOOD invalide");
            }
            if (critereMap.get(Critere.PAIR_GENDER) == null ||
                !critereMap.get(Critere.PAIR_GENDER).equals("femme") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("homme") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("other") &&
                !critereMap.get(Critere.PAIR_GENDER).equals("")) {
                throw new CritereInvalideException("Critere invalide : critere PAIR_GENDER invalide");
            }
            if (critereMap.get(Critere.HISTORY) == null ||
                !critereMap.get(Critere.HISTORY).equals("same") &&
                !critereMap.get(Critere.HISTORY).equals("other") &&
                !critereMap.get(Critere.HISTORY).equals("")) {
                throw new CritereInvalideException("Critere invalide : critere HISTORY invalide");
            }
        }
    }

    /**
     * Compare l'adolescent courant avec un autre à partir de leur prénom et nom.
     *
     * @param prenom Le prénom à comparer.
     * @param nom    Le nom à comparer.
     * @return true si les deux correspondent.
     */
    public boolean equals(String prenom, String nom) {
        if(this.getPrenom() != null && this.getNom() != null && prenom != null && nom != null) {
            return (this.getPrenom().equals(prenom) && this.getNom().equals(nom));
        }
        return false;
    }

    /**
     * Sérialise l'objet courant dans un fichier.
     * Le fichier est nommé d'après le prénom et le nom de l'adolescent.
     */
    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/"+this.prenom+" "+this.nom+".ser")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Désérialise un objet Adolescent depuis un fichier.
     *
     * @param prenom Le prénom de l'adolescent.
     * @param nom Le nom de l'adolescent.
     * @return L'objet Adolescent désérialisé, ou null en cas d'erreur.
     */
    public static Adolescent deserialisation(String prenom, String nom) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("../res/Object/"+prenom+" "+nom+".java")))) {
            Adolescent tmp = (Adolescent)ois.readObject();
            return tmp;
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}