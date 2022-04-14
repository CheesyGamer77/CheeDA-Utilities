package pw.cheesygamer77.cheedautilities;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class StringUtil {
    /**
     * Returns the "title-case" version of the given input string
     *
     * Note that underscores will be replaced with spaces
     * @param value The string to convert
     * @return The title-case version of the input string
     */
    public static @NotNull String toTitleCase(@NotNull String value) {
        if(value.isEmpty()) throw new IllegalArgumentException("String value cannot be empty");

        String[] words = value.toLowerCase(Locale.ROOT).replace("_", " ").split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            word = word.strip();

            System.out.println("\tCurrent Word: " + word);
            if (word.length() == 1)
                word = word.toUpperCase(Locale.ROOT);
            else
                word = word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);

            builder.append(word).append(" ");
        }

        return builder.toString().strip();
    }
}
