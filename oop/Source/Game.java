import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {


    private static final Game _inst = new Game();
    private static final ArrayList<Monster> monsters = new ArrayList<Monster>();
    private static final ArrayList<Tower> towers = new ArrayList<Tower>();
    private static ITowerFactory factory;
    private static final ArrayList<Integer> towerDP = new ArrayList<Integer>();
    private Graphics g;
    private int Gold = 25;
    private int Kills = 0;
    private int Wave = 0;
    private int Lives = 3;
    private Timer T;
    private GamePanel gPanel;

    /**
     *
     * @return Game
     */
    public static Game getInstance() {
        return _inst;
    }

    /**
     *
     */
    public Game() {
    }

    /**
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        //TODO
        for (Monster monster : monsters) {
            monster.paint(g);
        }
        for (Tower tower : towers) {
            tower.paint(g);
        }
    }

    /**
     *
     */
    public void newWave() {
        this.updateWave();
        this.generateMonsters();
        //start steps
        T = new Timer(5, actionEvent -> {
            Game.getInstance().step();
        });
       T.start();


    }

    /**
     *
     */
    private void calculateAttacks() {
        for(Tower t : towers) {
            t.arrangeAttack(monsters);
        }
    }

    /**
     *
     */
    public void step() {
        //TODO
        calculateAttacks();
        ArrayList<Monster> deletedMonsters = new ArrayList<Monster>();
        if (monsters.size() > 0) {
            for (Monster m : monsters) {
                m.step();
                if(m.getIsDead()){
                    if(deletedMonsters.contains(m)){
                        continue;
                    }else {
                        this.updateKills(1);
                        deletedMonsters.add(m);
                        continue;
                    }
                }

                if(m.getIsDone()){
                    if(deletedMonsters.contains(m)){
                    }else {
                        deletedMonsters.add(m);
                    }
                }

            }
        }
        for(Monster m : deletedMonsters){
            if (m.getIsDead()){
                updateGold(10);
            } else {
                updateLives();
            }
            monsters.remove(m);
        }

        for (Tower tower : towers) {
            tower.step();
        }

        if(Lives > 0 &&  monsters.size() == 0) {
            T.stop();
            monsters.clear();
            newWave();
        }

        if (Lives <= 0) {
            monsters.clear();
        }

        Display.getInstance().getGamePanel().repaint();

        for(Tower t : towers){
            if (t.getKillCount() > 50){
                towers.set(towers.indexOf(t), new TowerDecoratorGrade3(t));
            } else if (t.getKillCount() > 25){
                towers.set(towers.indexOf(t) ,new TowerDecoratorGrade2(t));
            } else if (t.getKillCount() > 10){
                towers.set(towers.indexOf(t) ,new TowerDecoratorGrade1(t));
            }
        }

    }

    /**
     *
     */
    private void generateMonsters(){
        for(int i = 0; i< this.Wave; i++){
            Monster newMonster = new Monster(g, Wave);
            monsters.add(newMonster);
        }
        monsters.sort((o1, o2) -> o1.compareMagnitude(o2.getDirectionMagnitude()));
    }

    /**
     *
     */
    private void towerListener() {
        this.gPanel = Display.getInstance().getGamePanel();
        gPanel.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        addTower(e);
                    }
                }
        );
    }

    /**
     *
     * @param e KeyEvent
     */
    private void addTower(KeyEvent e) {
        Point mouseLoc = gPanel.getMousePosition();
        int indX = ((int)mouseLoc.getX() - Commons.TowerZoneX) / Commons.TowerZoneDivideLength;
        int indY = ((int)mouseLoc.getY() - Commons.TowerZoneY) / Commons.TowerZoneDivideLength;
        int dpInd = indX + indY * 4;
        if(towerDP.get(dpInd) == 1){
           return;
        }

        if(indX > 4 || indY > 4 || mouseLoc.getX() < 80 || mouseLoc.getX() > 320 || mouseLoc.getY() < 80 || mouseLoc.getX() > 320) {
            return;
        }
        if(e.getKeyCode() == 87){
            if(this.Gold < 25) {
                return;
            }
            updateGold(-25);
            factory = new TowerPoisonFactory();
        } else if (e.getKeyCode() == 69) {
            if(this.Gold < 15){
                return;
            }
            updateGold(-15);
            factory = new TowerIceFactory();
        } else if (e.getKeyCode() == 81 ) {
            if(this.Gold < 20) {
                return;
            }
            updateGold(-20);
            factory = new TowerRegularFactory();
        }
        towerDP.set(dpInd, 1);
        towers.add(factory.createTower(new Vector2D(indX, indY)));
    }

    //You can make changes

    /**
     *
     */
    public static void startGame() {
        Display.getInstance().setVisible(true);
        Game.getInstance().initDP();
        Game.getInstance().newWave();
        Game.getInstance().towerListener();

    }

    /**
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::startGame);
    }

    /**
     *
     * @param gold int
     */
    private void updateGold(int gold) {
        this.Gold += gold;
        InfoPanel panel = Display.getInstance().getInfoPanel();
        panel.updateGold(this.Gold);
    }

    /**
     *
     */
    private void updateLives() {
        this.Lives--;
        InfoPanel panel = Display.getInstance().getInfoPanel();
        panel.updateLive(this.Lives);
    }

    /**
     *
     * @param killCount int
     */
    private void updateKills(int killCount) {
        this.Kills += killCount;
        InfoPanel panel = Display.getInstance().getInfoPanel();
        panel.updateKills(this.Kills);
    }

    /**
     *
     */
    private void updateWave() {
        this.Wave++;
        InfoPanel panel = Display.getInstance().getInfoPanel();
        panel.updateWave(this.Wave);
    }

    /**
     *
     */
    private void initDP(){
        for(int i = 0; i < 16; i++) {
            towerDP.add(0);
        }
    }
}

