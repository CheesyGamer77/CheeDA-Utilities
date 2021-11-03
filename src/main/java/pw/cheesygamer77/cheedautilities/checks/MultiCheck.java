package pw.cheesygamer77.cheedautilities.checks;

import org.jetbrains.annotations.NotNull;
import pw.cheesygamer77.cheedautilities.commands.Context;

import java.util.function.Predicate;

public class MultiCheck implements Check {
    protected final Check[] checks;
    private final boolean checkAll;

    public MultiCheck(boolean checkAll, Check... checks) {
        this.checkAll = checkAll;
        this.checks = checks;
    }

    @Override
    public @NotNull Predicate<Context> getPredicate() {
        return ctx -> {
            for(Check check : checks) {
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
