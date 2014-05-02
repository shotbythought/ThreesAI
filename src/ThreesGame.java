import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

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
        g.setFont(new Font("Helvetica",Font.BOLD, 30));
        for(int i = 0; i<4; ++i)
        {
            for(int j = 0; j<4; ++j)
            {
                int value = sim.getThreesGrid().get(i).get(j);
                g.setColor(getTileColor(value));
                g.fillRect(BOX_SIZE*j,BOX_SIZE*i,BOX_SIZE,BOX_SIZE);
                g.setColor(Color.BLACK);
                if (value != 0) {
                    g.drawString(value + "", BOX_SIZE * j + BOX_SIZE / 2 - 15, BOX_SIZE * i + BOX_SIZE / 2);
                }
            }
        }
        //nextTile
        int value = sim.getNextTile();
        g.setColor(getTileColor(value));
        g.fillRect(BOX_SIZE*4+10,0,BOX_SIZE,BOX_SIZE);
        g.setColor(Color.BLACK);
        g.drawString(value+"",WIDTH-BOX_SIZE+BOX_SIZE/2,BOX_SIZE/2);
        g.drawString(sim.getScore()+"",WIDTH-BOX_SIZE+BOX_SIZE/2,BOX_SIZE*3/2);
        if(!sim.isAlive())
        {
            g.drawString("YOU LOST",WIDTH/2,HEIGHT/2);
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
            case KeyEvent.VK_R:
                sim = new Simulator();
                break;
        }
        repaint();
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