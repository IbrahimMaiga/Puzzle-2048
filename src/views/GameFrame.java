package views;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;

/**
 * @uthor Kanfa.
 */

public class GameFrame extends JFrame implements ActionListener, Observer{

    private JPanel container;
    private JPanel content;
    private JPanel panel;
    private JPopupMenu popupMenu;
    private GamePanel gamePanel;
    private ScorePanel currentScorePanel;
    private ScorePanel bestScorePanel;
    private JButton menuBtn;
    private JButton restart;
    private Model model;
    private Serializer serializer;
    private boolean changed;
    private Data data;
    private float opacity = 0;

    public GameFrame(){
        this.setTitle("Puzzle 2048");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.serializer = new Serializer();
        this.getIfExist();
        this.model.addObserver("obs_frame", this);
        this.container = new JPanel(new BorderLayout());
        this.panel = new JPanel();
        this.gamePanel = new GamePanel(this.model);
        this.initializeScorePanel(this.model, false);
        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.add(this.panel, BorderLayout.NORTH);
        JLabel gameTitle = new JLabel("2048", JLabel.CENTER);
        JPanel gameTitlePanel = new JPanel();
        gameTitlePanel.add(gameTitle);
        gameTitle.setFont(new Font("Verdana", Font.BOLD, 30));
        panel4.add(gameTitlePanel, BorderLayout.WEST);
        this.menuBtn = new JButton();
        this.menuBtn.setBackground(Color.WHITE);
        this.menuBtn.setIcon(new ImageIcon(this.getClass().getResource("/icon/menu.png")));
        this.menuBtn.setPreferredSize(new Dimension(40, 40));
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menuPanel.add(menuBtn);
        panel4.add(menuPanel, BorderLayout.EAST);
        this.restart = new JButton("Restart");
        this.restart.setBackground(Color.RED);
        this.restart.setForeground(Color.WHITE);
        this.restart.setFocusPainted(false);
        this.restart.setBorderPainted(false);
        this.menuBtn.setFocusable(false);
        this.restart.setFocusable(false);

        this.popupMenu = this.createPopupMenu();

        this.menuBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                popupMenu.show(menuBtn, e.getX(), e.getY());
            }
        });

        this.restart.addActionListener(e -> {
            this.restartGame();
        });

        JPanel restartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        restartPanel.add(restart);
        this.content = new JPanel(new BorderLayout());
        this.content.add(panel4, BorderLayout.CENTER);
        this.content.add(restartPanel, BorderLayout.SOUTH);
        addComponents();
        this.setContentPane(this.container);
        this.pack();

        this.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                String current = model.getConfig().getConfigName();
                serializer.serialize(current, model.getData());
                saveCurrent(current);
            }
        });

        this.setVisible(true);
    }

    private void restartGame() {
        this.model.initialize();
        this.repaint();
    }

    private void getIfExist(){
        if (this.exists(this.getCurrent())){
            this.data = (Data) this.serializer.deserialize(this.getCurrent());
            this.model = new Model(data);
        }
        else{
            this.model = new Model();
            this.data = new Data
                    (
                            this.model.getConfig(),
                            this.model.getCells(),
                            this.model.getCurrentScore(),
                            this.model.getBestScore()
                    );
            this.model.setData(this.data);
        }
    }


    private boolean exists(String filename){
        File file = new File("resources/flux/" + filename);
        return file.exists() && file.isFile();
    }


    private void giveFocus() {
        this.menuBtn.transferFocus();
        this.restart.transferFocus();
    }

    private void addComponents() {
        this.container.add(this.content, BorderLayout.NORTH);
        this.container.add(gamePanel, BorderLayout.CENTER);
    }

    private void saveCurrent(String str){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("resources/flux/current.txt"));
            fileWriter.write(str);
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        finally {
            if (fileWriter != null) try {fileWriter.close();}
            catch (IOException e) {e.printStackTrace();}
        }
    }

    private String getCurrent(){
        FileReader fileReader = null;
        BufferedReader reader = null;
        String current=  "";
        try {
            fileReader = new FileReader(new File("resources/flux/current.txt"));
            reader = new BufferedReader(fileReader);
            current = reader.readLine();
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        finally {
            if (fileReader != null) try {fileReader.close();}
            catch (IOException e) {e.printStackTrace();}
            if (reader != null) try {fileReader.close();}
            catch (IOException e) {e.printStackTrace();}
        }
        return current;
    }

    private JPopupMenu createPopupMenu(){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem qxq = new JMenuItem("4x4");
        JMenuItem cxc = new JMenuItem("5x5");
        JMenu menu = new JMenu("Grille");
        menu.add(qxq);
        menu.addSeparator();
        menu.add(cxc);
        qxq.setActionCommand(qxq.getText());
        cxc.setActionCommand(cxc.getText());
        qxq.addActionListener(this);
        cxc.addActionListener(this);
        popupMenu.add(menu);
        return popupMenu;
    }

    private void initializeScorePanel(Model model, boolean removeAll){
            this.currentScorePanel = new CurrentScorePanel(model, new Color(128, 128, 128), Color.RED);
            this.bestScorePanel = new BestScorePanel(model, new Color(128, 128, 128), Color.BLACK);
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JPanel panel2 = new JPanel();
            panel1.add(currentScorePanel);
            panel2.add(bestScorePanel);
            if (removeAll) this.panel.removeAll();
            this.panel.add(panel2);
            this.panel.add(panel1);
            if (removeAll){
                this.panel.revalidate();
            }
            model.notifyObserver(bestScorePanel, model);
            model.notifyObserver(currentScorePanel, model);
    }

    public Model getModel(){
        return this.model;
    }

    @Override public void actionPerformed(ActionEvent e) {
        this.changed = true;
        if (this.opacity != 0){
            this.opacity = 0;
            repaint();
        }
        model.loadConfig(e.getActionCommand(), serializer);
    }

    @Override public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.opacity));
        g.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
        String txt = this.model.isOver() ? Text.getGameOverText() : this.model.isWin() ? Text.getWinText() : "";
        boolean canDraw = this.model.isOver() || this.model.isWin();
        if (canDraw){
            graphics2D.setFont(new Font("SansSerif", Font.BOLD, 14));
            graphics2D.setColor(Color.BLACK);
            Rectangle2D bound = graphics2D.getFont().getStringBounds(txt, graphics2D.getFontRenderContext());
            graphics2D.drawString(txt, (this.getPreferredSize().width / 2 - (int)bound.getWidth() / 2),
                                  (this.getPreferredSize().height / 2 - (int)bound.getHeight() / 2));
        }
    }

    @Override public void update(Object o) {
        (new Worker((Model)o)).execute();
    }

    public final class Worker extends SwingWorker<Void, Void>{
        private Model m;
        private KeyboardFocusManager manager;
        private DisPatcher disPatcher;
        public Worker(Model m){
            this.m = m;
            this.manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            this.disPatcher = new DisPatcher(this.m);
        }

        @Override protected Void doInBackground() throws Exception {
            serializer.serialize(model.getConfig().getConfigName(), model.getData());
            saveCurrent(model.getConfig().getConfigName());
            return null;
        }

        @Override protected void done() {
            if (m.isOver() || m.isWin()){
                this.manager.addKeyEventDispatcher(this.disPatcher);
                opacity = 0.8f;
                GameFrame.this.repaint();
            }
            m.addObserver("obs_frame", GameFrame.this);

            gamePanel = new GamePanel(m);
            initializeScorePanel(m, true);
            container.removeAll();
            addComponents();
            container.revalidate();
            giveFocus();

            changed = !changed;
            model = this.m;
        }
    }

    public final class DisPatcher implements KeyEventDispatcher {

        private Model model;
        public DisPatcher(Model model){
            this.model = model;
        }
        @Override public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED){
                if (model.isOver() || model.isWin()){
                    GameFrame.this.opacity = 0;
                    if (model.isOver()){
                        if (e.getKeyCode() == KeyEvent.VK_SPACE){
                            GameFrame.this.restartGame();
                            model.setOver(false);
                        }
                    }
                    else {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            model.setWin(false);
                        }
                    }
                    GameFrame.this.repaint();
                }
            }
            return false;
        }
    }
}