package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.checks.Check;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandGroupNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandNotFound;
import pw.cheesygamer77.cheedautilities.internal.ISlashCommand;
import pw.cheesygamer77.cheedautilities.internal.SlashCommandDataAdapter;

import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class SlashCommand implements ISlashCommand<SlashCommandDataAdapter> {
    private final SlashCommandDataAdapter data;

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private final HashMap<String, SubCommandGroup> subCommandGroups = new HashMap<>();

    public SlashCommand(@NotNull CommandData data) {
        this.data = new SlashCommandDataAdapter(data);
    }

    public SlashCommand addSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
        return this;
    }

    public SlashCommand addSubCommandGroup(SubCommandGroup subCommandGroup) {
        subCommandGroups.put(subCommandGroup.getName(), subCommandGroup);
        return this;
    }

    public CommandCreateAction upsertCommand(@NotNull Guild guild) {
        CommandData data = new CommandData(getName(), getDescription());

        if(! getData().getOptions().isEmpty())
            data.addOptions(getData().getOptions());

        data.addSubcommands(
                subCommands.values().stream()
                        .map(c -> {
                            SubcommandData s = new SubcommandData(c.getName(), c.getDescription());

                            System.out.println(c);

                            if(!c.getData().getOptions().isEmpty())
                                s.addOptions(c.getData().getOptions());

                            return s;
                        })
                        .collect(Collectors.toList())
        );

        data.addSubcommandGroups(
                subCommandGroups.values().stream()
                        .map(
                                g -> {
                                    SubcommandGroupData group = new SubcommandGroupData(g.getName(), g.getDescription());

                                    System.out.println("Saw " + g.getData().getSubcommands().size() + " sub commands when upserting command");

                                    group.addSubcommands(g.getData().getSubcommands());

                                    return group;
                                }
                        )
                        .collect(Collectors.toList())
        );

        System.out.println(data.getSubcommandGroups().size());

        return guild.upsertCommand(data);
    }

    @Override
    public SlashCommandDataAdapter getData() {
        return data;
    }

    @Override
    public ISlashCommand<?> parse(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) throws CommandNotFound {
        // run checks first cause reasons
        try {
            for(Check check : getChecks()) {
                check.check(ctx);
            }
        }
        catch (CheckFailure ignored) {
            return null;
        }

        String subCommandName = event.getSubcommandName();
        String subCommandGroupName = event.getSubcommandGroup();

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

                throw new SubCommandNotFound(event);
            }
            else {
                SubCommandGroup subCommandGroup = subCommandGroups.get(subCommandGroupName);
                if(subCommandGroup != null) {
                    System.out.println("Parsing subcommand group: " + subCommandGroup.getName());
                    return subCommandGroup.parse(event, ctx);
                }

                throw new SubCommandGroupNotFound(event);
            }
        }
    }
}
