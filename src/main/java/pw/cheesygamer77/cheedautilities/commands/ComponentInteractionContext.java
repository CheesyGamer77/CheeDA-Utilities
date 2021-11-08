package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.ComponentInteraction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.converters.Converter;

import java.util.HashMap;

@SuppressWarnings("unused")
public class ComponentInteractionContext implements Context {
    private final ComponentInteraction interaction;

    public ComponentInteractionContext(@NotNull ComponentInteraction interaction) {
        this.interaction = interaction;
    }

    @Override
    public @NotNull JDA getJDA() {
        return interaction.getJDA();
    }

    @Override
    public @Nullable Guild getGuild() {
        return interaction.getGuild();
    }

    @Override
    public @NotNull MessageChannel getChannel() {
        return interaction.getMessageChannel();
    }

    @Override
    public @Nullable Message getMessage() {
        return interaction.getMessage();
    }

    @Override
    public @Nullable Member getAuthorAsMember() {
        return interaction.getMessage().getMember();
    }

    @Override
    public @Nullable User getAuthorAsUser() {
        return interaction.getMessage().getAuthor();
    }

    @Override
    public @Nullable Member getMember() {
        assert getInteraction() != null;
        return getInteraction().getMember();
    }

    @Override
    public @Nullable HashMap<String, String> getArguments() {
        return null;
    }

    @Override
    public @Nullable HashMap<String, Converter<?>> getConverters() {
        return null;
    }

    @Override
    public @Nullable HashMap<String, Object> getConvertedArguments() {
        return null;
    }

    public @Nullable ComponentInteraction getInteraction() {
        return interaction;
    }
}
