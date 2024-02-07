package com.bmhs.gametitle.game.assets.tasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.bmhs.gametitle.game.assets.characters.NonPlayerCharacter;

public class TalkTask extends LeafTask<NonPlayerCharacter> {

    NonPlayerCharacter npc;

    @Override
    public void start() {
        System.out.println("Starting talk task");
        npc = getObject();
    }

    @Override
    public Status execute() {
        System.out.println("Executing talk task");
        npc.talk();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<NonPlayerCharacter> copyTo(Task<NonPlayerCharacter> task) {
        return null;
    }
}
