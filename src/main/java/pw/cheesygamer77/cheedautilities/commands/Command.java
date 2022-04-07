package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command.Type;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for all given command
 * @param <D> The command data type
 * @param <E> The invoking event type
 */
public interface Command<D extends CommandData, E extends Event> {
    /**
     * Returns the name of this command
     * @return The command name
     */
    @NotNull String getName();

    /**
     * Returns the type of this command
     * @return The command type
     */
    @NotNull Type getType();

    /**
     * Invokes the execution of this command
     * @param event The event that invoked this command
     */
    void invoke(@NotNull E event);

    /**
     * Adds a predicate to this command
     * @param predicate The predicate
     * @return The current command
     */
    @NotNull Command<D, E> addPredicate(@NotNull Predicate<SlashCommandInteractionEvent> predicate);

    /**
     * Returns this command's data
     * @return The command data
     */
    @NotNull D getData();

    /**
     * Returns a list of all the required predicates that must pass before invoking this command
     * @return The list of required predicates
     */
    default @NotNull List<Predicate<E>> getPredicates() {
        return List.of();
    }

    /**
     * Calls this command
     *
     * This method runs any predicates and invokes the command if all predicates pass
     * @param event The event that called this command
     */
    default void call(@NotNull E event) {
        for(Predicate<E> predicate : getPredicates())
            // TODO: Allow for custom predicate failure handling
            if(!predicate.test(event))
                return;

        invoke(event);
    }
}
