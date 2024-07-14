package rus.iglo.MirseEngine.Engine.Dialog;

import rus.iglo.MirseEngine.Engine.Action;

public class Rest {
    private final String label;
    private final Action action;

    public Rest(String label, Action action) {
        this.label = label;
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public Action getAction() {
        return action;
    }
}
