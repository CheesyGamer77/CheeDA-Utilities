package pw.cheesygamer77.cheedautilities.internal;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
