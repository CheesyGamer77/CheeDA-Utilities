package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandNotFound;
import pw.cheesygamer77.cheedautilities.internal.ISlashCommand;
import pw.cheesygamer77.cheedautilities.internal.SlashCommandDataAdapter;

import java.util.HashMap;

@SuppressWarnings("unused")
public abstract class SubCommandGroup implements ISlashCommand<SlashCommandDataAdapter> {
    private final SlashCommandDataAdapter data;

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public SubCommandGroup(SubcommandGroupData data) {
        this.data = new SlashCommandDataAdapter(data);
    }

    @Override
    public SlashCommandDataAdapter getData() {
        return data;
    }

    public SubCommandGroup addSubCommand(SubCommand command) {
        subCommands.put(command.getName(), command);
        data.addSubCommand(command);
        return this;
    }

    @Override
    public @Nullable ISlashCommand<?> parse(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) throws CommandNotFound {
        String subCommandName = event.getSubcommandName();
        SubCommand subCommand = subCommands.get(subCommandName);
        if (subCommand != null)
            return subCommand;

        throw new SubCommandNotFound(event);
    }
}
