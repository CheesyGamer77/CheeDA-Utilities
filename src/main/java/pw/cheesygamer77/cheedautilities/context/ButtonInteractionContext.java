package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ComponentInteractionContext} in relation to a {@link Button} being clicked
 */
public interface ButtonInteractionContext extends ComponentInteractionContext<ButtonInteractionEvent, ButtonInteraction> {
    /**
     * Returns the {@link ButtonStyle} of the {@link Button} clicked
     * @return The style
     */
    default @NotNull ButtonStyle getButtonStyle() {
        return getEvent().getButton().getStyle();
    }

    /**
     * Returns whether the given button has a component ID or not
     * More simply put, this returns whether this button isn't a {@link ButtonStyle#LINK} button
     * @return Whether the button has an ID
     */
    default boolean hasId() {
        switch (getButtonStyle()) {
            case LINK:
            case UNKNOWN:
                return false;
            default:
                return true;
        }
    }
}
