package pw.cheesygamer77.cheedautilities.checks;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.context.InteractionContext;

import java.util.function.Predicate;

public class MultiCheck<T extends InteractionContext<?, ?>> implements Check<T> {
    protected final Check<T>[] checks;
    private final boolean checkAll;

    @SafeVarargs
    public MultiCheck(boolean checkAll, Check<T>... checks) {
        this.checkAll = checkAll;
        this.checks = checks;
    }

    @Override
    public @NotNull Predicate<T> getPredicate() {
        return ctx -> {
            for(Check<T> check : checks) {
                if(checkAll && !check.getPredicate().test(ctx))
                    return false;
                else if(!checkAll && check.getPredicate().test(ctx)) {
                    return true;
                }
            }

            return checkAll;
        };
    }
}
