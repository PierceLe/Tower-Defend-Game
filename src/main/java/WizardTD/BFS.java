package WizardTD;

import processing.core.PVector;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.*;

public class BFS
{
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    /**
     * Tracing the path to wizardhouse after perform an BFS
     * @param parent containing two adjacent point in the path leading to wizardhouse
     * @param start the starting point of monster
     * @param end the ending point of monster
     * @return the shortest path to wizardhouse
     */
    public ArrayList<PVector> trace(HashMap<PVector, PVector> parent, PVector start, PVector end)
    {
        ArrayList<PVector> shortestpath = new ArrayList<>();
        shortestpath.add(end);
        while (!start.equals(shortestpath.get(shortestpath.size() - 1)))
        {
            shortestpath.add(parent.get(shortestpath.get(shortestpath.size() - 1)));
        }

        Collections.reverse(shortestpath);
        return shortestpath;

    }

    /**
     * This method produce the path to wizard
     * @param grid containing details of each cell in the game board
     * @param start the starting point in the game board
     * @return Collection of PVector that make up path to wizardhouse
     */
    public ArrayList<PVector> shortestPath(int[][] grid, PVector start)
    {
        int row = grid.length;
        int col = grid[0].length;
        int[][] check = new int[row][col];
        HashMap<PVector, PVector> parents = new HashMap<>();
        for(int[] rows: check)
        {
            Arrays.fill(rows, 0);
        }
        Queue<PVector> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty())
        {
            PVector currentPoint = queue.poll();
            int x = (int) currentPoint.x;
            int y = (int) currentPoint.y;

            if (grid[x][y] == -1)
            {
                return trace(parents, start, new PVector(x, y));
            }
            for(int i = 0; i < 4; i++)
            {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX >= 0 && newX < row && newY > 0 && newY < col && check[newX][newY] == 0
                    && (grid[newX][newY] >= 10 || grid[newX][newY] == -1))
                {
                    check[newX][newY] = check[x][y] + 1;
                    PVector newPoint = new PVector(newX, newY);
                    parents.put(newPoint, currentPoint);
                    queue.add(newPoint);
                }
            }
        }
        return new ArrayList<>();
    }


}
