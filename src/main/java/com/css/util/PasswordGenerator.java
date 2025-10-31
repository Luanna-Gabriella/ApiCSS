package com.css.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        List<Character> chars = new ArrayList<>();

        // Garante pelo menos um de cada tipo
        chars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        chars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        chars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // Preenche o restante (6 - 3 = 3 caracteres)
        String allChars = UPPERCASE + LOWERCASE + DIGITS;
        for (int i = 3; i < 6; i++) {
            chars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Embaralha para não manter ordem previsível
        Collections.shuffle(chars, random);

        // Monta string final
        StringBuilder password = new StringBuilder();
        for (char c : chars) {
            password.append(c);
        }

        return password.toString();
    }
}
