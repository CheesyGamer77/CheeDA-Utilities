package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.InteractionContext;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;
import pw.cheesygamer77.cheedautilities.context.internal.SlashCommandContextImpl;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.errors.CommandError;
import pw.cheesygamer77.cheedautilities.internal.ISlashCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class CommandListener extends ListenerAdapter {
    private final HashMap<String, SlashCommand> slashCommands = new HashMap<>();
    private final Set<Check> checks = new HashSet<>();
    private final BiConsumer<InteractionContext, CommandError> onCommandErrorCallback;

    public CommandListener(BiConsumer<InteractionContext, CommandError> onCommandErrorCallback) {
        this.onCommandErrorCallback = onCommandErrorCallback;
    }

    public CommandListener addChecks(Check... checks) {
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
            for (Check check : checks)
                check.check(ctx);

            ISlashCommand<?> toExecute = command.parse(event, ctx);
            if(toExecute != null) {
                toExecute.call(event, ctx);
            }

            throw new CommandNotFound(event);
        }
        catch (CommandError error) {
            onCommandErrorCallback.accept(ctx, error);
        }
    }
}
