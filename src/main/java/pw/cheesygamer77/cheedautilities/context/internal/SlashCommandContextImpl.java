package pw.cheesygamer77.cheedautilities.context.internal;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.SlashCommandContext;

public class SlashCommandContextImpl implements SlashCommandContext {
    private final SlashCommandInteractionEvent event;

    public SlashCommandContextImpl(@NotNull SlashCommandInteractionEvent event) {
        this.event = event;
    }

    @Override
    public @NotNull SlashCommandInteractionEvent getEvent() {
        return event;
    }

    @Override
    public @NotNull SlashCommandInteraction getInteraction() {
        return event.getInteraction();
    }
}
