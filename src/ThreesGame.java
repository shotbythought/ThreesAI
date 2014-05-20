import java.awt.*;
import java.awt.event.*;
//import java.awt.image.BufferStrategy;
import javax.swing.*;

public class ThreesGame extends Canvas {

    public final int WIDTH = 500;
    public final int HEIGHT = 500;
    public final int BOX_SIZE = 100;

    public static final String TITLE = "Threes";

    Simulator sim;

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
        g.setFont(new Font("TimesNewRoman",Font.BOLD, 30));
        for(int i = 0; i<4; ++i)
        {
            for(int j = 0; j<4; ++j)
            {
                int value = sim.getCurrent().getThreesGrid().get(i).get(j);
                g.setColor(getTileColor(value));
                g.fillRect(BOX_SIZE*j,BOX_SIZE*i,BOX_SIZE,BOX_SIZE);
                g.setColor(Color.BLACK);
                if (value != 0) {
                    g.drawString(value + "", BOX_SIZE * j + BOX_SIZE / 2 - 15, BOX_SIZE * i + BOX_SIZE / 2);
                }
            }
        }
        //nextTile
        int value = sim.getCurrent().getNextTile();
        g.setColor(getTileColor(value));
        g.fillRect(BOX_SIZE*4+10,0,BOX_SIZE,BOX_SIZE);
        g.setColor(Color.BLACK);
        g.drawString(value+"",WIDTH-BOX_SIZE+BOX_SIZE/2,BOX_SIZE/2);
        g.drawString("Score:",WIDTH-BOX_SIZE+10,BOX_SIZE*3/2);
        g.drawString(sim.getCurrent().getScore()+"",WIDTH-BOX_SIZE+BOX_SIZE/3,BOX_SIZE*2);
        if(!sim.getCurrent().isAlive())
        {
            g.drawString("YOU LOST",WIDTH-300,HEIGHT-50);
        }
    }

    private Color getTileColor(int value)
    {
        if (value == 0) {
            return new Color(182,217,217);
        } else if (value == 1) {
            return new Color(102,204,255);
        } else if (value == 2) {
            return new Color(255,102,128);
        } else {
            return Color.WHITE;
        }
    }

    public void moveIt(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                sim.move('d');
                break;
            case KeyEvent.VK_UP: case KeyEvent.VK_W:
                sim.move('u');
                break;
            case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
                sim.move('l');
                break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                sim.move('r');
                break;
            case KeyEvent.VK_R:
                sim = new Simulator();
                break;
            case KeyEvent.VK_Z:
                sim.undo();
                break;
            case KeyEvent.VK_I:
                sim.artificialMove();
                break;
            case KeyEvent.VK_U:
                sim.runArtificial();
                break;
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        ThreesGame gm = new ThreesGame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gm);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        gm.requestFocus();
    }
}