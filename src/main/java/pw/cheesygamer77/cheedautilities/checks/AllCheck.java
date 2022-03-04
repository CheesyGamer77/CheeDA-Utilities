package pw.cheesygamer77.cheedautilities.checks;

import pw.cheesygamer77.cheedautilities.context.Context;
import pw.cheesygamer77.cheedautilities.errors.AllCheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

@SuppressWarnings("unused")
public class AllCheck extends MultiCheck {
    public AllCheck(Check... checks) {
        super(true, checks);
    }

    @Override
    public void check(Context ctx) throws AllCheckFailure {
        try {
            super.check(ctx);
        } catch (CheckFailure ignored) {
            throw new AllCheckFailure();
        }
    }
}
