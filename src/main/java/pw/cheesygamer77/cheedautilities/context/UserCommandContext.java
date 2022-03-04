package pw.cheesygamer77.cheedautilities.context;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a {@link CommandContext} for {@link UserContextInteractionEvent}s
 */
public interface UserCommandContext extends CommandContext<UserContextInteractionEvent, UserContextInteraction> {
    /**
     * Gets the target user as a {@link Member} if the {@link UserContextInteraction} took
     * place in a {@link net.dv8tion.jda.api.entities.Guild}, {@code null} otherwise
     * @return The target member if it exists, {@code null} otherwise
     */
    default @Nullable Member getTargetAsMember() {
        return getEvent().getTargetMember();
    }

    /**
     * Gets the target user as a {@link User} if the {@link UserContextInteraction} took
     * place in a {@link net.dv8tion.jda.api.entities.Guild}, {@code null} otherwise.
     *
     * @return The target user if it exists, {@code null} otherwise
     */
    default @Nullable User getTargetAsUser() {
        Member target = getTargetAsMember();
        return target != null ? target.getUser() : null;
    }
}
