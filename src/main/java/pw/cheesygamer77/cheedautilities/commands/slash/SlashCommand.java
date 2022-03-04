package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.commands.InvokableCommand;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandGroupNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandNotFound;

import java.util.HashMap;

public abstract class SlashCommand implements InvokableCommand<SlashCommandContext> {
    private SlashCommandData data;

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private final HashMap<String, SubCommandGroup> subCommandGroups = new HashMap<>();

    public SlashCommand(@NotNull SlashCommandData data) {
        this.data = data;
    }

    @Override
    public Command.@NotNull Type getCommandType() {
        return Command.Type.SLASH;
    }

    public SlashCommand addSubCommand(@NotNull SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
        data.addSubcommands(subCommand.getData());
        return this;
    }

    public SlashCommand addSubCommandGroup(@NotNull SubCommandGroup subCommandGroup) {
        subCommandGroups.put(subCommandGroup.getName(), subCommandGroup);
        data.addSubcommandGroups(subCommandGroup.getData());
        return this;
    }

    @Override
    public @NotNull SlashCommandData getData() {
        return data;
    }

    @Override
    public InvokableCommand<SlashCommandContext> parse(@NotNull SlashCommandContext ctx) throws CommandNotFound {
        // run checks first cause reasons
        try {
            for(Check<SlashCommandContext> check : getChecks()) {
                check.check(ctx);
            }
        }
        catch (CheckFailure ignored) {
            return null;
        }

        String subCommandName = ctx.getSubcommandName();
        String subCommandGroupName = ctx.getSubcommandGroupName();

        // first check if there's no subcommands or subcommand groups, in which
        // case we can just call this command
        if(subCommandName == null && subCommandGroupName == null) {
            return this;
        }
        else {
            // we either have a subcommand, or a subcommand group + subcommand pair

            // do we just have a subcommand?
            if(subCommandGroupName == null) {
                // there is no subcommand group, get the subcommand
                SubCommand subCommand = subCommands.get(subCommandName);
                if (subCommand != null)
                    return subCommand;

                throw new SubCommandNotFound(ctx);
            }
            else {
                SubCommandGroup subCommandGroup = subCommandGroups.get(subCommandGroupName);
                if(subCommandGroup != null) {
                    System.out.println("Parsing subcommand group: " + subCommandGroup.getName());
                    return subCommandGroup.parse(ctx);
                }

                throw new SubCommandGroupNotFound(ctx);
            }
        }
    }
}
