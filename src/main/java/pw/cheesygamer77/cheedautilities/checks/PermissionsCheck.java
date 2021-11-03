package pw.cheesygamer77.cheedautilities.checks;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class PermissionsCheck implements Check {
    protected final List<Permission> permissions;
    protected final Function<Context, Object> targetCallback;
    boolean useGuildPermissions;

    public PermissionsCheck(
            Function<Context, Object> targetCallback, boolean useGuildPermissions, Permission... permissions) {
        this.targetCallback = targetCallback;
        this.useGuildPermissions = useGuildPermissions;
        this.permissions = Arrays.asList(permissions);
    }

    @Override
    public @NotNull Predicate<Context> getPredicate() {
        return ctx -> {
            Object target = targetCallback.apply(ctx);
            if(target instanceof Member) {
                if(useGuildPermissions) {
                    return ((Member) target).hasPermission(permissions);
                }
                return ((Member) target).hasPermission((GuildChannel) ctx.getChannel(), permissions);
            }
            return true;
        };
    }
}
