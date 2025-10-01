package model;

public class Highscore {
    private int id;
    private String name;
    private double time;
    private String difficulty;

    public Highscore(int id, String name, double time, String difficulty) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.difficulty = difficulty;
    }

    // Getterek
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    public String getDifficulty() {
        return difficulty;
    }
    
    public void setTime(double time) {
        this.time = time;
    }
}
