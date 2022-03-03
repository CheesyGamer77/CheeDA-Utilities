package pw.cheesygamer77.cheedautilities.checks;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.Context;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

import java.util.function.Predicate;

public interface Check<T extends Context> {
    default void check(T ctx) throws CheckFailure {
        if(!getPredicate().test(ctx))
            throw new CheckFailure();
    }

    @NotNull Predicate<T> getPredicate();
}
