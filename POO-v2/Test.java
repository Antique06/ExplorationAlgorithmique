package SAE202;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class Test {
    private Adolescent ado1;
    private Adolescent ado2;
    private Adolescent adoDifferent;
    private PlateformeSejour plateforme;

    @BeforeEach
    public void initialisation() {
        Map<Critere, String> critere1 = new EnumMap<>(Critere.class);
        critere1.put(Critere.BIRTH_DATE, "2008-01-01");
        critere1.put(Critere.GUEST_ANIMAL_ALLERGY, "non");
        critere1.put(Critere.HOST_HAS_ANIMAL, "non");
        critere1.put(Critere.HOST_FOOD, "végétarien");
        critere1.put(Critere.GUEST_FOOD_CONSTRAINT, "végétarien");
        critere1.put(Critere.HOBBIES, "sports");
        critere1.put(Critere.GENDER, "homme");
        critere1.put(Critere.PAIR_GENDER, "femme");

        Map<Critere, String> critere2 = new EnumMap<>(Critere.class);
        critere2.put(Critere.BIRTH_DATE, "2008-02-01");
        critere2.put(Critere.GUEST_ANIMAL_ALLERGY, "non");
        critere2.put(Critere.HOST_HAS_ANIMAL, "non");
        critere2.put(Critere.HOST_FOOD, "végétarien");
        critere2.put(Critere.GUEST_FOOD_CONSTRAINT, "végétarien");
        critere2.put(Critere.HOBBIES, "sports");
        critere2.put(Critere.GENDER, "femme");
        critere2.put(Critere.PAIR_GENDER, "homme");

        Map<Critere, String> critereDifferent = new EnumMap<>(Critere.class);
        critereDifferent.put(Critere.BIRTH_DATE, "2000-01-01");
        critereDifferent.put(Critere.GUEST_ANIMAL_ALLERGY, "oui");
        critereDifferent.put(Critere.HOST_HAS_ANIMAL, "oui");
        critereDifferent.put(Critere.HOST_FOOD, "");
        critereDifferent.put(Critere.GUEST_FOOD_CONSTRAINT, "végétarien");
        critereDifferent.put(Critere.HOBBIES, "lire");
        critereDifferent.put(Critere.GENDER, "homme");
        critereDifferent.put(Critere.PAIR_GENDER, "homme");

        ado1 = new Adolescent("Corentin", "Chocraux", LocalDate.of(2008, 1, 1), "homme", "France", critere1);
        ado2 = new Adolescent("Emily", "Paris", LocalDate.of(2008, 2, 1), "femme", "France", critere2);
        adoDifferent = new Adolescent("Martin", "Morenville", LocalDate.of(2000, 1, 1), "homme", "France", critereDifferent);

        plateforme = new PlateformeSejour(new ArrayList<>());
    }

    @Test
    public void testCompatibiliteV() {
        assertTrue(ado1.estCompatibleAvec(ado2));
        assertTrue(ado2.estCompatibleAvec(ado1));
    }

    @Test
    public void testCompatibiliteI() {
        assertFalse(ado1.estCompatibleAvec(adoDifferent));
        assertFalse(adoDifferent.estCompatibleAvec(ado1));
    }

    @Test
    public void testASPlateforme() {
        assertTrue(plateforme.addAdolescent(ado1));
        assertEquals(ado1, plateforme.removeAdolescent(0));
    }

    @Test
    public void testAPlateforme() {
        ArrayList<Adolescent> plateforme1 = new ArrayList<>();
        plateforme1.add(ado1);
        plateforme1.add(ado2);
        assertTrue(plateforme.addAdolescent(plateforme1));
    }

    @Test
    public void testAffectation() {
        Affectation affectation = new Affectation();
        assertNotNull(affectation.platforme_Affectation);
    }
}