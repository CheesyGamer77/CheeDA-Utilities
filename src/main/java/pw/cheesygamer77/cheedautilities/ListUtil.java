package pw.cheesygamer77.cheedautilities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ListUtil {
    /**
     * Creates a list of sublists from a particular size
     * @param list The list
     * @param maxChunkSize The maximum number of items in each chunk
     * @param <T> The value type in the List
     * @return The list of chunks from the said chunks
     */
    public static <T> @NotNull List<List<T>> chunks(@NotNull List<T> list, int maxChunkSize) {
        final List<List<T>> out = new ArrayList<>();

        for(int i = 0; i < list.size(); i++)
            out.add(list.subList(i, Math.min(i + maxChunkSize, list.size())));

        return out;
    }
}
