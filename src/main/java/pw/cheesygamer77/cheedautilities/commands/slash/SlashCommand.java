package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.Command.Type;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class SlashCommand implements Command<SlashCommandData, SlashCommandInteractionEvent> {
    private final List<Predicate<SlashCommandInteractionEvent>> predicates = new ArrayList<>();
    private final SlashCommandData data;

    public SlashCommand(@NotNull SlashCommandData data) {
        this.data = data;
    }

    public SlashCommand(@NotNull SlashCommandData data, Predicate<SlashCommandInteractionEvent> predicate) {
        this(data);
        addPredicate(predicate);
    }

    @Override
    public @NotNull Type getType() {
        return Type.SLASH;
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
    public @NotNull Command<SlashCommandData, SlashCommandInteractionEvent> addPredicate(@NotNull Predicate<SlashCommandInteractionEvent> predicate) {
        this.predicates.add(predicate);
        return this;
    }
}
