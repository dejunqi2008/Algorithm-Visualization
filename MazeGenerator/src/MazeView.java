/**
 * View module of MVC architecture
 * Responsible for creating a Java Swing Frame and painting data
 */

import java.awt.*;
import javax.swing.*;


public class MazeView extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public MazeView(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        setResizable(false);
        pack();    // prevent resize
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public MazeView(String title){
        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: set your own data type
    private MazeData data;
    public void render(MazeData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // double cache for data visualization
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // ***************  painting  ***************************************
            // TODO： use your own data type
            int row = data.getRow();
            int col = data.getCol();
            int w = canvasWidth / col;
            int h = canvasHeight / row;

            for (int i = 0; i < row; i ++) {
                for (int j = 0; j < col; j++) {

                    if (!data.visible[i][j]) {
                        Utils.setColor(g2d, Utils.Black);
                    } else if (data.maze[i][j] == MazeData.WALL) {
                        Utils.setColor(g2d, Utils.LightBlue);
                    } else {
                        Utils.setColor(g2d, Utils.White);
                    }
                    Utils.fillRectangle(g2d, j * w, i * h, w, h);
                }
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}