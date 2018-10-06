import java.awt.*;

public class MazeController {

    private static int blockSide = 8;
    private static int DELAY = 5;
    private MazeData data;
    private MazeView view;


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

    private void generate() {
         setData();
    }

    private void setData() {
        view.render(data);
        Utils.pause(DELAY);
    }

    public static void main(String[] args) {
        int row = 101;
        int col = 101;
        MazeController controller = new MazeController(row, col);
    }
}
