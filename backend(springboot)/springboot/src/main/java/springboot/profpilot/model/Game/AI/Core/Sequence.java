package springboot.profpilot.model.Game.AI.Core;

import java.util.List;

public class Sequence extends AiNode {
    private List<AiNode> children;

    public Sequence(List<AiNode> children) {
        this.children = children;
    }

    @Override
    public boolean run() {
        for (AiNode child : children) {
            if (!child.run()) {return false;}
        }
        return true;
    }
}