package com.inside_the_town_hall.game.board.lib.behavior.pathfinding.graph;

import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import org.lwjgl.system.linux.XVisibilityEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * Node of a graph
 * Class is used for the A* algorithm
 *
 * @author NekroQuest
 */
public class Node implements Cost<Node> {
    private final BoardPosition position;
    private final UUID itemId;
    private boolean open; // Node is potential path node
    private boolean checked; // Node has been checked
    private final boolean passable; // Movable can pass through node
    private Node parent;
    private final String nodeType; // Class name of entity represented by node

    public Node(BoardPosition position, UUID itemId, boolean passable, String type) {
        this.position = position;
        this.itemId = itemId;
        this.passable = passable;
        this.checked = false;
        this.open = false;
        this.parent = null;
        this.nodeType = type;
    }

    // Getter
    public BoardPosition getPosition() {
        return position;
    }

    public UUID getItemId() {
        return itemId;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isPassable() {
        return passable;
    }

    public double getCost(Node start, Node dest) {
        return getFCost(this, start, dest);
    }

    public Node getParent() {
        return this.parent;
    }

    public String getNodeType() {
        return nodeType;
    }

    @Override
    public double getGCost(Node from, Node to) {
        int xVertex = Math.abs(from.getPosition().getX() - to.getPosition().getX());
        int yVertex = Math.abs(from.getPosition().getY() - to.getPosition().getY());
        return xVertex + yVertex;
    }

    @Override
    public double getHCost(Node from, Node to) {
        int xVertex = Math.abs(from.getPosition().getX() - to.getPosition().getX());
        int yVertex = Math.abs(from.getPosition().getY() - to.getPosition().getY());
        int doorBonus = this.nodeType.equals("Door") ? 5 : 0; // They like to walk through doors :)
        // return xVertex + yVertex;
        return xVertex + yVertex - doorBonus;
    }

    @Override
    public double getFCost(Node current, Node start, Node dest) {
        return getGCost(start, current) + getHCost(current, dest);
    }

    // Setter
    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(position, node.position);
    }
}
