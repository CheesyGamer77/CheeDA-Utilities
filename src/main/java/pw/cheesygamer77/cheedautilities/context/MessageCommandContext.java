package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an {@link CommandContext} for {@link MessageContextInteractionEvent}s
 */
public interface MessageCommandContext extends CommandContext<MessageContextInteractionEvent, MessageContextInteraction> {
    /**
     * Returns the {@link MessageChannel} where this message command was called in.
     * As of JDA v5.0.0-alpha.9, this is never null, but the docs also say that it might become null
     * in the future. Whether this will indeed happen remains to be seen :shrug:
     * @return The {@link MessageChannel} this command was called in
     * @see <a href="https://javadoc.io/static/net.dv8tion/JDA/5.0.0-alpha.9/net/dv8tion/jda/api/events/interaction/command/MessageContextInteractionEvent.html#getChannel()">JDA Docs on MessageChannel nullability</a>
     */
    default @Nullable MessageChannel getChannel() {
        return getEvent().getChannel();
    }
}
