package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.IPathfindingBehavior;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Movable board item actions
 *
 * @author NekroQuest
 */
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

    /**
     * Cancels an action
     * @param actionId the id of the action
     * @return if the action was canceled
     */
    @Override
    public boolean cancelAction(UUID actionId) {
        return this.abortedActions.contains(actionId);
    }

    /**
     * Cancels all actions
     */
    @Override
    public void abort() {
        this.abortedActions.addAll(this.activeActions);
        this.activeActions = new LinkedList<>();
    }

    /**
     * Moves board item in scheduler
     * @param path the HashMap with the paths to move
     * @param actionId the id of the moving action
     */
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

    /**
     * Pathfind and move the board item to the destination
     * @param targetPos position to pathfind to
     */
    @Override
    public void pathfindTo(BoardPosition targetPos) {
        abort();
        UUID actionId = UUID.randomUUID();
        HashMap<BoardPosition, BoardPosition> path = this.pathfindingBehavior.pathfind(this.boardPosition, targetPos);
        if(path == null) return;
        GameController.getInstance().getScheduler().createTimedTask(
                () -> moveToTask(path, actionId),
                this.entity.getSpeed(),
                path.size()
        );
        this.activeActions.add(actionId);
    }

    /**
     * Moves the entity for one position
     * @param targetPosition the next position
     */
    @Override
    public void moveTo(BoardPosition targetPosition) {
        if(!Board.getInstance().getLayout().isPassablePosition(targetPosition)) {
            return;
        }
        abort();
        UUID actionId = UUID.randomUUID();
        BoardPosition boardPosition = this.boardPosition;
        HashMap<BoardPosition, BoardPosition> path = new HashMap<>() {{
            put(boardPosition, targetPosition);
        }};
        GameController.getInstance().getScheduler().createVolatileTask(
                () -> moveToTask(path, actionId),
                this.entity.getSpeed()
        );
        this.activeActions.add(actionId);
    }

    // Getter
    @Override
    public IPathfindingBehavior getPathfindingBehavior() {
        return this.pathfindingBehavior;
    }
}
