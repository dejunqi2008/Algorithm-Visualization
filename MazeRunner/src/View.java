/**
 * View module of MVC architecture
 * Responsible for creating a Java Swing Frame and painting data
 */

import java.awt.*;
import javax.swing.*;


public class View extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public View(String title, int canvasWidth, int canvasHeight){

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

    public View(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}


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
            int w = canvasWidth / data.N();
            int h = canvasHeight / data.M();

            for (int i = 0; i < data.N(); i ++) {
                for (int j = 0; j < data.M(); j++) {
                    if (data.getMaze(i, j) == MazeData.WALL) {
                        Utils.setColor(g2d, Utils.LightBlue);
                    } else {
                        Utils.setColor(g2d, Utils.White);
                    }

                    if (data.path[i][j]) {
                        Utils.setColor(g2d, Utils.Green);
                    }

                    if (data.results[i][j]) {
                        Utils.setColor(g2d, Utils.Purple);
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
