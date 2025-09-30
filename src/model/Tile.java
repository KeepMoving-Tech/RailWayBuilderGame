package model;

public class Tile {
    private int x;
    private int y;
    private String type;
    private String customImage;

    public Tile(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Tile(int x, int y, String type, String customImage) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.customImage = customImage;
    }

    // Getterek és setterek
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomImage() {
        return customImage;
    }

    public void setCustomImage(String customImage) {
        this.customImage = customImage;
    }
}