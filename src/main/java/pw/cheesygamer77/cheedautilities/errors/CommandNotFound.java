package pw.cheesygamer77.cheedautilities.errors;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

public class CommandNotFound extends CommandError {
    private final SlashCommandEvent event;

    public CommandNotFound(@NotNull SlashCommandEvent event) {
        this.event = event;
    }

    public SlashCommandEvent getEvent() {
        return event;
    }
}
