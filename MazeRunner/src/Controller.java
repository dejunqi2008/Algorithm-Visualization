import java.awt.*;
import java.util.LinkedList;
import java.util.Stack;

public class Controller {

    private static int blockSide = 8;
    private static int DELAY = 5;
    private static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;        // data
    private View frame;    // view

    public Controller(String mazeFile) {
        data = new MazeData(mazeFile);

        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new View("Maze visualization", sceneWidth, sceneHeight);
            new Thread(() -> {
//                mazeRunnerDFSRecursive();
//                mazeRunnerDFSIterative();
                mazeRunnerBFS();

            }).start();
        });
    }

    public void mazeRunnerDFSRecursive() {
        setData(-1, -1, false);

        if (!depthFirstSearch(data.getEntranceX(), data.getEntranceY())) {
            System.out.println("Could not find a path to exist for this maze!");
        }

        setData(-1, -1, false);
    }

    /**
     * Depth First Search iterative version
     */
    public void mazeRunnerDFSIterative() {
        setData(-1, -1, false);
        Stack<Position> stack = new Stack<>();

        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;

        while (!stack.isEmpty()) {

            Position curPos = stack.pop();

            int curX = curPos.getX();
            int curY = curPos.getY();
            setData(curX, curY, true);

            if (curX == data.getExitX() && curY == data.getExitY()) {
                isSolved = true;
                backTackForPath(curPos);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = dirs[i][0] + curX;
                int nextY = dirs[i][1] + curY;
                if (data.inArea(nextX, nextY) && data.getMaze(nextX, nextY) == MazeData.ROAD && !data.visited[nextX][nextY]) {
                    Position nextPos = new Position(nextX, nextY, curPos);
                    stack.push(nextPos);
                    data.visited[nextX][nextY] = true;
                }
            }
        }

        if (!isSolved) {
            System.out.println("Could not find path to exist for this maze!");
        }

        setData(-1, -1, false);

    }


    /**
     * Breath First Search finding path of maze
     */
    public void mazeRunnerBFS() {
        setData(-1, -1, false);

        LinkedList<Position> queue = new LinkedList<>();
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        queue.addLast(entrance);

        data.visited[entrance.getX()][entrance.getY()] = true;
        boolean isSolved = false;

        while (queue.size() != 0) {

            Position curPos = queue.pop();

            int curX = curPos.getX();
            int curY = curPos.getY();
            setData(curX, curY, true);

            if (curX == data.getExitX() && curY == data.getExitY()) {
                isSolved = true;
                backTackForPath(curPos);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = dirs[i][0] + curX;
                int nextY = dirs[i][1] + curY;
                if (data.inArea(nextX, nextY) && data.getMaze(nextX, nextY) == MazeData.ROAD && !data.visited[nextX][nextY]) {
                    Position nextPos = new Position(nextX, nextY, curPos);
                    queue.addLast(nextPos);
                    data.visited[nextX][nextY] = true;
                }
            }
        }

        if (!isSolved) {
            System.out.println("Could not find path to exist for this maze!");
        }

        setData(-1, -1, false);

    }


    // Recursive DFS walk through the maze
    private boolean depthFirstSearch(int x, int y) {
        if (!data.inArea(x, y)) {
            throw new IllegalArgumentException("x y out of index");
        }
        data.visited[x][y] = true;
        setData(x, y, true); // render color for the path

        if (x == data.getExitX() && y == data.getExitY()) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nextX = dirs[i][0] + x;
            int nextY = dirs[i][1] + y;
            if (data.inArea(nextX, nextY) && data.getMaze(nextX, nextY) == MazeData.ROAD && !data.visited[nextX][nextY]) {
                if (depthFirstSearch(nextX, nextY)) {
                    return true;
                }

            }
        }

        setData(x, y, false);
        return false;

    }


    private void backTackForPath(Position finalDes) {
        Position cur = finalDes;
        while (cur != null) {
            int curx = cur.getX();
            int cury = cur.getY();
            data.results[curx][cury] = true;
            cur = cur.getPrev();

        }
    }

    private void setData(int x, int y, boolean isPath) {
        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }
        frame.render(data);
        Utils.pause(DELAY);
    }


    public static void main(String[] args) {
        String mazeFile = "maze_data.txt";
        Controller vis = new Controller(mazeFile);
    }
}