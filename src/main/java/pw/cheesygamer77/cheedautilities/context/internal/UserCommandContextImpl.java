package pw.cheesygamer77.cheedautilities.context.internal;

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.UserCommandContext;

public class UserCommandContextImpl implements UserCommandContext {
    private final UserContextInteractionEvent event;

    public UserCommandContextImpl(@NotNull UserContextInteractionEvent event) {
        this.event = event;
    }


    @Override
    public @NotNull UserContextInteractionEvent getEvent() {
        return this.event;
    }

    @Override
    public @NotNull UserContextInteraction getInteraction() {
        return event.getInteraction();
    }
}
