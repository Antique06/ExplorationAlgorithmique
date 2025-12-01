package SAE202;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Énumération représentant les différents critères permettant d’évaluer l’affinité entre adolescents.
 * Chaque critère possède un type (caractère) et un poids (importance numérique).
 *
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

    /**
     * Type associé au critère (par exemple : 'd' pour date, 'b' pour booléen, 't' pour texte).
     */
    private char type;

    /**
     * Poids (importance) du critère dans le calcul d’affinité.
     */
    private int poids;

    /**
     * Constructeur privé de l’énumération Critere, utilisé uniquement pour BIRTH_DATE.
     *
     * @param type Caractère représentant le type du critère.
     */
    private Critere(char type) {
        this.type = type;
}

    /**
     * Constructeur principal de l’énumération Critere.
     *
     * @param type  Caractère représentant le type du critère.
     * @param poids Poids ou importance du critère.
     */
    private Critere(char type, int poids) {
        this.type = type;
        this.poids = poids;
    }

    /**
     * Retourne le type du critère (non exposé publiquement par défaut).
     *
     * @return Le caractère représentant le type.
     */
    private char getType() {
        return this.type;
    }

    /**
     * Retourne le poids du critère, utilisé pour pondérer son influence dans le calcul d'affinité.
     *
     * @return Le poids du critère.
     */
    public int getPoids() {
        return poids;
    }

    /**
     * Modifie le poids (importance) du critère.
     *
     * @param poids Nouvelle valeur du poids à attribuer.
     */
    public void setPoids(int poids) {
        this.poids = poids;
    }

    /**
     * Sérialise l'instance courante de Critere dans un fichier.
     * Le fichier est enregistré dans le répertoire {@code ../res/Object/} avec le nom {@code Critere.ser}.
     */
    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/Critere.ser")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Désérialise un objet Critere depuis un fichier.
     *
     * @return L'objet Critere désérialisé, ou null en cas d'erreur.
     */
    public static Critere deserialisation() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("../res/Object/Critere.java")))) {
            Critere tmp = (Critere)ois.readObject();
            return tmp;
        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}
