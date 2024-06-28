package springboot.profpilot.model.Game.AI.Core.Condition;

import springboot.profpilot.model.Game.AI.Core.AiNode;

public class Condition extends AiNode {
    private ConditionFunction conditionFunc;
    public Condition(ConditionFunction conditionFunc) {
        this.conditionFunc = conditionFunc;
    }
    @Override
    public boolean run() {
        return conditionFunc.check();
    }
}