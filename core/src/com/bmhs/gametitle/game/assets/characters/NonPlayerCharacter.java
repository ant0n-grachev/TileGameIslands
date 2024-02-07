package com.bmhs.gametitle.game.assets.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.MathUtils;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

import java.io.Reader;

public class NonPlayerCharacter extends Character {

    public String name;
    public String brainLog;
    private BehaviorTree<NonPlayerCharacter> behaviorTree;
    public NonPlayerCharacter(WorldTile tile, float x, float y, String name) {
        super(tile, x, y);
        this.name = name;
        this.brainLog = name + " brain";

        Reader reader = Gdx.files.internal("btrees/npc.btree").reader();
        BehaviorTreeParser<NonPlayerCharacter> parser = new BehaviorTreeParser<>(BehaviorTreeParser.DEBUG_HIGH);
        behaviorTree = parser.parse(reader, this);
    }

    public void tickTree() {
        behaviorTree.step();
    }

    public BehaviorTree<NonPlayerCharacter> getBehaviorTree() {
        return behaviorTree;
    }

    public void setBehaviorTree(BehaviorTree<NonPlayerCharacter> behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public void talk() {
        if (MathUtils.randomBoolean()) {
            System.out.println("Where am I?");
        }
        else {
            System.out.println("I'm at " + getY() + " " + getX());
        }
    }

    public void walk() {
        if (Math.random() > .95) {
            adjustX(MathUtils.random(-10, 10));
            adjustY(MathUtils.random(-10, 10));
        }
    }
}
