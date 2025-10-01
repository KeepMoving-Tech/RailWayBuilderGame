package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Database;
import model.Direction;
import model.Player;
import model.Position;
import model.Tile;
import model.Enemy;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Highscore;
import model.HighscoreManager;
import UI.RailWayBuilderGame;
import javax.swing.JFrame;



public class Board extends JPanel {

    private Player player;
    private List<Tile> tiles;
    private List<Enemy> enemies;
    
    private final int TILE_SIZE = 64;
    private Map<String, Image> tileImages;
    private String carriedRailImage;
    private Random random = new Random();
    private List<String> railImages = Arrays.asList("betonrail1.jpg", "betonrail2.jpg", "betonrail3.jpg", "betonrail4.jpg", "betonrail5.jpg", "betonrail6.jpg");
    private JButton resetButton;
    private Timer enemiesMovementTimer;
    private long startTime;
    private long elapsedTime;
    private int mapId; 
     private JLabel timerLabel;
     
     
    public Board(String selectedCharacterImage,int mapIds) {
        this.setLayout(null);
        resetButton = new JButton("Reset");
        resetButton.setBounds(140, 5, 100, 50);
        resetButton.addActionListener(e-> resetBoard(selectedCharacterImage));
        resetButton.setFocusable(false);
                
        this.timerLabel = timerLabel;
        
        this.setFocusable(true);
        this.add(resetButton);
        
        timerLabel = new JLabel("Timer: 0");
        timerLabel.setBounds(10, 10, 150, 30); 
        this.add(timerLabel);
        
        if(mapIds==1){
           this.mapId = mapIds; 
        }else if(mapIds == 2){
             this.mapId = mapIds+3; 
        }
        
        startTime = System.currentTimeMillis();
        
        enemies = new ArrayList<>();
        if (mapId > 4) {
            // Wolf ellenségek létrehozása
            enemies.add(new Enemy(new Position(3, 5), mapId));
            enemies.add(new Enemy(new Position(5, 2), mapId));
            enemies.add(new Enemy(new Position(6, 5), mapId));
            enemies.add(new Enemy(new Position(2, 7), mapId));
            enemies.add(new Enemy(new Position(7, 8), mapId));
            enemies.add(new Enemy(new Position(10, 2), mapId));
        } else {
            // Cow ellenségek létrehozása
            enemies.add(new Enemy(new Position(5, 2), mapId));
            enemies.add(new Enemy(new Position(6, 5), mapId));
            enemies.add(new Enemy(new Position(2, 7), mapId));
        }
        
        
        this.setFocusable(true);
        
        Database db = new Database();
        tiles = db.loadMap(mapId);
        
        player = new Player(new Position(1, 0));
        loadImages(selectedCharacterImage);

        startEnemiesMovementTimer();
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        player.move(Direction.UP, tiles,enemies);
                        break;
                    case KeyEvent.VK_S:
                        player.move(Direction.DOWN, tiles,enemies);
                        break;
                    case KeyEvent.VK_A:
                        player.move(Direction.LEFT, tiles,enemies);
                        break;
                    case KeyEvent.VK_D:
                        player.move(Direction.RIGHT, tiles,enemies);
                        break;
                    case KeyEvent.VK_SPACE:
                        handleSpaceKey(selectedCharacterImage);
                        break;
                }
                repaint();
            }
        });
    }

    private void startEnemiesMovementTimer() {
            enemiesMovementTimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveEnemies();
                }
            });
            enemiesMovementTimer.start();
        }


           private void moveEnemies() {
            for (Enemy enemy : enemies) {
                enemy.moveRandomly(tiles,player.getPosition());
            }
            repaint();
        }
    
    private void resetBoard(String selectedCharacterImage) {
           
        
        
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        timerLabel.setText("Timer: 0"); 
        tileImages.put("PLAYER", loadImage(selectedCharacterImage));
        Database db = new Database();
        tiles = db.loadMap(mapId); 
        elapsedTime = 0;
        player.getPosition().setX(1);
        player.getPosition().setY(0);

        enemies = new ArrayList<>();
        if (mapId > 4) {
            // Wolf ellenségek létrehozása
            enemies.add(new Enemy(new Position(3, 0), mapId));
            enemies.add(new Enemy(new Position(5, 2), mapId));
            enemies.add(new Enemy(new Position(6, 5), mapId));
            enemies.add(new Enemy(new Position(2, 7), mapId));
            enemies.add(new Enemy(new Position(7, 8), mapId));
            enemies.add(new Enemy(new Position(10, 2), mapId));
        } else {
            // Cow ellenségek létrehozása
            enemies.add(new Enemy(new Position(3, 2), mapId));
            enemies.add(new Enemy(new Position(5, 2), mapId));
            enemies.add(new Enemy(new Position(6, 5), mapId));
            enemies.add(new Enemy(new Position(2, 7), mapId));
        }


        carriedRailImage = null;


        this.setFocusable(true);
        repaint();
    }
    
    
    
    private void handleSpaceKey(String selectedCharacterImage ) {
        Position playerPos = player.getPosition();
        for (Tile tile : tiles) {
            if (tile.getX() == playerPos.getX() && tile.getY() == playerPos.getY()) {

                if (playerPos.getX() == 0 && playerPos.getY() == 3 && "BETONRAIL".equals(tile.getType()) ) {
                    if (carriedRailImage == null) {
                        carriedRailImage = tile.getCustomImage();
                        tile.setCustomImage("betonrail1.jpg");
                        tileImages.put("PLAYER", loadImage("Builder_I_rail.png"));
                    }
                            
                } else if (playerPos.getX() == 0 && playerPos.getY() == 1) {
                    if (carriedRailImage != null) {
                        carriedRailImage = null;
                        tileImages.put("PLAYER", loadImage(selectedCharacterImage));
                    }
                } else if ("GRASS".equals(tile.getType()) || "SNOW".equals(tile.getType()) ) {
                    if (carriedRailImage != null) {
                        tile.setType("RAIL");
                        tile.setCustomImage(carriedRailImage);
                        carriedRailImage = null;
                        tileImages.put("PLAYER", loadImage(selectedCharacterImage));

                        resetBetonRail();
                        
                        
                        Database db = new Database();
                        List<Position> stations = db.loadStations(mapId);
                         if (stations.size() >= 2) {
                            Position station1 = stations.get(0); 
                            Position station2 = stations.get(1);
                        
                        Map<Position, List<Position>> graph = buildRailGraph();
                        if (isConnected(station1, station2, graph)) {
                            System.out.println("Stations are connected!");
                            
                             this.mapId++;
                             if(this.mapId == 3 || this.mapId == 7){
                                  String playerName = JOptionPane.showInputDialog(this, "Enter your name:");
    
                                if (playerName != null && !playerName.isEmpty()) {                            
                                HighscoreManager highscoreManager = new HighscoreManager();

                                    if(this.mapId > 4){
                                        highscoreManager.saveHighscore(playerName, elapsedTime, "Medium");
                                    }else{
                                        highscoreManager.saveHighscore(playerName, elapsedTime, "Easy");
                                    }

                                        List<Highscore> highscores = highscoreManager.getHighscores();
                                        displayHighscores(highscores);
                                        
                                        RailWayBuilderGame railwayBuilder = new RailWayBuilderGame();
                                        railwayBuilder.setVisible(true);
                                        //this.setVisible(false);
                                        JFrame parentFrame = (JFrame) this.getTopLevelAncestor(); // Az aktuális JFrame elérése
                                        parentFrame.getContentPane().remove(this);  // A JPanel eltávolítása
                                        parentFrame.revalidate();  // Az új elrendezés frissítése
                                        parentFrame.repaint();  // Az új elrendezés kirajzolása
                                 }
                             }
   
                             loadNextMap();
                        } else {
                            System.out.println("Stations are not connected!");
                        }
                    }
                    }
                }
                break;
            }
        }
    }
    private void loadNextMap() {
    Database db = new Database();
    tiles = db.loadMap(mapId);
    
    player.getPosition().setX(1);
    player.getPosition().setY(0);
    
    enemies = new ArrayList<>();
    if (mapId > 4) {
        // Wolf ellenségek létrehozása
        enemies.add(new Enemy(new Position(3, 5), mapId));
        enemies.add(new Enemy(new Position(5, 2), mapId));
        enemies.add(new Enemy(new Position(6, 5), mapId));
        enemies.add(new Enemy(new Position(2, 7), mapId));
        enemies.add(new Enemy(new Position(7, 8), mapId));
        enemies.add(new Enemy(new Position(10, 2), mapId));
    } else {
        // Cow ellenségek létrehozása
        enemies.add(new Enemy(new Position(5, 2), mapId));
        enemies.add(new Enemy(new Position(6, 5), mapId));
        enemies.add(new Enemy(new Position(2, 7), mapId));
    }
    
    carriedRailImage = null;
    
    startTime = System.currentTimeMillis();
    
    repaint();
}
    
    private void displayHighscores(List<Highscore> highscores) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %-15s\n", "Name", "Time", "Difficulty"));
        sb.append("---------------------------------------------------------------\n");

        for (Highscore highscore : highscores) {
            sb.append(String.format("%-20s %-10.2f %-15s\n", highscore.getName(), highscore.getTime(), highscore.getDifficulty()));
        }

        JOptionPane.showMessageDialog(this, sb.toString());  
    }
    
    private void resetBetonRail() {
        for (Tile tile : tiles) {
            if (tile.getX() == 0 && tile.getY() == 3 && "BETONRAIL".equals(tile.getType()) ) {
                tile.setCustomImage(getRandomRailImage());
                break;
            }
        }
    }
    

    private String getRandomRailImage() {
        return railImages.get(random.nextInt(railImages.size()));
    }

    private Map<Position, List<Position>> buildRailGraph() {
    Map<Position, List<Position>> graph = new HashMap<>();

        // Minden egyes csempére ellenőrizzük a szomszédos vasúti szakaszokat
        for (Tile tile : tiles) {
            if (isRail(tile)) {
                Position pos = new Position(tile.getX(), tile.getY());
                List<Position> connections = new ArrayList<>();

                for (Direction dir : Direction.values()) {
                    Position neighborPos = getNeighborPosition(pos, dir);
                    Tile neighborTile = getTileAtPosition(neighborPos);
                    if (neighborTile != null && isRail(neighborTile)) {
                        connections.add(neighborPos);
                    }
                }

                graph.put(pos, connections);
            }
        }

        return graph;
    
    }

    private boolean isConnected(Position start, Position end, Map<Position, List<Position>> graph) {
        Set<Position> visited = new HashSet<>();
        return dfs(start, end, graph, visited);
    }

    private boolean dfs(Position current, Position target, Map<Position, List<Position>> graph, Set<Position> visited) {
        if (current.equals(target)) {
            return true;
        }

        visited.add(current);

        for (Position neighbor : graph.getOrDefault(current, List.of())) {
            if (!visited.contains(neighbor)) {
                if (dfs(neighbor, target, graph, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Position getNeighborPosition(Position pos, Direction direction) {
        switch (direction) {
            case UP:
                return new Position(pos.getX(), pos.getY() - 1);
            case DOWN:
                return new Position(pos.getX(), pos.getY() + 1);
            case LEFT:
                return new Position(pos.getX() - 1, pos.getY());
            case RIGHT:
                return new Position(pos.getX() + 1, pos.getY());
            default:
                return null;
        }
    }

    private boolean isRail(Tile tile) {
        return tile.getType().equals("RAIL") || tile.getType().equals("BETONRAIL") || tile.getType().equals("STATION");
    }

    private Tile getTileAtPosition(Position pos) {
        for (Tile tile : tiles) {
            if (tile.getX() == pos.getX() && tile.getY() == pos.getY()) {
                return tile;
            }
        }
        return null;
    }
    
    private void loadImages(String selectedCharacterImage) {
        tileImages = new HashMap<>();
        tileImages.put("GRASS", loadImage("grass2.jpg"));
        tileImages.put("SNOW", loadImage("snow.png"));
        tileImages.put("STATION", loadImage("station2.png"));
        tileImages.put("MOUNTAIN", loadImage("Mountain.jpg"));
        tileImages.put("LAKE", loadImage("lake.jpg"));
        tileImages.put("KUKA", loadImage("kuka.png"));
        tileImages.put("RAIL", loadImage("rail.jpg"));
        tileImages.put("TREE", loadImage("tree.png"));
        tileImages.put("PLAYER", loadImage(selectedCharacterImage));
        tileImages.put("BETONRAIL", loadImage(getRandomRailImage()));
        tileImages.put("SNOWMOUNTAIN", loadImage("snowMountain2.png"));
        tileImages.put("PINE", loadImage("pine.png"));
        tileImages.put("HOUSE", loadImage("house.png"));
        carriedRailImage = getRandomRailImage();
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource("/Images/" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateBoard(List<Tile> tiles) {
    this.tiles = tiles;
    repaint();
}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Másodpercekben
        timerLabel.setText("Timer: " + elapsedTime); // TimerLabel frissítése
        
        //Tiles
        for (Tile tile : tiles) {
            int tileX = tile.getX() * TILE_SIZE;
            int tileY = tile.getY() * TILE_SIZE;

            Image tileImage = tile.getCustomImage() != null
                    ? loadImage(tile.getCustomImage())
                    : tileImages.get(tile.getType());

            if (tileImage != null) {
                g2d.drawImage(tileImage, tileX, tileY, TILE_SIZE, TILE_SIZE, null);
            }
        }

        // Player
        g2d.drawImage(tileImages.get("PLAYER"), player.getPosition().getX() * TILE_SIZE, player.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
       // Enemies
       for (Enemy enemy : enemies) {
            g2d.drawImage(enemy.getEnemyImage(), enemy.getPosition().getX() * TILE_SIZE, enemy.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
}
    }
}
