package validation;

import error.exceptions.IllegalHotkeyValueException;
import error.exceptions.InvalidDimensionException;

public class Verifier {
    private static final Verifier INSTANCE = new Verifier();

    private Verifier() {}

    public static Verifier getInstance() {
        return INSTANCE;
    }

    public void validateDimensions(int width, int height, int mines) {
        if (width < 5 || width > 30) {
            throw new InvalidDimensionException("Invalid width parameter: " + width);
        }
        if (height < 5 || height > 24) {
            throw new InvalidDimensionException("Invalid height parameter: " + height);
        }
        var maxMines = width * height;
        if (mines >= maxMines || mines < 1) {
            throw new InvalidDimensionException("Invalid number of miens: " + mines);
        }
    }

    public void validateHotkeys(String... hotkeys) {
        for (int i = 0; i < hotkeys.length; ++i) {
            if (hotkeys[i].length() != 1) {
                throw new IllegalHotkeyValueException("Hotkey " + hotkeys[i] + " not a " +
                        "character of length 1");
            }
            for (int j = i + 1; j < hotkeys.length; j++) {
                if (hotkeys[i].equals(hotkeys[j])) {
                    throw new IllegalHotkeyValueException("Two hotkeys cannot have the same value.");
                }
            }
        }
    }
}
