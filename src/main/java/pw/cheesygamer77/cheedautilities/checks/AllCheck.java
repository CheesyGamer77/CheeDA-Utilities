package pw.cheesygamer77.cheedautilities.checks;

import pw.cheesygamer77.cheedautilities.context.InteractionContext;
import pw.cheesygamer77.cheedautilities.errors.AllCheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

@SuppressWarnings("unused")
public class AllCheck<T extends InteractionContext<?, ?>> extends MultiCheck<T> {
    @SafeVarargs
    public AllCheck(Check<T>... checks) {
        super(true, checks);
    }

    @Override
    public void check(T ctx) throws AllCheckFailure {
        try {
            super.check(ctx);
        } catch (CheckFailure ignored) {
            throw new AllCheckFailure();
        }
    }
}
