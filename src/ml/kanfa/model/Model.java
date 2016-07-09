package ml.kanfa.model;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @uthor Kanfa.
 */
public class Model implements Observable, IName{

    private static final String PACKAGE = "ml.kanfa";
    private static final String DIR = "resources/flux/";
    private static final String CONFIG = "config_";
    private static final int GENERATION_LENGTH = 2;
    private static final long WAIT_TIME = 200;

    private Vector<Observer> observers   = new Vector<>();
    private Map<String, Observer> m_observers = new HashMap<>();
    private ArrayList<Cell> cells;
    private final Cell cell = new Cell();
    private Score currentScore;
    private Score bestScore;
    private PlatformConfig config;
    private Data data;
    private boolean isOver;
    private boolean win;
    private boolean repaint;

    public Model(PlatformConfig config){
        this.config = config;
        this.currentScore = new Score();
        this.bestScore = new Score();
        this.cells = new ArrayList<>();
        this.data = new Data(this.config, this.cells, this.currentScore, this.bestScore);
        this.execute();
    }

    public Model(Data data){
        this.data = data;
        this.setData(data);
    }

    public Model(){
        this(new Config_4x4());
    }

    private void setup(ArrayList<Cell> cells){
        for (int i = 0; i < this.config.getSide(); i++){
            for (int j = 0; j < this.config.getSide(); j++){
                cells.add(clone(cell, i, j));
            }
        }
    }

