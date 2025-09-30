package model;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Player {

    private Position position;
    private static final Set<String> BLOCKED_TILES = new HashSet<>();

    static {
        BLOCKED_TILES.add("MOUNTAIN");
        BLOCKED_TILES.add("LAKE");
        BLOCKED_TILES.add("TREE");
        BLOCKED_TILES.add("WOLF");
        BLOCKED_TILES.add("SNOWMOUNTAIN");
        BLOCKED_TILES.add("PINE");
        BLOCKED_TILES.add("EMPTY");
    }

    public Player(Position initialPosition) {
        this.position = initialPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Direction direction, List<Tile> tiles, List<Enemy> enemies) {
        int newX = position.getX();
        int newY = position.getY();

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }

        for (Enemy enemy : enemies) {
            if (enemy.getPosition().getX() == newX && enemy.getPosition().getY() == newY) {
                return;
            }
    }
        
        if (newX >= 0 && newX < 12 && newY >= 0 && newY < 9 && canMoveTo(newX, newY, tiles)) {
            this.position = new Position(newX, newY);
        }
    }

    private boolean canMoveTo(int x, int y, List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                String tileType = tile.getType();
                return !BLOCKED_TILES.contains(tileType);
            }
        }
        return false;
    }
}
