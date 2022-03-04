package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.components.ActionComponent;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ComponentInteraction;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link InteractionContext} for {@link ComponentInteraction}s
 * @param <T> The {@link ComponentInteraction} type this wraps
 */
public interface ComponentInteractionContext<E extends GenericInteractionCreateEvent, I extends ComponentInteraction> extends InteractionContext<E, I> {
    /**
     * Returns the {@link ActionComponent} contained by this interaction
     * @return The component
     */
    default @NotNull ActionComponent getComponent() {
        return getInteraction().getComponent();
    }

    /**
     * Returns the {@link Component.Type} of the {@link ActionComponent} contained by this interaction context
     * @return The component's type
     */
    default @NotNull Component.Type getComponentType() {
        return getComponent().getType();
    }
}
