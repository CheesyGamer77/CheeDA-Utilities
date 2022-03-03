package pw.cheesygamer77.cheedautilities.converters;

import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.Context;
import pw.cheesygamer77.cheedautilities.errors.ContextNotFromGuild;
import pw.cheesygamer77.cheedautilities.errors.ConversionFailed;
import pw.cheesygamer77.cheedautilities.errors.MessageFromDifferentGuild;
import pw.cheesygamer77.cheedautilities.errors.MessageNotFromGuild;

@SuppressWarnings("unused")
public class GuildMessageConverter extends GlobalMessageConverter {
    /**
     * Converts a given String argument to a valid {@link Message} using the following lookup strategy
     *
     * This converter will fail for any of the following reasons:
     * <ul>
     *     <li>The argument could not be converted to any valid message</li>
     *     <li>The invocation context has no associated guild</li>
     *     <li>The resolved message was from a different guild than the invocation context</li>
     * </ul>
     * @throws ContextNotFromGuild if the invocation context does not originate from a guild
     * @throws MessageFromDifferentGuild if the resolved message is from a different guild than the invocation context
     * @throws MessageNotFromGuild if the resolved message is not from a guild
     */
    @Override
    public @NotNull Message convert(@NotNull Context ctx, String argument) throws ConversionFailed, MessageFromDifferentGuild, MessageNotFromGuild, ContextNotFromGuild {
        if(ctx.getGuild() == null)
            throw new ContextNotFromGuild();

        Message message = super.convert(ctx, argument);
        if(message.isFromGuild())
            if(!message.getGuild().equals(ctx.getGuild()))
                return message;
            else
                throw new MessageFromDifferentGuild();
        else
            throw new MessageNotFromGuild();
    }
}
