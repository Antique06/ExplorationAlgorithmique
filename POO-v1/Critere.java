package SAE202;

/**
 * Énumération des différents critères utilisés pour évaluer l'affinité entre adolescents.
 * @version 1.0
 * @since 2025
 */
public enum Critere {
    BIRTH_DATE('d'),
    GUEST_ANIMAL_ALLERGY('b'),
    HOST_HAS_ANIMAL('b'),
    GUEST_FOOD_CONSTRAINT('t'),
    HOST_FOOD('t'),
    HOBBIES('t'),
    GENDER('t'),
    PAIR_GENDER('t'),
    HISTORY('t');

    private char type;

    /**
     * Constructeur de l’énumération Critere.
     * @param type Type associé au critère
     */
    private Critere(char type) {
        this.type = type;
    }

    //Renvoie le type associé au critère.
    private char getType() {
        return this.type;
    }
}