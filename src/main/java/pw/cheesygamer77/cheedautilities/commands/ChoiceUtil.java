package pw.cheesygamer77.cheedautilities.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ChoiceUtil {
    public static <T extends Enum<T>> List<Command.Choice> fromEnum(@NotNull Class<T> enumType) {
        return Arrays.stream(enumType.getEnumConstants())
                .map(
                        t -> new Command.Choice(StringUtil.toTitleCase(t.name()), t.name())
                )
                .limit(OptionData.MAX_CHOICES)
                .collect(Collectors.toList());
    }
}
