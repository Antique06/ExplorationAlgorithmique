package SAE202;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
 


/**
 * Classe d’affectation d’adolescents à une plateforme.
 * @version 1.0
 * @since 2025
 */
public class Affectation implements Serializable {
    //info pour l'import du fichier CSV

    //tableaux d'ado pour les affectation
    public ArrayList<Adolescent> plateforme_Affectation;
    
    public Affectation() {
        this.plateforme_Affectation = new ArrayList<Adolescent>();
    }

    public void serialisation() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("../res/Object/Affectation.java")))) {
            oos.writeObject(this);
        } catch(Exception e) {e.printStackTrace();}
    }
}