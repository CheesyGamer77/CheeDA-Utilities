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

    /**
     * Adds a subcommand to this Slash Command
     * @param subcommand The subcommand to add
     * @see SlashCommand#addSubcommandGroup(SubcommandGroup)
     */
    protected void addSubcommand(Subcommand subcommand) {
        subcommandMapping.put(subcommand.getName(), subcommand);
    }

    /**
     * Adds a subcommand group to this Slash Command
     * @param group The subcommand group to add
     * @see SlashCommand#addSubcommand(Subcommand)
     */
    protected void addSubcommandGroup(SubcommandGroup group) {
        subcommandGroupMapping.put(group.getName(), group);
    }

    @Override
    public void call(@NotNull SlashCommandInteractionEvent event) {
        String subcommandName = event.getSubcommandName();
        String subcommandGroupName = event.getSubcommandGroup();

        // check if there's no subcommand/group being called
        if(subcommandGroupName == null && subcommandName == null)
            if(canInvoke(event)) invoke(event);

        // if a subcommand group is specified, call it's parsing
        // otherwise, call the subcommand
        if(subcommandGroupName != null) {
            // TODO: Throw exception for missing subcommand group
            SubcommandGroup group = subcommandGroupMapping.get(subcommandGroupName);
            if(group != null) group.call(event);
        } else {
            // TODO: Throw exception for missing subcommand
            Subcommand subcommand = subcommandMapping.get(subcommandName);
            if(subcommand != null) subcommand.call(event);
        }
    }
}
