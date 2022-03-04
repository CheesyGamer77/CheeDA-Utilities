package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.data.SerializableData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import pw.cheesygamer77.cheedautilities.context.CommandContext;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.errors.CommandError;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;

import java.util.List;

/**
 * Represents an executable command in Discord, including Slash, Message, and User Context commands
 * @param <T> The type of {@link CommandContext} given on invocation
 */
public interface InvokableCommand<T extends CommandContext<?, ?>> {
    /**
     * Returns the data for this command
     * @return The command data
     */
    @NotNull SerializableData getData();

    /**
     * Returns the name of this command
     * @return The command name
     */
    @NotNull String getName();

    /**
     * Returns the description of this command
     * @return The command description
     */
    @NotNull String getDescription();

    /**
     * Returns the {@link Command.Type} for this command
     * @return The command's type
     */
    @NotNull Command.Type getCommandType();

    default void onCommandError(@NotNull T ctx, @NotNull CommandError error) {
        LoggerFactory.getLogger(InvokableCommand.class)
                .error(
                        "Encountered an error while parsing \"{}\": {}",
                        ctx.getEvent().getCommandString(),
                        error.getMessage()
                );
    }

    default List<Check<T>> getChecks() {
        return List.of();
    }

    /**
     *
     * @param ctx
     * @return
     * @throws CommandNotFound
     */
    InvokableCommand<T> parse(@NotNull T ctx) throws CommandNotFound;

    /**
     * Invokes this command given a particular {@link CommandContext}.
     * DANGER: This bypasses any {@link Check}s defined for this command! To invoke with permission checking,
     * use {@link InvokableCommand#call(CommandContext)} instead
     * @param ctx The context to invoke the command with
     */
    void invoke(@NotNull T ctx);

    default void call(@NotNull T ctx) {
        try {
            for(Check<T> check : getChecks())
                check.check(ctx);

            invoke(ctx);
        }
        catch (CommandError error) {
            onCommandError(ctx, error);
        }
    }
}
