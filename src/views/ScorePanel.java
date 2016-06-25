package views;

import model.Animated;
import model.Model;
import model.Observer;
import model.Score;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @uthor Kanfa.
 */
public abstract class ScorePanel extends JPanel implements Observer{

    private int width;
    private int height;
    private int middle_height;
    private int increment;

    protected Score score;
    protected Model model;
    protected Boolean start;
    protected Color background;
    protected Color fontColor;
    private Score last;

    public ScorePanel(Model model, Color background, Color fontColor){
        this.last = new Score();
        this.setModel(model);
        this.background = background;
        this.fontColor = fontColor;
        this.score = new Score();
        this.start = Boolean.FALSE;
        this.width = 150;
        this.height = 30;
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    public void paint(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D)graphics.create();
        graphics2D.setColor(this.background);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.fillRoundRect(0, 0, this.width, this.height, 10, 10);
        graphics2D.setColor(this.fontColor);
        String str = String.valueOf(this.score.getValue());
        String current = String.valueOf(this.score.getCurrent());
        Rectangle2D r = graphics2D
                .getFont()
                .getStringBounds(str, ((Graphics2D) graphics).getFontRenderContext());
        this.middle_height = (this.height / 2) + ((int)r.getHeight() / 2);
        graphics2D.drawString(str, (this.width / 2 - (int)r.getWidth()/2),
                              (this.height / 2) + ((int)r.getHeight() / 2));

        if (this.start && this.score.getCurrent() != 0) {
            if (this instanceof Animated) graphics2D.setColor(Animated.animatedFontColor());
            graphics2D.drawString(("+" + current), 30, this.middle_height - increment);
        }
        graphics2D.dispose();
    }

    public Model getModel(){
        return this.model;
    }

    public void setModel(Model model) {
        if (model != null){
            this.model = model;
            this.model.addObserver(this.getObserverName(), this);
            this.model.notifyObserver(this);
        }
    }

    @Override public void update(Object o) {
        this.model = (Model)o;
        if (this.getClass().getSimpleName().toLowerCase().startsWith("current"))
            this.score = this.model.getCurrentScore();
        else
            this.score = this.model.getBestScore();

        if (this.last.getValue() != this.score.getValue()){
            this.start = this.run();
            this.repaint();
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                while (this.middle_height - increment >= 0){
                    increment++;
                    this.repaint();
                    try {Thread.sleep(50);}
                    catch (InterruptedException e) {e.printStackTrace();}
                }
                this.start = false;
                this.increment = 0;
            });

            service.shutdown();
            this.last.setValue(this.score.getValue());
        }
    }

    public abstract String getObserverName();
    public abstract boolean run();
}
