package ml.kanfa.ui;

import ml.kanfa.controller.Controller;
import ml.kanfa.engine.*;
import ml.kanfa.engine.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.*;

/**
 * @uthor Ibrahim Maïga.
 */

public class GamePanel extends JPanel implements KeyListener, Observer, IName{

    private ArrayList<Cell> cells;
    private Model model;
    private int getBlocLength;
    private static final int ARCX = 10;
    private static final int ARCY = 10;
    private static final int SPACE = 4;
    private Controller controller;
    private PlatformConfig config;

    public GamePanel(Model model){
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(400, 400));
        this.setModel(model);
    }

    private void initModel(){
        this.model.addObserver(OBS_GAME, this);
        this.controller = new Controller(this.model);
        this.config = this.model.getConfig();
        this.getBlocLength = this.config.getBlocLength();
        this.cells = this.model.getCells();
    }



    @Override public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(this.config.getBackground());
        graphics2D.fillRoundRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height, ARCX, ARCY);
        double scaleX = (this.getPreferredSize().width - ((getBlocLength - 1) * this.config.getSide())) / 2.0;
        double scaleY = (this.getPreferredSize().height - ((getBlocLength - 1) * this.config.getSide())) / 2.0;

        for (final Cell cell : this.cells){
            int x = cell.getPosX() * getBlocLength;
            int y = cell.getPosY() * getBlocLength;
            graphics2D.setFont(new Font("Arial", Font.PLAIN, 24));
            graphics2D.setColor(cell.getBackground());
            graphics2D.fill(new RoundRectangle2D.Double(x + scaleX, y + scaleY, getBlocLength - SPACE, getBlocLength - SPACE, ARCX, ARCY));
            graphics2D.setColor(cell.getForeground());
            graphics2D.setFont(cell.getFont());
            String str = cell.getValue() == 0 ? "" : String.valueOf(cell.getValue());
            Rectangle2D r = graphics2D
                            .getFont()
                            .getStringBounds(str, ((Graphics2D) g).getFontRenderContext());
            graphics2D.drawString(str, (x + (getBlocLength / 2)) - ((int)r.getWidth()/2),
                    (y + (getBlocLength / 2)) + ((int)r.getHeight() / 2));
        }
    }

    public void setModel(Model model) {
        this.model = model;
        this.initModel();
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override public void keyPressed(KeyEvent e) {}

    @Override public void keyReleased(KeyEvent e) {
       this.controller.control(e.getKeyCode());
    }

    @Override public void update(Object o) {
        this.model = (Model) o;
        this.cells = this.model.getCells();
        this.repaint();
    }
}
