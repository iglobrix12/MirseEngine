package rus.iglo.MirseEngine.Engine;

@FunctionalInterface
public interface Action extends Runnable {
    public abstract void execute();

    @Override
    default void run() {

    }
}
