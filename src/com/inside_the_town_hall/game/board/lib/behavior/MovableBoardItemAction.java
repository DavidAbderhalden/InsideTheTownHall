package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class MovableBoardItemAction implements IBoardItemAction{
    private final Entity entity;
    private final UUID boardItemId;
    private final BoardPosition boardPosition;
    private LinkedList<UUID> activeActions;
    private final LinkedList<UUID> abortedActions;
    private final IPathfindingBehavior pathfindingBehavior;
    private final LogHandler LOGGER = new LogHandler(this.getClass());


    public MovableBoardItemAction(UUID boardItemId, Entity entity, BoardPosition boardPosition, IPathfindingBehavior pathfindingBehavior){
        this.boardItemId = boardItemId;
        this.entity = entity;
        this.boardPosition = boardPosition;
        this.activeActions = new LinkedList<>();
        this.abortedActions = new LinkedList<>();
        this.pathfindingBehavior = pathfindingBehavior;
    }

    @Override
    public boolean cancelAction(UUID actionId) {
        return this.abortedActions.contains(actionId);
    }

    @Override
    public void abort() {
        this.abortedActions.addAll(this.activeActions);
        this.activeActions = new LinkedList<>();
    }

    @Override
    public IPathfindingBehavior getPathfindingBehavior() {
        return this.pathfindingBehavior;
    }

    @Override
    public void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId) {
        if(cancelAction(actionId)) {
            this.LOGGER.deepLog(LogMode.YELLOW, "SCHEDULER.TASK.ACTION.CANCEL", new HashMap<>(){{
                put("ACTION_ID", actionId.toString());
            }});
            return;
        }
        BoardPosition nextPosition = path.get(this.boardPosition);
        Board.getInstance().getItem(this.boardItemId).moveTo(nextPosition);
        // GUIManager.getInstance().getCurrentScreen().update();
    }

    @Override
    public void pathfindTo(BoardPosition targetPos) {
        UUID actionId = UUID.randomUUID();
        HashMap<BoardPosition, BoardPosition> path = this.pathfindingBehavior.pathfind(this.boardPosition, targetPos);
        GameController.getInstance().getScheduler().createTimedTask(
                () -> moveToTask(path, actionId),
                entity.getSpeed(),
                path.size()
        );
        this.activeActions.add(actionId);
    }
}
