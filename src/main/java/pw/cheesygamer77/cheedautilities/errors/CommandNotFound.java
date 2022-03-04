package pw.cheesygamer77.cheedautilities.errors;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.CommandContext;

public class CommandNotFound extends CommandError {
    private final CommandContext<?, ?> ctx;

    public CommandNotFound(@NotNull CommandContext<?, ?> ctx) {
        this.ctx = ctx;
    }

    public CommandContext<?, ?> getContext() {
        return ctx;
    }
}
