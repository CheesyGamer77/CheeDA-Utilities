package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.SlashCommand;
import pw.cheesygamer77.cheedautilities.context.CommandContext;
import pw.cheesygamer77.cheedautilities.context.InteractionContext;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;
import pw.cheesygamer77.cheedautilities.context.internal.SlashCommandContextImpl;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.errors.CommandError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class CommandListener extends ListenerAdapter {
    private final HashMap<String, SlashCommand> slashCommands = new HashMap<>();
    private final Set<Check<?>> checks = new HashSet<>();
    private final BiConsumer<InteractionContext<?, ?>, CommandError> onCommandErrorCallback;

    public CommandListener(BiConsumer<InteractionContext<?, ?>, CommandError> onCommandErrorCallback) {
        this.onCommandErrorCallback = onCommandErrorCallback;
    }

    public CommandListener addChecks(Check<?>... checks) {
        this.checks.addAll(Arrays.asList(checks));
        return this;
    }

    public CommandListener addSlashCommand(SlashCommand command) {
        this.slashCommands.put(command.getName(), command);
        return this;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        SlashCommandContext ctx = new SlashCommandContextImpl(event);

        SlashCommand command = slashCommands.get(event.getName());
        if(command == null)
            return;

        try {
            // TODO: fix
            /*
            for (Check<?> check : checks)
                check.check(ctx);
             */
            InvokableCommand<SlashCommandContext> toExecute = command.parse(ctx);
            if(toExecute != null) {
                toExecute.call(ctx);
            }

            throw new CommandNotFound(ctx);
        }
        catch (CommandError error) {
            onCommandErrorCallback.accept(ctx, error);
        }
    }
}
