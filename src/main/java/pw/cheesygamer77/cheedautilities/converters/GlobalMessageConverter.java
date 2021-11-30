package pw.cheesygamer77.cheedautilities.converters;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class GlobalMessageConverter implements Converter<Message> {
    public static class MinimalMessageData {
        private final @NotNull Long id;
        private final @NotNull Long channelId;
        private final @NotNull Long guildId;

        public MinimalMessageData(String messageUrl) {
            String[] data = (String[]) Arrays.stream(messageUrl.replace("https://", "").split("/")).skip(2).toArray();

            guildId = Long.parseLong(data[0]);
            channelId = Long.parseLong(data[1]);
            id = Long.parseLong(data[2]);
        }

        public long getID() {
            return id;
        }

        public long getChannelID() {
            return channelId;
        }

        public long getGuildID() {
            return guildId;
        }
    }

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
            // separate channel/message IDs
            String[] ids = argument.split("-");
            TextChannel channel = ctx.getJDA().getTextChannelById(ids[0]);
            if(channel != null)
                try {
                    return channel.retrieveMessageById(ids[1]).submit().get();
                }
                catch (InterruptedException | ExecutionException ignored) {}
        }
        else if(SNOWFLAKE_PATTERN.matcher(argument).matches())
            try {
                return ctx.getChannel().retrieveMessageById(argument).submit().get();
            }
            catch (InterruptedException | ExecutionException ignored) {}
        else if(Message.JUMP_URL_PATTERN.matcher(argument).matches()) {
            MinimalMessageData data = new MinimalMessageData(argument);
            TextChannel channel = ctx.getJDA().getTextChannelById(data.getChannelID());
            if(channel != null) {
                try {
                    return channel.retrieveMessageById(data.getID()).submit().get();
                }
                catch (InterruptedException | ExecutionException ignored) {}
            }
        }

        throw new ConversionFailed();
    }
}
