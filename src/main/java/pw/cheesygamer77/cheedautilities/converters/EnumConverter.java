package pw.cheesygamer77.cheedautilities.converters;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;

public class EnumConverter<T extends Enum<T>> implements Converter<T> {
    Class<T> klass;

    public EnumConverter(Class<T> klass) {
        this.klass = klass;
    }

    @Override
    public @NotNull T convert(Context ctx, String input) throws ConversionFailed {
        try {
            return T.valueOf(klass, input);
        } catch (IllegalArgumentException err) {
            throw new ConversionFailed();
        }
    }
}
