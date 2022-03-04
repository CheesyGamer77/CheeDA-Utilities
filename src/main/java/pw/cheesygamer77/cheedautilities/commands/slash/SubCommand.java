package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.commands.InvokableCommand;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;

public abstract class SubCommand implements InvokableCommand<SlashCommandContext> {
    private SubcommandData data;

    public SubCommand(SubcommandData data) {
        this.data = data;
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
    public @NotNull SubcommandData getData() {
        return data;
    }

    // TODO: This should not be here in prod
    @Override
    public String toString() {
        return getName() + ": " + data.getOptions().size() + " options total";
    }

    @Override
    public @Nullable InvokableCommand<SlashCommandContext> parse(@NotNull SlashCommandContext ctx) {
        return this;
    }
}
