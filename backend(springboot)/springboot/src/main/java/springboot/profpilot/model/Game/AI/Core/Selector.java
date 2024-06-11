package springboot.profpilot.model.Game.AI.Core;

import java.util.List;

public class Selector extends AiNode {
    private List<AiNode> children;

    public Selector(List<AiNode> children) {
        this.children = children;
    }

    @Override
    public boolean run() {
        for (AiNode child : children) {
            if (!child.run()) {
                return false;
            }
        }
        return true;
    }
}