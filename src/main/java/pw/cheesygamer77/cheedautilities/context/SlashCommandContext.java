package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a {@link CommandContext} for {@link SlashCommandInteractionEvent}s
 */
public interface SlashCommandContext extends CommandContext<SlashCommandInteractionEvent, SlashCommandInteraction> {
    /**
     * Returns the name of the subcommand group called in this context, if it exists
     * @return The name of the subcommand group called if it exists, {@code null} otherwise
     */
    default @Nullable String getSubcommandGroupName() {
        return getEvent().getSubcommandGroup();
    }

    /**
     * Returns the name of the subcommand called in this context, if it exists
     * @return The name of the subcommand called if it exists, {@code null} otherwise
     */
    default @Nullable String getSubcommandName() {
        return getEvent().getSubcommandName();
    }
}
