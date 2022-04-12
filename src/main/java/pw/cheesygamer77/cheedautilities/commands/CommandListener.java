package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.SlashCommand;

import java.util.HashMap;

/**
 * Represents an Event Listener that handles commands.
 *
 * This should be added to your JDA instance as an event listener.
 *
 * This currently only supports {@link SlashCommand}s
 */
public class CommandListener extends ListenerAdapter {
    // TODO: Add support of multiple command types

    private final HashMap<String, SlashCommand> commands = new HashMap<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        SlashCommand command = commands.get(event.getName());
        if(command != null) command.call(event);
    }

    /**
     * Adds a command to this CommandListener
     * @param command The command to add
     * @return The modified CommandListener
     */
    public CommandListener addCommand(@NotNull SlashCommand command) {
        if(command.getType() == Type.SLASH)
            commands.put(command.getName(), command);

        return this;
    }
}
