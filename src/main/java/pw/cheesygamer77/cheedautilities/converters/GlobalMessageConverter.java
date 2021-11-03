package pw.cheesygamer77.cheedautilities.converters;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class GlobalMessageConverter extends SnowflakeConverter implements Converter<Message> {
    public static final Pattern SNOWFLAKE_PAIR_PATTERN = Pattern.compile(SNOWFLAKE_REGEX + "-" + SNOWFLAKE_REGEX);

    /**
     * Converts a given String argument to a valid {@link Message} using the following lookup strategy:
     * <ol>
     *     <li>Lookup by "{channel ID}-{message ID}" (retrieved by shift-clicking on "Copy ID")</li>
     *     <li>Lookup by message ID (the message **must** be in the context channel)</li>
     *     <li>Lookup by message URL</li>
     * </ol>
     *
     * @throws ConversionFailed If the conversion failed
     */
    @Override
    public @NotNull Message convert(Context ctx, String argument) throws ConversionFailed {
        if(SNOWFLAKE_PAIR_PATTERN.matcher(argument).matches()) {
            System.out.println("Matches");

            // separate channel/message IDs
            String[] ids = argument.split("-");
            TextChannel channel = ctx.getJDA().getTextChannelById(ids[0]);
            if(channel != null) {
                try {
                    return channel.retrieveMessageById(ids[1]).submit().get();
                }
                catch (InterruptedException | ExecutionException ignored) {}
            }

            throw new ConversionFailed();
        }

        throw new ConversionFailed();
    }
}
