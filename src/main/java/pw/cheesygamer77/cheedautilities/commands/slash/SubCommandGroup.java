package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.commands.InvokableCommand;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.errors.SubCommandNotFound;

import java.util.HashMap;

@SuppressWarnings("unused")
public abstract class SubCommandGroup implements InvokableCommand<SlashCommandContext> {
    private SubcommandGroupData data;

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public SubCommandGroup(SubcommandGroupData data) {
        this.data = data;
    }

    @Override
    public @NotNull SubcommandGroupData getData() {
        return data;
    }

    public SubCommandGroup addSubCommand(SubCommand command) {
        subCommands.put(command.getName(), command);
        data.addSubcommands(command.getData());
        return this;
    }

    @Override
    public @Nullable InvokableCommand<SlashCommandContext> parse(@NotNull SlashCommandContext ctx) throws CommandNotFound {
        String subCommandName = ctx.getSubcommandName();
        SubCommand subCommand = subCommands.get(subCommandName);
        if (subCommand != null)
            return subCommand;

        throw new SubCommandNotFound(ctx);
    }
}
