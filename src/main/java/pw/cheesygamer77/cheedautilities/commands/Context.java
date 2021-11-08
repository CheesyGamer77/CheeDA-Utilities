package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.converters.Converter;

import java.util.HashMap;

public interface Context {
    @NotNull JDA getJDA();

    @Nullable Guild getGuild();

    @NotNull MessageChannel getChannel();

    @Nullable Message getMessage();

    @Nullable Member getAuthorAsMember();

    @Nullable User getAuthorAsUser();

    @Nullable Member getMember();

    @Nullable HashMap<String, String> getArguments();

    @Nullable HashMap<String, Converter<?>> getConverters();

    @Nullable HashMap<String, Object> getConvertedArguments();
}

