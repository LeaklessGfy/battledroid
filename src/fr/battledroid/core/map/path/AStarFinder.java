package fr.battledroid.core.map.path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import fr.battledroid.core.utils.Point;
import fr.battledroid.core.map.Map;

public final class AStarFinder implements PathFinder {
    private final Map map;
    private final int maxSearchDistance;
    private final boolean allowDiagMovement;

    public AStarFinder(Map map, int maxSearchDistance, boolean allowDiagMovement) {
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;
    }

    @Override
    public List<Point> findPath(Point src, Point dst, boolean allowNearest) {
        if (map.isBusy(dst) && !allowNearest) {
            return null;
        }

        Node[][] nodes = new Node[map.size()][map.size()];
        SortedList open = new SortedList();
        ArrayList<Node> closed = new ArrayList<>();
        Node closest = new Node(0,0, Integer.MAX_VALUE);

        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.size(); y++) {
                nodes[x][y] = new Node(x,y);
            }
        }
        nodes[src.x][src.y].cost = 0;
        nodes[src.x][src.y].depth = 0;
        open.add(nodes[src.x][src.y]);
        nodes[dst.x][dst.y].parent = null;

        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && !open.isEmpty()) {
            Node current = open.first();
            if (current == nodes[dst.x][dst.y]) {
                break;
            }
            closed.add(current);

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if ((x == 0) && (y == 0)) {
                        continue;
                    }
                    if (!allowDiagMovement) {
                        if ((x != 0) && (y != 0)) {
                            continue;
                        }
                    }

                    int xp = x + current.x;
                    int yp = y + current.y;

                    if (isValidLocation(src.x, src.y, xp, yp)) {
                        float nextStepCost = current.cost + map.cost(current.x, current.y, xp, yp);
                        Node neighbour = nodes[xp][yp];

                        if (nextStepCost < neighbour.cost) {
                            if (open.contains(neighbour)) {
                                open.remove(neighbour);
                            }
                            if (closed.contains(neighbour)) {
                                closed.remove(neighbour);
                            }
                        }

                        if (!open.contains(neighbour) && !closed.contains(neighbour)) {
                            neighbour.cost = nextStepCost;
                            neighbour.heuristic = getHeuristicCost(xp, yp, dst.x, dst.y);
                            if (neighbour.heuristic < closest.heuristic) {
                                closest = neighbour;
                            }
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            open.add(neighbour);
                        }
                    }
                }
            }
        }

        if (nodes[dst.x][dst.y].parent == null && !allowNearest) {
            return null;
        }

        LinkedList<Point> path = new LinkedList<>();
        Node target = nodes[dst.x][dst.y];
        if (nodes[dst.x][dst.y].parent == null) {
            target = closest;
        }
        while (target != nodes[src.x][src.y] && target != null) {
            path.addFirst(new Point(target.x, target.y));
            target = target.parent;
        }

        return path;
    }

    private boolean isValidLocation(int sx, int sy, int x, int y) {
        boolean invalid = (x < 0) || (y < 0) || (x >= map.size()) || (y >= map.size());
        if ((!invalid) && ((sx != x) || (sy != y))) {
            invalid = map.isBusy(x, y);
        }
        return !invalid;
    }

    private float getHeuristicCost(int x, int y, int tx, int ty) {
        float dx = tx - x;
        float dy = ty - y;
        return (float) (Math.sqrt((dx*dx)+(dy*dy)));
    }

    private class SortedList {
        private final PriorityQueue<Node> list = new PriorityQueue<>();
        private final HashSet<Node> container = new HashSet<>();

        Node first() {
            return list.poll();
        }

        public void add(Node n) {
            list.offer(n);
            container.add(n);
        }

        void remove(Node n) {
            list.remove(n);
            container.remove(n);
        }

        boolean isEmpty() {
            return list.isEmpty();
        }

        private boolean contains(Node n) {
            return container.contains(n);
        }
    }

    private class Node implements Comparable {
        private int x;
        private int y;
        private float cost;
        private Node parent;
        private float heuristic;
        private int depth;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Node(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.heuristic = h;
        }

        int setParent(Node parent) {
            depth = parent.depth + 1;
            this.parent = parent;
            return depth;
        }

        @Override
        public int compareTo(Object other) {
            Node o = (Node) other;
            float f = heuristic + cost;
            float of = o.heuristic + o.cost;

            return Float.compare(f, of);
        }
    }
}
