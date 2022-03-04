package pw.cheesygamer77.cheedautilities.checks;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.InteractionContext;

import java.util.Arrays;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class MemberHasPermissionsCheck<T extends InteractionContext<?, ?>> implements Check<T> {
    private final Permission[] permissions;

    public MemberHasPermissionsCheck(Permission... permissions) {
        this.permissions = permissions;
    }

    @Override
    public @NotNull Predicate<T> getPredicate() {
        return ctx -> {
            Member member = ctx.getEvent().getMember();
            if(member != null)
                return member.getPermissions().containsAll(Arrays.asList(permissions));
            return true;
        };
    }
}
