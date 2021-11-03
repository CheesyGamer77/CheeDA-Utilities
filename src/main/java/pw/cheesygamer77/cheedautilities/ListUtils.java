package pw.cheesygamer77.cheedautilities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    /**
     * Converts a list into a 2d list of lists of a particular size
     * @param list The list to chunkify
     * @param maxItemsPerChunk The maximum number of items per chunk
     * @return A two dimensional list of lists of a particular size
     */
    public static @NotNull <T> List<List<T>> chunkify(@NotNull List<T> list, int maxItemsPerChunk) {
        final List<List<T>> out = new ArrayList<>();

        for (int i = 0; i < list.size(); i += maxItemsPerChunk) {
            out.add(list.subList(i,
                    Math.min(i + maxItemsPerChunk, list.size())));
        }

        return out;
    }
}
