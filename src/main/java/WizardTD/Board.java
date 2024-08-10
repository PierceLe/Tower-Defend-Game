package WizardTD;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board
{
    PApplet pApplet;

    String layout;
    int[][] map;
    Cell[][] grid;

    HashMap<Integer, ArrayList<PVector>> validPaths;

    int x_wizardhouse = 0, y_wizardhouse = 0;

    public Board(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.map = new int[20][20];
        this.grid = new Cell[20][20];
        this.validPaths = new HashMap<>();
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public float getPixelX(float x)
    {
        return x * 32;
    }

    public float getPixelY(float y)
    {
        return y * 32 + 40;
    }

    /**
     * This method check the validity of position and return the position in PVector
     * @param x coordinate of position checked in horizontal
     * @param y coordinate of position check in vertial
     * @return the position in PVector
     */
    public PVector checkValidBuild(float x, float y)
    {
        int pos1 = (int) x / 32;
        int pos2 = (int) (y - 40) / 32;
        if (map[pos1][pos2] == 0)
        {
            return new PVector(getPixelX(pos1), getPixelY(pos2));
        }
        return new PVector(-1, -1);
    }

    /**
     * This method save the position of tower in map attribute
     * @param x coordinate of tower in horizontal
     * @param y coordinate of tower in vertical
     */
    public void setTower(float x, float y)
    {
        int pos1 = (int) x / 32;
        int pos2 = (int) (y - 40) / 32;
        map[pos1][pos2] = 2;
    }

    /**
     * This method create a cell object in each position of game board
     * @param Image The image of elements in the game
     */
    public void setupMap(HashMap<String, PImage> Image) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                int value = map[i][j];
                if (value == 0) {
                    grid[i][j] = new Cell("grass", Image.get("GrassImage"), i, j);
                } else if (value == 1) {
                    grid[i][j] = new Cell("shrub", Image.get("ShrubImage"), i, j);
                } else if (value >= 10) {
                    grid[i][j] = new Cell("path", Image.get(String.format("PathImage%d", map[i][j])), i, j);
                } else if (value == -1) {
                    grid[i][j] = new Cell("wizardhouse", Image.get("WizardImage"), i, j);
                }
            }
        }

    }

    public HashMap<Integer, ArrayList<PVector>> getPathToWizard()
    {
        return validPaths;
    }

    /**
     * This method read the layout text file and save them in a two dimension array with the number corresponding to the elements
     * @param src the relative path to layout text file
     */
    public void loadMap(String src) {
        try {
            File file = new File(src);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    char ch = line.charAt(j);
                    int value = 0;
                    if (ch == ' ') {
                        value = 0;
                    } else if (ch == 'S') {
                        value = 1;
                    } else if (ch == 'X') {
                        value = 10;
                    } else if (ch == 'W') {
                        value = -1;
                    }
                    map[j][i] = value;
                }

                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 20; i++)
        {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] >= 10) {
                    boolean left = (i > 0 && map[i - 1][j] >= 10) ? true : false;
                    boolean right = (i < 19 && map[i + 1][j] >= 10) ? true : false;
                    boolean up = (j > 0 && map[i][j - 1] >= 10) ? true : false;
                    boolean down = (j < 19 && map[i][j + 1] >= 10) ? true : false;

                    if (!left && !right && (up || down)) {
                        map[i][j] = 11;
                    } else if (up && left && !down && !right) {
                        map[i][j] = 12;
                    } else if (!up && left && down && !right) {
                        map[i][j] = 13;
                    } else if (!up && !left && down && right) {
                        map[i][j] = 14;
                    } else if (up && !left && !down && right) {
                        map[i][j] = 15;
                    } else if (up && left && !down && right) {
                        map[i][j] = 16;
                    } else if (up && left && down && !right) {
                        map[i][j] = 17;
                    } else if (!up && left && down && right) {
                        map[i][j] = 18;
                    } else if (up && !left && down && right) {
                        map[i][j] = 19;
                    } else if (up && left && down && right) {
                        map[i][j] = 20;
                    }

                }
            }
        }

        ArrayList<PVector> startPoints = new ArrayList<>();
        for (int i : new int[]{0, 19})
        {
            for(int j = 0; j < 20; j++)
            {
                if (map[i][j] == 10)
                {
                    startPoints.add(new PVector(i, j));
                }
            }
        }

        for(int j : new int[]{0, 19})
        {
            for(int i = 0; i < 20; i++)
            {
                if (map[i][j] == 11)
                {
                    startPoints.add(new PVector(i, j));
                }
            }
        }

        BFS bfs = new BFS();
        int indexPath = 0;
        for(PVector start: startPoints)
        {
            ArrayList<PVector> shortestPath = bfs.shortestPath(map, start);
            if (shortestPath.size() != 0)
            {
                validPaths.put(indexPath, shortestPath);
                indexPath++;
            }

        }
    }

    /**
     * This method display the game board to screen
     */
    public void display()
    {
        int x_grass = 0, y_grass = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell cell = grid[i][j];
                if (cell.getType() == "grass")
                {
                    x_grass = i;
                    y_grass = j;
                }
                if (cell.getType() == "wizardhouse") {
                    x_wizardhouse = i;
                    y_wizardhouse = j;
                } else {
                    pApplet.image(cell.getImg(), i * 32, j * 32 + 40, 32, 32);
                }

            }
        }
        pApplet.image(grid[x_grass][y_grass].getImg(), x_wizardhouse * 32 - 10, y_wizardhouse * 32 - 7 + 40, 32, 32);

    }

    /**
     * This method display the WizardHouse to screen
     */
    public void displayWizard()
    {
        pApplet.image(grid[x_wizardhouse][y_wizardhouse].getImg(), x_wizardhouse * 32 - 7, y_wizardhouse * 32 - 7 + 40, 48, 48);
    }
}
