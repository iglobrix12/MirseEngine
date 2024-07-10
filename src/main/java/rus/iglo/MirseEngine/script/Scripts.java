package rus.iglo.MirseEngine.script;

import rus.iglo.MirseEngine.Engine.Dialog.DialogGUI;
import rus.iglo.MirseEngine.Engine.Dialog.Rest;
import rus.iglo.MirseEngine.Engine.Function.NPCJAVA;

import static rus.iglo.MirseEngine.MirseEngine.*;

public class Scripts {
    public static void execute() {

        var t = new NPCJAVA(0,-60,0,"n.png","???");
        t.create();
        DialogGUI dialogGUI = new DialogGUI("Привет я нпс тебе помочь?",new Rest[]{
                new Rest("давай",() -> {
                    t.sendMessage("я понял ты плохой");
                    t.moveToPosition(10,-60,10);
                })
        });
        t.performActionBeforeDistance(() -> {
            DialogGUI.open(dialogGUI);
        },3);
    }
}
