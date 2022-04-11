package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.internal.SlashCommandInvokable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Subcommand implements SlashCommandInvokable<SubcommandData> {
    private final List<Predicate<SlashCommandInteractionEvent>> predicates = new ArrayList<>();

    private final SubcommandData data;

    public Subcommand(@NotNull SubcommandData data) {
        this.data = data;
    }

    @Override
    public @NotNull SubcommandData getData() {
        return this.data;
    }

    @Override
    public @NotNull String getName() {
        return getData().getName();
    }

    @Override
    public void addPredicate(@NotNull Predicate<SlashCommandInteractionEvent> predicate) {
        this.predicates.add(predicate);
    }

    @Override
    public @NotNull List<Predicate<SlashCommandInteractionEvent>> getPredicates() {
        return predicates;
    }
}
