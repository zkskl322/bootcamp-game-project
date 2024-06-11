package springboot.profpilot.model.Game.AI.Core.Action;

import springboot.profpilot.model.Game.AI.Core.AiNode;

public class Action extends AiNode {
    private ActionFunction actionFunc;

    public Action(ActionFunction actionFunc) {
        this.actionFunc = actionFunc;
    }

    @Override
    public boolean run() {
        actionFunc.perform();
        return true;
    }
}