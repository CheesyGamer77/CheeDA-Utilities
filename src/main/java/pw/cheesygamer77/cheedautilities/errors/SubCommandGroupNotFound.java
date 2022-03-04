package pw.cheesygamer77.cheedautilities.errors;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;

public class SubCommandGroupNotFound extends CommandNotFound {
    public SubCommandGroupNotFound(@NotNull SlashCommandContext ctx) {
        super(ctx);
    }
}