    private Cell clone(final Cell cell, int i, int j){
        Cell c = null;
        try {
            c = cell.clone();
            c.set(j, i);
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void initialize(){
        this.isOver = false;
        this.win = false;
        this.repaint = true;
        this.execute();
        this.currentScore.setValue(0);
        this.currentScore.setCurrent(0);
        this.data.setCells(this.cells);
        this.data.setWin(false);
        this.notifyObservers(this, OBS_FRAME);
    }

    private void execute(){
        this.cells = new ArrayList<>();
        this.setup(this.cells);
        (new Thread(() -> {
        for (int i = 0; i < GENERATION_LENGTH; i++){
                this.generate();
                try {Thread.sleep(WAIT_TIME);}
                catch (InterruptedException e) {}
                this.notifyObserver(OBS_GAME, this);
            }
        })).start();
    }

    private ArrayList<Cell> emptyCell(){
        ArrayList<Cell> cells = new ArrayList<>();
        cells.addAll(this.cells.stream().filter(c -> c.getValue() == 0).collect(Collectors.toList()));
        return cells;
    }

    private void generate(){
        ArrayList<Cell> array = emptyCell();
        if (array.size() > 0){
            this.generate(array);
        }
    }

    private void generate(final ArrayList<Cell> cells){
        final Random rand = new Random();
        Cell c = cells.get(rand.nextInt(cells.size()));
        this.cells.stream().filter(cell -> (cell.getPosX() == c.getPosX() &&
                cell.getPosY() == c.getPosY())).forEach(
                cell -> cell.setValue(Generator.generate())
        );
    }

    public void move(final Direction d) {
        boolean b = false;
        if (!this.win){
            switch (d){
                case UP: moveUp(); break;
                case DOWN: moveDown(); break;
                case LEFT: moveLeft(); break;
                case RIGHT: moveRight(); break;
                default:
            }
            this.generate();
            this.notifyObservers(this, OBS_FRAME);
            this.win = this.check(this.cells);
            if (this.win){
                this.data.setWin(true);
                this.notifyWin();
                b = true;
            }
        }
        if (b){
            this.notifyWin();
        }
    }

    private boolean check(final ArrayList<Cell> cells){
        boolean b = false;
        int count = 0;
        for (Cell cell : cells){
            if (cell.getValue() == 2048){
                b =  true;
                if (count <= 1) count++;
            }
        }

        return (b && (count == 1)) && !this.data.isWin();
    }

    private void moveUp(){
        moveLeft(this.reverseArray());
    }

    private void moveDown(){
        this.moveRight(this.reverseArray());
    }

    private void moveLeft(){
        this.moveLeft(this.toArray());
    }

    private void moveLeft(final ArrayList<ArrayList<Cell>> arrays){
        boolean added = false;
        for (ArrayList<Cell> arrayList : arrays){
            arrayList = adjustLeft(arrayList);
            for (int i = 0; i < arrayList.size() - 1; i++){
                if (arrayList.get(i).same(arrayList.get(i + 1)) && !arrayList.get(i).isEmpty()){
                    arrayList.get(i).add(arrayList.get(i + 1));
                    this.currentScore.addValue(arrayList.get(i).getValue());
                    added = true;
                }
                arrayList = adjustLeft(arrayList);
            }
        }
        this.checkAndAdd(added);
    }

    private void checkAndAdd(boolean added) {
        if (added){
            if (this.currentScore.greaterThan(this.bestScore)){
                this.bestScore.setValue(this.currentScore.getValue());
                this.bestScore.setCurrent(this.currentScore.getCurrent());
            }
        }
    }

    private void moveRight(){
        this.moveRight(this.toArray());
    }

    private void moveRight(final ArrayList<ArrayList<Cell>> arrays){
        boolean added = false;
        for (ArrayList<Cell> arrayList : arrays){
            arrayList = adjustRight(arrayList);
            for (int i = arrayList.size() - 2; i >= 0; i--){
                if (arrayList.get(i).same(arrayList.get(i + 1)) && !arrayList.get(i).isEmpty()){
                    arrayList.get(i + 1).add(arrayList.get(i));
                    this.currentScore.addValue(arrayList.get(i + 1).getValue());
                    added = true;
                }
                arrayList = adjustRight(arrayList);
            }
        }
        this.checkAndAdd(added);
    }

    private ArrayList<Cell> adjustLeft(ArrayList<Cell> cell){
        return adjust(cell, 1, 0);
    }

    private ArrayList<Cell> adjustRight(ArrayList<Cell> cell){
        return adjust(cell, -1, cell.size() - 1);
    }

    private ArrayList<Cell> adjust(final ArrayList<Cell> cell, int d, int index){
        if (index < 0 || index >= cell.size() ||
                searchIndex(cell, d, index) == - 1 ||
                searchEmpty(cell, d, index) == -1){
            return cell;
        }
        else{
            int a = searchIndex(cell, d, searchEmpty(cell, d, index));
            if (a > -1){
                cell.get(searchEmpty(cell, d, index)).add(cell.get(searchIndex(cell, d, a)));
            }
            return adjust(cell, d, index + d);
        }
    }

    public void load(final String command, final Serializer serializer) {
        if (!this.config.getConfigName().equals(command)){
            String filename = CONFIG + command;
            PlatformConfig c;
            CellConfig cellConfig;
            Model model = null;
            Data data;
            if ((new File(DIR + command)).exists() ) {
                this.data.setBestScore(this.bestScore);
                this.data.setCurrentScore(this.currentScore);
                data = (Data) serializer.deserialize(command);
                model = new Model(data);
            }
            else {
                try {
                    String className = Character.toString(filename.charAt(0)).toUpperCase().concat(filename.substring(1));
                    Class cellConfigClass = Class.forName(PACKAGE + ".model.Cell" + className);
                    Class configClass = Class.forName(PACKAGE + ".model." + className);
                    c = (PlatformConfig) configClass.newInstance();
                    cellConfig = (CellConfig)cellConfigClass.newInstance();
                    this.config = c;
                    model = this.getInstance(c, cellConfig);
                }
                catch (ClassNotFoundException e) {e.printStackTrace();}
                catch (InstantiationException e) {e.printStackTrace();}
                catch (IllegalAccessException e) {e.printStackTrace();}
            }
            this.notifyObserver(OBS_FRAME, model);
        }
    }

    private Model getInstance(final PlatformConfig c, final CellConfig cellConfig){
        Model model = new Model(c);
        for (Cell cell : model.getCells()){
            cell.setConfig(cellConfig);
        }
        this.cells = model.getCells();
        return model;
    }

    private int searchEmpty(ArrayList<Cell> cell, int d, int index){
        return CellUtils.searchEmpty(cell, d, index);
    }

    private int searchIndex(ArrayList<Cell> cell, int d, int index) {
        return CellUtils.searchIndex(cell, d, index);
    }

    private ArrayList<ArrayList<Cell>> toArray(){
        return CellUtils.toArray(this.cells);
    }

    private ArrayList<ArrayList<Cell>> reverseArray(){
        return CellUtils.reverseArray(this.cells);
    }

    public PlatformConfig getConfig(){
        return this.config;
    }

    public void setConfig(PlatformConfig config){
        this.config = config;
    }

    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    public void setCells(ArrayList<Cell> cells){
        this.cells = cells;
    }
    public Score getCurrentScore(){
        return this.currentScore;
    }

    public void setCurrentScore(Score currentScore){
        if (this.currentScore == null) this.currentScore = new Score();
        this.currentScore.setValue(currentScore.getValue());
    }

    public Score getBestScore(){
        return this.bestScore;
    }

    public void setBestScore(Score bestScore){
        if (this.bestScore == null) this.bestScore = new Score();
        this.bestScore.setValue(bestScore.getValue());
    }

    public boolean isOver(){
        return this.isOver;
    }

    public void notifyGameOver() {
        this.isOver = true;
        this.notifyObserver(OBS_FRAME, this);
    }

    public void setData(Data data) {
        this.setConfig(data.getConfig());
        this.setCells(data.getCells());
        this.setCurrentScore(data.getCurrentScore());
        this.setBestScore(data.getBestScore());
    }

    public Data getData(){
        this.data.getCurrentScore().setValue(this.currentScore.getValue());
        this.data.getBestScore().setValue(this.bestScore.getValue());
        this.data.setCells(this.cells);
        return this.data;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win){
        this.win = win;
    }

    public boolean canRepaint(){
        return this.repaint;
    }

    public void setRepaint(boolean repaint){
        this.repaint = repaint;
    }

    @Override public void addObserver(Observer o) {
        this.addObserver(o.getClass().getName(), o);
    }

    @Override public void addObserver(String name, Observer o) {
        if (o != null){
            this.observers.add(o);
            this.m_observers.putIfAbsent(name, o);
        }
    }

    @Override public void removeObserver(Observer o) {
        this.removeObserver(o.getClass().getName());
    }

    @Override public void removeObserver(String name) {
        if (this.m_observers.containsKey(name)){
            this.observers.remove(this.m_observers.get(name));
            this.m_observers.remove(name);
        }
    }

    @Override public void notifyObserver(Observer observer, Object o) {
        observer.update(o);
    }

    public void notifyObserver(Observer observer) {
        observer.update(this);
    }

    @Override public void notifyObserver(String name, Object o) {
        if (this.m_observers.containsKey(name)){
            this.m_observers.get(name).update(o);
        }
    }

    @Override public void notifyObservers(Object o) {
        for (Observer observer : this.observers){
            observer.update(o);
        }
    }

    public void notifyObservers(Object o, String... ignores) {
        this.m_observers.keySet().stream().filter(key -> !Arrays.asList(ignores).contains(key)).forEach(key -> m_observers.get(key).update(o));
    }

    public void notifyWin(){
        this.notifyObserver(OBS_FRAME, this);
    }

}