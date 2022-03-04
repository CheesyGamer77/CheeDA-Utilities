package pw.cheesygamer77.cheedautilities.context.internal;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.SelectMenuInteractionContext;

public class SelectMenuInteractionContextImpl implements SelectMenuInteractionContext {
    private final SelectMenuInteractionEvent event;

    public SelectMenuInteractionContextImpl(@NotNull SelectMenuInteractionEvent event) {
        this.event = event;
    }

    @Override
    public @NotNull SelectMenuInteractionEvent getEvent() {
        return this.event;
    }

    @Override
    public @NotNull SelectMenuInteraction getInteraction() {
        return getEvent().getInteraction();
    }
}
