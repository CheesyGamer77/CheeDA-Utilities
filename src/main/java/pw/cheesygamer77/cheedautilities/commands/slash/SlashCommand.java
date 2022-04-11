package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.internal.SlashCommandInvokable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public abstract class SlashCommand implements SlashCommandInvokable<SlashCommandData> {
    private final List<Predicate<SlashCommandInteractionEvent>> predicates = new ArrayList<>();
    private final HashMap<String, Subcommand> subcommandMapping = new HashMap<>();  // subcommand name to subcommand instance
    private final HashMap<String, SubcommandGroup> subcommandGroupMapping = new HashMap<>();  // group name to group instance

    private final SlashCommandData data;

    public SlashCommand(@NotNull SlashCommandData data) {
        this.data = data;
    }

    public SlashCommand(@NotNull SlashCommandData data, Predicate<SlashCommandInteractionEvent> predicate) {
        this(data);
        addPredicate(predicate);
    }

    @Override
    public @NotNull SlashCommandData getData() {
        return this.data;
    }

    @Override
    public @NotNull String getName() {
        return getData().getName();
    }

    @Override
    public @NotNull List<Predicate<SlashCommandInteractionEvent>> getPredicates() {
        return predicates;
    }

    @Override
    public void addPredicate(@NotNull Predicate<SlashCommandInteractionEvent> predicate) {
        this.predicates.add(predicate);
    }
}
