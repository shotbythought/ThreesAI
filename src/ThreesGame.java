import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class ThreesGame extends Canvas {

    public final int WIDTH = 500;
    public final int HEIGHT = 500;
    public final int BOX_SIZE = 100;

    public static final String TITLE = "Threes";

    Simulator sim;
    boolean running = true;

    public ThreesGame() {
        sim = new Simulator();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                moveIt(evt);
            }
        });
    }

    public void paint(Graphics g) {
        for(int i = 0; i<4; ++i)
        {
            for(int j = 0; j<4; ++j)
            {
                int value = sim.getThreesGrid().get(i).get(j);
                Color color;
                if (value == 0) {
                    color = Color.WHITE;
                } else if (value == 1) {
                    color = Color.BLUE;
                } else if (value == 2) {
                    color = Color.RED;
                } else {
                    color = Color.YELLOW;
                }
                g.setColor(color);
                g.fillRect(BOX_SIZE*j,BOX_SIZE*i,BOX_SIZE,BOX_SIZE);
                g.setColor(Color.BLACK);
                g.drawString(value+"",BOX_SIZE*j+BOX_SIZE/2,BOX_SIZE*i+BOX_SIZE/2);
            }
        }
        int value = sim.getNextTile();
        Color color;
        if (value == 0) {
            color = Color.WHITE;
        } else if (value == 1) {
            color = Color.BLUE;
        } else if (value == 2) {
            color = Color.RED;
        } else {
            color = Color.YELLOW;
        }
        g.setColor(color);
        g.fillRect(BOX_SIZE*4+10,0,BOX_SIZE,BOX_SIZE);
        g.setColor(Color.BLACK);
        g.drawString(value+"",WIDTH-BOX_SIZE+BOX_SIZE/2,BOX_SIZE/2);

        if(!running)
        {
            g.drawString("YOU LOST",WIDTH/2,HEIGHT/2);
        }
    }

    public void moveIt(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                sim.moveDown();
                break;
            case KeyEvent.VK_UP:
                sim.moveUp();
                break;
            case KeyEvent.VK_LEFT:
                sim.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                sim.moveRight();
                break;
        }
        repaint();
        if(!sim.isAlive())
        {
            running = false;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        ThreesGame gm = new ThreesGame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gm);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        gm.requestFocus();
    }
}