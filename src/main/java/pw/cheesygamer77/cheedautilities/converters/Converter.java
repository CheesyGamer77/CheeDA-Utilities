package pw.cheesygamer77.cheedautilities.converters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;

public interface Converter<T> {
    @NotNull T convert(Context ctx, String argument) throws ConversionFailed;

    default @Nullable T safeConvert(Context ctx, String argument) {
        try {
            return convert(ctx, argument);
        } catch (ConversionFailed error) {
            return null;
        }
    }
}
