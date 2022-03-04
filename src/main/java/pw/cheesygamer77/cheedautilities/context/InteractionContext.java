package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import org.jetbrains.annotations.NotNull;

/**
 * An {@code InteractionContext} is a utility wrapper around a given {@link Interaction}
 * @param <T> The {@link Interaction} type that this context wraps around
 */
public interface InteractionContext<E extends GenericInteractionCreateEvent, I extends Interaction> {
    /**
     * Returns the {@link GenericInteractionCreateEvent} that this context wraps
     * @return The {@link GenericInteractionCreateEvent} object
     */
    @NotNull E getEvent();

    /**
     * Returns the {@link Interaction} that this context wraps
     * @return The {@link Interaction} object
     */
    @NotNull I getInteraction();

    /**
     * Returns the {@link JDA} provided by the event wrapped by this context
     * @return The {@link JDA} object
     */
    default @NotNull JDA getJDA() {
        return getEvent().getJDA();
    }
}

