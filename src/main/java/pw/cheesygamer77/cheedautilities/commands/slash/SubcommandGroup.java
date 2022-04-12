package pw.cheesygamer77.cheedautilities.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.slash.internal.SlashCommandInvokable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public abstract class SubcommandGroup implements SlashCommandInvokable<SubcommandGroupData> {
    private final List<Predicate<SlashCommandInteractionEvent>> predicates = new ArrayList<>();
    private final HashMap<String, Subcommand> subcommandMapping = new HashMap<>();  // subcommand name to subcommand instance

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
        this.predicates.add(predicate);
    }

    @Override
    public @NotNull List<Predicate<SlashCommandInteractionEvent>> getPredicates() {
        return predicates;
    }

    protected void addSubcommand(@NotNull Subcommand subcommand) {
        this.subcommandMapping.put(subcommand.getName(), subcommand);
        data.addSubcommands(subcommand.getData());
    }


    @Override
    public void call(@NotNull SlashCommandInteractionEvent event) {
        if(event.getSubcommandName() == null) return;

        Subcommand subcommand = subcommandMapping.get(event.getSubcommandName());
        if(subcommand != null) subcommand.call(event);
    }

    @Override
    public final void invoke(@NotNull SlashCommandInteractionEvent event) {
        throw new IllegalStateException("Subcommand Groups cannot be invoked as commands");
    }
}
