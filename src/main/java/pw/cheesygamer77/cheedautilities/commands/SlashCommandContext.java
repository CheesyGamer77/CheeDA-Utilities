package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.converters.Converter;

import java.util.HashMap;
import java.util.List;

public class SlashCommandContext implements Context {
    private final SlashCommandEvent event;
    private final HashMap<String, Converter<?>> converters;

    public SlashCommandContext(@NotNull SlashCommandEvent event) {
        this.event = event;
        converters = new HashMap<>();
    }

    public SlashCommandContext setConverter(String argumentName, Converter<?> converter) {
        converters.put(argumentName, converter);
        return this;
    }

    public SlashCommandContext removeConverter(String argumentName) {
        converters.remove(argumentName);
        return this;
    }

    @Override
    public @NotNull JDA getJDA() {
        return event.getJDA();
    }

    @Override
    public @Nullable Guild getGuild() {
        return event.getGuild();
    }

    @Override
    public @NotNull MessageChannel getChannel() {
        return event.getMessageChannel();
    }

    @Override
    public @Nullable Message getMessage() {
        return null;
    }

    @Override
    public @Nullable Member getAuthorAsMember() {
        return null;
    }

    @Override
    public @Nullable User getAuthorAsUser() {
        return null;
    }

    @Override
    public @Nullable HashMap<String, String> getArguments() {
        List<OptionMapping> options = event.getOptions();
        if(options.isEmpty())
            return null;

        HashMap<String, String> map = new HashMap<>();

        for(OptionMapping optionMapping : event.getOptions())
            map.put(optionMapping.getName(), optionMapping.getAsString());

        return map;
    }

    @Override
    public @Nullable HashMap<String, Converter<?>> getConverters() {
        return converters;
    }

    @Override
    public @Nullable HashMap<String, Object> getConvertedArguments() {
        HashMap<String, String> rawArguments = getArguments();
        if(rawArguments == null)
            return null;

        HashMap<String, Object> out = new HashMap<>();
        for(String key : rawArguments.keySet())
            if(converters.containsKey(key))
                out.put(key, converters.get(key));

        return out;
    }
}
