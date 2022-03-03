package pw.cheesygamer77.cheedautilities.internal;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;
import pw.cheesygamer77.cheedautilities.errors.CommandError;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;

import java.util.List;

public interface ISlashCommand<T extends SlashCommandDataAdapter> {
    default @NotNull String getName() {
        return getData().getName();
    }

    default @NotNull String getDescription() {
        return getData().getDescription();
    }

    T getData();

    default void onCommandError(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx, @NotNull CommandError error) {
        LoggerFactory.getLogger(ISlashCommand.class)
                .error(
                        "Encountered an error while parsing \"{}\": {}",
                        event.getCommandString(),
                        error.getMessage()
                );
    }

    default List<Check> getChecks() {
        return List.of();
    }

    /**
     * Returns the {@link ISlashCommand} derived from a particular {@link SlashCommandEvent}
     *
     * @param event The Slash Command Event
     * @return The command
     */
    @Nullable ISlashCommand<?> parse(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) throws CommandNotFound;

    default void call(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) {
        try {
            for(Check check : getChecks())
                check.check(ctx);

            invoke(event, ctx);
        }
        catch (CommandError error) {
            onCommandError(event, ctx, error);
        }
    }

    void invoke(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) throws CommandError;
}
