package pw.cheesygamer77.cheedautilities.checks;

import pw.cheesygamer77.cheedautilities.commands.Context;
import pw.cheesygamer77.cheedautilities.errors.AnyCheckFailure;
import pw.cheesygamer77.cheedautilities.errors.CheckFailure;

@SuppressWarnings("unused")
public class AnyCheck extends MultiCheck {
    public AnyCheck(Check... checks) {
        super(false, checks);
    }

    @Override
    public void check(Context ctx) throws AnyCheckFailure {
        try {
            super.check(ctx);
        } catch (CheckFailure ignored) {
            throw new AnyCheckFailure();
        }
    }
}
