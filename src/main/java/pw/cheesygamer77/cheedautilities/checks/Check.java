package pw.cheesygamer77.cheedautilities.checks;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

import java.util.function.Predicate;

public interface Check {
    default void check(Context ctx) throws CheckFailure {
        if(!getPredicate().test(ctx))
            throw new CheckFailure();
    }

    @NotNull Predicate<Context> getPredicate();
}
