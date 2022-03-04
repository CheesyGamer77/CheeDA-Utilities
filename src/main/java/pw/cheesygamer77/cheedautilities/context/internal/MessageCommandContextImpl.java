package pw.cheesygamer77.cheedautilities.context.internal;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.MessageCommandContext;

public class MessageCommandContextImpl implements MessageCommandContext {
    private final MessageContextInteractionEvent event;

    public MessageCommandContextImpl(@NotNull MessageContextInteractionEvent event) {
        this.event = event;
    }

    @Override
    public @NotNull MessageContextInteractionEvent getEvent() {
        return this.event;
    }

    @Override
    public @NotNull MessageContextInteraction getInteraction() {
        return getEvent().getInteraction();
    }
}
