package pw.cheesygamer77.cheedautilities.internal;

import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.interactions.commands.build.*;
import pw.cheesygamer77.cheedautilities.commands.SubCommand;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrapper class for all the CommandData objects from JDA
 * This has to exist due to the fact that {@link SubcommandGroupData}
 * doesn't extend {@link BaseCommand} unlike {@link CommandData} and {@link SubcommandData}. Ideally
 * Java would support union types but that's not a thing so we have to do this shit
 */
@Deprecated(since = "1.0.0", forRemoval = true)
public final class SlashCommandDataAdapter {
    private final String name;
    private final String description;
    private final Collection<OptionData> options = new ArrayList<>();
    private final Collection<SubcommandData> subcommands = new ArrayList<>();

    public SlashCommandDataAdapter(@NotNull CommandData data) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.options.addAll(data.getOptions());
        this.subcommands.addAll(data.getSubcommands());
    }

    public SlashCommandDataAdapter(@NotNull SubcommandData data) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.options.addAll(data.getOptions());
    }

    public SlashCommandDataAdapter(@NotNull SubcommandGroupData data) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.subcommands.addAll(data.getSubcommands());
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull Collection<SubcommandData> getSubcommands() { return subcommands; }

    public @NotNull Collection<OptionData> getOptions() {
        return options;
    }

    public @NotNull SlashCommandDataAdapter addSubCommand(@NotNull SubCommand subCommand) {
        subcommands.add(
                new SubcommandData(subCommand.getName(), subCommand.getDescription())
                        .addOptions(subCommand.getData().getOptions())
        );

        return this;
    }
}
