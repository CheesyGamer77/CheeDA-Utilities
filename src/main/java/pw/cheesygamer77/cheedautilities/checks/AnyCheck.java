package pw.cheesygamer77.cheedautilities.checks;

import pw.cheesygamer77.cheedautilities.context.InteractionContext;
import pw.cheesygamer77.cheedautilities.errors.AnyCheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

@SuppressWarnings("unused")
public class AnyCheck extends MultiCheck {
    public AnyCheck(Check... checks) {
        super(false, checks);
    }

    @Override
    public void check(InteractionContext ctx) throws AnyCheckFailure {
        try {
            super.check(ctx);
        } catch (CheckFailure ignored) {
            throw new AnyCheckFailure();
        }
    }
}
