package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.internal.SlashCommandInvokable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class SubcommandGroup implements SlashCommandInvokable<SubcommandGroupData> {
    private final List<Predicate<SlashCommandInteractionEvent>> predicates = new ArrayList<>();

    private final SubcommandGroupData data;

    public SubcommandGroup(@NotNull SubcommandGroupData data) {
        this.data = data;
    }

    @Override
    public @NotNull SubcommandGroupData getData() {
        return data;
    }

    @Override
    public @NotNull String getName() {
        return getData().getName();
    }

    @Override
    public void addPredicate(@NotNull Predicate<SlashCommandInteractionEvent> predicate) {
        predicates.add(predicate);
    }
}
