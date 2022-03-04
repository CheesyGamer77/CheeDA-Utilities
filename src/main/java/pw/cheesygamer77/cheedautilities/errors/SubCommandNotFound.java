package pw.cheesygamer77.cheedautilities.errors;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;

public class SubCommandNotFound extends CommandNotFound {
    public SubCommandNotFound(@NotNull SlashCommandContext ctx) {
        super(ctx);
    }
}
