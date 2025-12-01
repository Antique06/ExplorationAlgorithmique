package com.example;

/**
 * Exception personnalisée pour signaler des critères invalides dans une application.
 * Cette exception est levée lorsque des critères fournis ne respectent pas les contraintes ou valeurs attendues.
 */
public class CritereInvalideException extends Exception {

    /**
     * Constructeur de la classe CritereInvalideException.
     *
     * @param message Le message décrivant la cause de l'exception.
     */
    public CritereInvalideException(String message) {
        super(message);
    }
}