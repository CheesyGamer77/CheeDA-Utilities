package pw.cheesygamer77.cheedautilities.converters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.context.Context;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;

import java.util.regex.Pattern;

public interface Converter<T> {
    String SNOWFLAKE_REGEX = "([0-9]{15,20})";
    Pattern SNOWFLAKE_PAIR_PATTERN = Pattern.compile(SNOWFLAKE_REGEX + "-" + SNOWFLAKE_REGEX);
    Pattern SNOWFLAKE_PATTERN = Pattern.compile(SNOWFLAKE_REGEX + "$");

    @NotNull T convert(Context ctx, String argument) throws ConversionFailed;

    @SuppressWarnings("unused")
    default @Nullable T safeConvert(Context ctx, String argument) {
        try {
            return convert(ctx, argument);
        } catch (ConversionFailed error) {
            return null;
        }
    }
}
