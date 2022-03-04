package pw.cheesygamer77.cheedautilities.checks;

import pw.cheesygamer77.cheedautilities.context.InteractionContext;
import pw.cheesygamer77.cheedautilities.errors.AnyCheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

@SuppressWarnings("unused")
public class AnyCheck<T extends InteractionContext<?, ?>> extends MultiCheck<T> {
    @SafeVarargs
    public AnyCheck(Check<T>... checks) {
        super(false, checks);
    }

    @Override
    public void check(T ctx) throws AnyCheckFailure {
        try {
            super.check(ctx);
        } catch (CheckFailure ignored) {
            throw new AnyCheckFailure();
        }
    }
}
