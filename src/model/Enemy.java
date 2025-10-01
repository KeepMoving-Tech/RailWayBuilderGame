package model;

import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy {

    private Position position;
    private final Random random = new Random();
    private Image enemyImage;
    private int mapId; // Tárolja a mapId-t
    
    private static final int MAX_X = 12;
    private static final int MAX_Y = 8;

    public Enemy(Position position, int mapId) {
        this.position = position;
         this.mapId = mapId;
        if (mapId  > 4) {
            this.enemyImage = new ImageIcon(getClass().getResource("/Images/wolf.png")).getImage();
        } else {
            this.enemyImage = new ImageIcon(getClass().getResource("/Images/cow.png")).getImage();
        }
}
    // Véletlenszerű mozgás
    public void moveRandomly(List<Tile> tiles,Position playerPosition) {
        int newX = position.getX();
        int newY = position.getY();

        List<Direction> directions = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        Direction randomDirection = directions.get(new Random().nextInt(directions.size()));

        switch (randomDirection) {
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

        if ((newX != playerPosition.getX() || newY != playerPosition.getY()) && newX > 0 && newX < MAX_X && newY > 0 && newY < MAX_Y && canMoveTo(newX, newY, tiles)) {
            position = new Position(newX, newY);
        }
    }

    private boolean canMoveTo(int x, int y, List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                String tileType = tile.getType();
                return !"MOUNTAIN".equals(tileType) && !"LAKE".equals(tileType);
            }
        }
        return false;
    }

    public Image getEnemyImage() {
        return enemyImage;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}


