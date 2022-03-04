package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link InteractionContext} associated with a command interaction, such as Slash, Message, and User commands
 * @param <T> The {@link GenericCommandInteractionEvent} wrapped by this context
 */
public interface CommandContext<E extends GenericCommandInteractionEvent, I extends CommandInteraction> extends InteractionContext<E, I> {
    /**
     * Returns the command name given by the given {@link GenericCommandInteractionEvent}
     * @return The command name
     */
    default @NotNull String getCommandName() {
        return getEvent().getName();
    }

    /**
     * Returns the command id given by the given {@link GenericCommandInteractionEvent}
     * @return The command's ID
     */
    default long getCommandId() {
        return getEvent().getCommandIdLong();
    }
}
