package pw.cheesygamer77.cheedautilities.commands.slash.internal;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.data.SerializableData;
import pw.cheesygamer77.cheedautilities.commands.Command;

public interface SlashCommandInvokable<D extends SerializableData> extends Command<D, SlashCommandInteractionEvent> {

}
