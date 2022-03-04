package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a {@link ComponentInteractionContext} relating to {@link SelectMenu} clicks
 */
public interface SelectMenuInteractionContext extends ComponentInteractionContext<SelectMenuInteractionEvent, SelectMenuInteraction> {
    /**
     * Returns the {@link List} of values corresponding to each selected choice in the menu
     * @return A non-null, possibly empty {@link List} of each selected value
     */
    default @NotNull List<String> getSelectedValues() {
        return getEvent().getValues();
    }
}
