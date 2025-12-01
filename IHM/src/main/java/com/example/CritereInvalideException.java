package com.example;

/**
 * Exception personnalisée utilisée pour signaler qu'un ou plusieurs critères sont invalides
 * dans le contexte de l'application (par exemple, lors de la création ou de l'évaluation d’un adolescent).
 *
 * <p>Cette exception est levée lorsque les valeurs associées à un ou plusieurs critères ne respectent
 * pas les contraintes attendues (valeurs nulles, incorrectes ou incohérentes).</p>
 *
 * @version 1.0
 * @since 2025
 */
public class CritereInvalideException extends Exception {

    /**
     * Constructeur de la classe {@code CritereInvalideException}.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public CritereInvalideException(String message) {
        super(message);
    }
}