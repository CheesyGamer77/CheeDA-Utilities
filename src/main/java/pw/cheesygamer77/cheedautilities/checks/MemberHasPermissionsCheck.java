package pw.cheesygamer77.cheedautilities.checks;

import net.dv8tion.jda.api.Permission;
import pw.cheesygamer77.cheedautilities.commands.Context;

import java.util.function.Function;

public class MemberHasPermissionsCheck extends PermissionsCheck {
    public MemberHasPermissionsCheck(boolean useGuildPermissions, Permission... permissions) {
        super(ctx -> {
            if (ctx.getGuild() != null) {
                return ctx.getGuild().getSelfMember();
            }
            return ctx.getJDA().getSelfUser();
        }, useGuildPermissions, permissions);
    }

    public MemberHasPermissionsCheck(Permission... permissions) {
        this(false, permissions);
    }
}
