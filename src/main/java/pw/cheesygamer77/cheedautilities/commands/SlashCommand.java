package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CommandError;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommand extends ListenerAdapter {
    private final String name;
    private final String description;
    private final CommandData commandData;
    private final List<Check> checks;

    public SlashCommand(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
        this.commandData = new CommandData(name, description);
        this.checks = new ArrayList<>();
    }

    public SlashCommand addCheck(Check check) {
        this.checks.add(check);
        return this;
    }

    public SlashCommand addChecks(Check... checks) {
        this.checks.addAll(List.of(checks));
        return this;
    }

    public void onCommandError(SlashCommandContext ctx, CommandError error) {}

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(!event.getName().equals(this.name))
            return;

        // build slash command context
        SlashCommandContext ctx = new SlashCommandContext(event);

        // run checks
        if(!checks.isEmpty()) {
            for(Check check : checks) {
                try {
                    check.check(ctx);
                }
                catch (CheckFailure error) {
                    onCommandError(ctx, error);
                }
            }
        }

        // invoke command
        // TODO: Add argument parsing?
        invoke(event);
    }

    public abstract void invoke(@NotNull SlashCommandEvent event);

    public CommandData getData() {
        return this.commandData;
    }
}
