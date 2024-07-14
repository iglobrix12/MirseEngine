package rus.iglo.MirseEngine.script;

import rus.iglo.MirseEngine.Engine.Dialog.Dialog;
import rus.iglo.MirseEngine.Engine.Dialog.Rest;
import rus.iglo.MirseEngine.Engine.Function.NPCJAVA;
import rus.iglo.MirseEngine.MirseEngine;

public class Scripts {
    public static void execute() {
        MirseEngine.function.sendMessage("T");
        var t = new NPCJAVA(0,-60,0,"n.png","???");
        Dialog dialogGUI = new Dialog("Привет я нпс тебе помочь?",new Rest[]{
                new Rest("давай",() -> {
                    t.sendMessage("я понял ты плохой");
                    t.moveToPosition(10,-60,10);
                })
        });
        t.create();
        MirseEngine.sceneJAVA.addScenetimer(() -> {
            Dialog.open(dialogGUI);
        },5);
    }
}
