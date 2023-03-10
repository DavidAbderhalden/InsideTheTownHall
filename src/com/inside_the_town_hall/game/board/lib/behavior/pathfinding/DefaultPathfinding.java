package com.inside_the_town_hall.game.board.lib.behavior.pathfinding;

import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.graph.Node;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Default Pathfinding uses the A* algorithm for pathfinding
 *
 * @author NekroQuest
 */
public class DefaultPathfinding implements IPathfindingBehavior {
    private Node currentNode;
    private Node startNode;
    private Node destinationNode;

    /**
     * Pathfind between two positions
     *
     * @param start the starting position
     * @param destination the destination position
     * @return a HashMap of every move the entity must take to reach the destination
     */
    @Override
    public HashMap<BoardPosition, BoardPosition> pathfind(BoardPosition start, BoardPosition destination) {
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> checkedList = new ArrayList<>();
        Node[][] nodes = convertBoardItems();

        this.startNode = nodes[start.getY()][start.getX()];
        this.destinationNode = nodes[destination.getY()][destination.getX()];

        if (!this.startNode.isPassable() || !this.destinationNode.isPassable()) {
            return null;
        }
        this.currentNode = this.startNode;

        int controller = 0;
        int maxIterations = (Board.getInstance().getLayout().getBoardHeight() * Board.getInstance().getLayout().getBoardWidth()) + 1;

        while (controller < maxIterations) {
            int x = this.currentNode.getPosition().getX();
            int y = this.currentNode.getPosition().getY();

            this.currentNode.setChecked(true);
            checkedList.add(this.currentNode);
            openList.remove(this.currentNode);

            // OPEN UP
            if (x - 1 >= 0) {
                openNode(nodes[y][x - 1], openList);
            }
            // OPEN LEFT
            if (y - 1 >= 0) {
                openNode(nodes[y - 1][x], openList);
            }
            // OPEN RIGHT
            if (y + 1 <= Board.getInstance().getLayout().getBoardHeight() - 1) {
                openNode(nodes[y + 1][x], openList);
            }
            // OPEN DOWN
            if (x + 1 <= Board.getInstance().getLayout().getBoardWidth() - 1) {
                openNode(nodes[y][x + 1], openList);
            }

            int bestFieldIndex = 0;
            double bestNodeFCost = Double.POSITIVE_INFINITY;

            for (int i = 0; i < openList.size(); i++) {
                if (bestNodeFCost > openList.get(i).getCost(this.startNode, this.destinationNode)) {
                    bestNodeFCost = openList.get(i).getCost(this.startNode, this.destinationNode);
                    bestFieldIndex = i;
                } else if (bestNodeFCost == openList.get(i).getCost(this.startNode, this.destinationNode)) {
                    if (openList.get(bestFieldIndex).getGCost(this.startNode, this.destinationNode) > openList.get(i).getGCost(this.startNode, this.destinationNode)) {
                        bestFieldIndex = i;
                    }
                }
            }
            try {
                this.currentNode = openList.get(bestFieldIndex);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
            if (this.currentNode.equals(this.destinationNode)) {
                this.destinationNode = this.currentNode;
                return backTrack();
            }
            controller++;
        }
        return null;
    }

    /**
     * Uses result of pathfinding to trace the nodes back and get the path
     *
     * @return HashMap of every move the entity must take to reach the destination
     */
    private HashMap<BoardPosition, BoardPosition> backTrack() {
        HashMap<BoardPosition, BoardPosition> path = new HashMap<>();
        Node current = this.destinationNode;
        Node last = current;

        while (!current.equals(this.startNode)) {
            current = current.getParent();
            if (!current.equals(this.startNode)) {
                path.put(current.getPosition(), last.getPosition());
                last = current;
            } else {
                path.put(current.getPosition(), last.getPosition());
            }
        }
        return path;
    }

    /**
     * Opens a node (starts to use it in the pathfinding)
     *
     * @param node the node to be opened
     * @param openList the list with all the open nodes
     */
    private void openNode(Node node, ArrayList<Node> openList) {
        if (!node.isOpen() && !node.isChecked() && node.isPassable()) {
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    /**
     * Converts all the board items to a more usable datatype and node
     *
     * @return the converted items as a 2d node array
     */
    private Node[][] convertBoardItems() {
        Node[][] convertedBoardItems = new Node[Board.getInstance().getLayout().getBoardHeight()][Board.getInstance().getLayout().getBoardWidth()];
        for (Map.Entry<UUID, BoardItem> entry : Board.getInstance().getLayout().getById().entrySet()) {
            boolean isPassable = entry.getValue().isPassable();
            String[] nameArray = entry.getValue().getItem().getClass().getName().split("\\.");
            String name = nameArray[nameArray.length - 1];
            convertedBoardItems[entry.getValue().getPosition().getY()][entry.getValue().getPosition().getX()] = new Node(entry.getValue().getPosition(), entry.getValue().getUUIDId(), isPassable, name);
        }
        return convertedBoardItems;
    }
}
