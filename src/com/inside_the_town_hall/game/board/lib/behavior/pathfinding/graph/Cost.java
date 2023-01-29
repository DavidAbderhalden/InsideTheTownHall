package com.inside_the_town_hall.game.board.lib.behavior.pathfinding.graph;

/**
 * Cost interface for A* algorithm
 *
 * @param <T> A node of the graph
 * @author NekroQuest
 */
public interface Cost<T extends Node> {

    double getGCost(T from, T to);

    double getHCost(T from, T to);

    double getFCost(T current, T start, T dest);
}
