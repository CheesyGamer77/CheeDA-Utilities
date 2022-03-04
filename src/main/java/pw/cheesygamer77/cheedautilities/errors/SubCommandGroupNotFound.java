package pw.cheesygamer77.cheedautilities.errors;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;

public class SubCommandGroupNotFound extends CommandNotFound {
    public SubCommandGroupNotFound(@NotNull SlashCommandEvent event) {
        super(event);
    }
}
