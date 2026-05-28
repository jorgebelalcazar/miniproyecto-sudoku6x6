package com.example.miniproyectosudoku6x6.controller.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Adapter class that filters keyboard input to allow only digits between
 * 1 and 6, and standard editing keys (backspace and delete).
 * <p>
 * This class is implemented as an event adapter: it implements the
 * generic {@link EventHandler} contract for {@link KeyEvent} and adapts
 * it to the specific filtering needs of a Sudoku cell. By consuming
 * invalid key events, the field never receives the unwanted character.
 * </p>
 *
 * <p>This adapter is reusable: it can be attached to any text field in
 * the application that requires the same kind of restriction.</p>
 */
public class AdaptadorFocoFiltro implements EventHandler<KeyEvent> {

    /** Minimum digit accepted as input. */
    private static final char DIGITO_MINIMO = '1';

    /** Maximum digit accepted as input. */
    private static final char DIGITO_MAXIMO = '6';

    /**
     * Handles a key typed event by consuming it when the character is
     * not a valid Sudoku digit (1-6) or an empty string (cleared input).
     *
     * @param evento the key event being processed
     */
    @Override
    public void handle(KeyEvent evento) {
        String caracter = evento.getCharacter();

        if (caracter.isEmpty()) {
            return;
        }

        char teclaPresionada = caracter.charAt(0);
        boolean esDigitoValido = (teclaPresionada >= DIGITO_MINIMO
                && teclaPresionada <= DIGITO_MAXIMO);

        if (!esDigitoValido) {
            evento.consume();
        }
    }
}