package com.inside_the_town_hall.game.board.lib.behavior.pathfinding.graph;

import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.Objects;
import java.util.UUID;

public class Node implements Cost<Node> {
    private final BoardPosition position;
    private final UUID itemId;
    private boolean open;
    private boolean checked;
    private final boolean passable;
    private Node parent;
    private final String nodeType;

    public Node(BoardPosition position, UUID itemId, boolean passable, String type) {
        this.position = position;
        this.itemId = itemId;
        this.passable = passable;
        this.checked = false;
        this.open = false;
        this.parent = null;
        this.nodeType = type;
    }

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


    @Override
    public double getGCost(Node from, Node to) {
        // Pythagoras
        int xVertex = Math.abs(from.getPosition().getX() - to.getPosition().getX());
        int yVertex = Math.abs(from.getPosition().getY() - to.getPosition().getY());
        return Math.sqrt((xVertex*xVertex) + (yVertex*yVertex));
    }

    @Override
    public double getHCost(Node from, Node to) {
        // Pythagoras
        int xVertex = Math.abs(from.getPosition().getX() - to.getPosition().getX());
        int yVertex = Math.abs(from.getPosition().getY() - to.getPosition().getY());
        return Math.sqrt((xVertex*xVertex) + (yVertex*yVertex));
    }

    @Override
    public double getFCost(Node current, Node start, Node dest) {
        return getGCost(start, current) + getHCost(current, dest);
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
}
