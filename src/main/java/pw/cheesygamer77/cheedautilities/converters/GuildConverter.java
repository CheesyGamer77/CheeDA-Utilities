package pw.cheesygamer77.cheedautilities.converters;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.GuildConversionFailed;

import java.util.List;

@SuppressWarnings("unused")
public class GuildConverter implements Converter<Guild> {
    /**
     * Converts a given String argument to a valid {@link Guild} using the following lookup strategy:
     * <ol>
     *     <li>Lookup by ID</li>
     *     <li>Lookup by name (there is no disambiguation for Guilds with multiple matching names)</li>
     * </ol>
     *
     * @throws GuildConversionFailed If the conversion failed
     */
    @Override
    public @NotNull Guild convert(@NotNull Context ctx, String argument) throws GuildConversionFailed {
        JDA jda = ctx.getJDA();

        if(SNOWFLAKE_PATTERN.matcher(argument).matches()) {
            Guild guild = jda.getGuildById(argument);
            if(guild != null)
                return guild;
        }
        else {
            List<Guild> guilds = jda.getGuildsByName(argument, false);
            if(guilds.size() != 0)
                return guilds.get(0);
        }

        throw new GuildConversionFailed();
    }
}
