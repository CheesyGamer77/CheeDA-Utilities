package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.errors.CommandNotFound;
import pw.cheesygamer77.cheedautilities.internal.ISlashCommand;
import pw.cheesygamer77.cheedautilities.internal.SlashCommandDataAdapter;

public abstract class SubCommand implements ISlashCommand<SlashCommandDataAdapter> {
    private final SlashCommandDataAdapter data;

    public SubCommand(SubcommandData data) {
        this.data = new SlashCommandDataAdapter(data);
    }

    @Override
    public @NotNull String getName() {
        return data.getName();
    }

    @Override
    public @NotNull String getDescription() {
        return data.getDescription();
    }

    @Override
    public SlashCommandDataAdapter getData() {
        return data;
    }

    @Override
    public String toString() {
        return getName() + ": " + data.getOptions().size() + " options total";
    }

    @Override
    public @Nullable ISlashCommand<?> parse(@NotNull SlashCommandEvent event, @NotNull SlashCommandContext ctx) throws CommandNotFound {
        return this;
    }
}
