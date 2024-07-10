package rus.iglo.MirseEngine.Engine.Dialog;

public class Rest {
    private final String name;
    private final Runnable action;

    public Rest(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public String getLabel() {
        return name;
    }

    public Runnable getAction() {
        return action;
    }
}
