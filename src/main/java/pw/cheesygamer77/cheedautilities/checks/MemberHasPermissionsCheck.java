package pw.cheesygamer77.cheedautilities.checks;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.Context;

import java.util.Arrays;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class MemberHasPermissionsCheck implements Check {
    private final Permission[] permissions;

    public MemberHasPermissionsCheck(Permission... permissions) {
        this.permissions = permissions;
    }

    @Override
    public @NotNull Predicate<Context> getPredicate() {
        return ctx -> {
            Member member = ctx.getMember();
            if(member != null)
                return ctx.getMember().getPermissions().containsAll(Arrays.asList(permissions));
            return true;
        };
    }
}
