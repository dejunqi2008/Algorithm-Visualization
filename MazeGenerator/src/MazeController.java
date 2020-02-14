import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Stack;

public class MazeController {

    private static int blockSide = 8;
    private static int DELAY = 5;
    private MazeData data;
    private MazeView view;
    int dirs[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};


    public MazeController(int row, int col) {
        data = new MazeData(row, col);
        int sceneHeight = data.getRow() * blockSide;
        int sceneWidth = data.getCol() * blockSide;

        EventQueue.invokeLater(() -> {
            view = new MazeView("Random Maze Generation", sceneWidth, sceneHeight);
            new Thread(() -> {
                generate();
            }).start();
        });
    }

    // Generate maze
    private void generate() {
         setData(-1, -1);
//         dfsRecurive(data.getEntranceX(), data.getEntranceY() + 1);
//         dfsIterative(data.getEntranceX(), data.getEntranceY() + 1);
         bfsIterate(data.getEntranceX(), data.getEntranceY() + 1);
         setData(-1, -1);
         createMazeFile(data.maze);
    }

    /**********************************************************************
     * DFS recursive version
     * @param x
     * @param y
     */
    private void dfsRecurive(int x, int y) {
        if (!data.inArea(x, y)) {
            throw new IllegalArgumentException("Index x, y out of range!");
        }
        data.visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int newX = dirs[i][0] * 2 + x;
            int newY = dirs[i][1] * 2 + y;
            if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                setData(x + dirs[i][0], y + dirs[i][1]);
                dfsRecurive(newX, newY);
            }
        }

    }

    /**********************************************************************
     * DFS Iterative version
     * @param x
     * @param y
     */
    private void dfsIterative(int x, int y) {
        Stack<Position> stack = new Stack<>();
        Position first = new Position(x, y);
        stack.push(first);
        data.visited[x][y] = true;

        while (!stack.isEmpty()) {
            Position curpos = stack.pop();
            for (int i = 0; i < 4; i++) {
                int newX = curpos.getX() + dirs[i][0] * 2;
                int newY = curpos.getY() + dirs[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    stack.push(new Position(newX, newY));
                    setData(curpos.getX() + dirs[i][0], curpos.getY() + dirs[i][1]);
                }
            }
        }
    }


    /**
     *
     * @param x
     * @param y
     */
    private void bfsIterate(int x, int y) {
        // Use random queue
        RandomQueue<Position> queue = new RandomQueue<>();
        Position first = new Position(x, y);
        queue.add(first);
        data.visited[x][y] = true;
        data.setVisible(x, y);

        while (!queue.isEmpty()) {
            Position curpos = queue.remove();
            for (int i = 0; i < 4; i++) {
                int newX = curpos.getX() + dirs[i][0] * 2;
                int newY = curpos.getY() + dirs[i][1] * 2;
                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.setVisible(newX, newY);
                    setData(curpos.getX() + dirs[i][0], curpos.getY() + dirs[i][1]);
                }
            }
        }
    }


    private void createMazeFile(char[][] maze) {
        try {
            PrintWriter writer = new PrintWriter("maze_data.txt");
            int row = maze.length;
            int col = maze[0].length;
            StringBuilder sb = new StringBuilder();
            sb.append(row + " ");
            sb.append(col);
            sb.append("\n");

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    char c = maze[i][j];
                    sb.append(c);
                    if (j == col - 1) {
                        sb.append("\n");
                    }
                }
            }
            String maze_data = sb.toString();
            writer.print(maze_data);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setData(int x, int y) {
        if (data.inArea(x, y)) {
            data.maze[x][y] = MazeData.ROAD;
        }
        view.render(data);
        Utils.pause(DELAY);
    }

    public static void main(String[] args) {
        int row = 101;
        int col = 101;
        MazeController controller = new MazeController(row, col);
    }
}
