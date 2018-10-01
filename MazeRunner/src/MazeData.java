import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int entranceX, entranceY;
    private int exitX, exitY;

    private char[][] maze;
    private int N, M; // row, column

    public boolean[][] visited;
    public boolean[][] path;

    public boolean[][] results;

    public MazeData(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename can not be null!");
        }

        Scanner scanner = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                throw new IllegalArgumentException("File " + filename + " does not exist!");
            }
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String nmline = scanner.nextLine();


            // The file line of the maze file records the row and column number of the maze
            // This code parse this information
            String[] nm = nmline.trim().split("\\s+");
            N = Integer.parseInt(nm[0]); // row
            M = Integer.parseInt(nm[1]); // column

            maze = new char[N][M];
            visited = new boolean[N][M];
            path = new boolean[N][M];
            results = new boolean[N][M];

            for (int i = 0; i < N; i++) {
                String line = scanner.nextLine();
                if (line.length() != M) {
                    throw new IllegalArgumentException("Maze file " + filename + " is not valid");
                }
                for (int j = 0; j < M; j++) {
                    maze[i][j] = line.charAt(j);
                    visited[i][j] = false;
                    path[i][j] = false;
                    results[i][j] = false;
                }
            }

        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        // hard code the information of entrance and exists
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;

    }


    public int N() {return N;}
    public int M() {return M;}
    public int getEntranceX() { return entranceX; }
    public int getEntranceY() { return entranceY; }
    public int getExitX() { return exitX; }
    public int getExitY() { return exitY; }

    public char getMaze(int i, int j) {
        if (!inArea(i, j)) {
            throw new IllegalArgumentException("i or j is out of Index");
        }
        return maze[i][j];
    }

    public boolean inArea(int x, int y) {
        return x < N && y < M && x >= 0 && y >= 0;
    }

    public void print() {
        System.out.println(N + " * " + N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        return;
    }
}
