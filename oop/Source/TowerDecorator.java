import java.awt.*;
import java.util.ArrayList;

public abstract class TowerDecorator extends Tower {
    protected Tower t;

    /**
     *
     * @param g Graphics
     */
    public abstract void paint(Graphics g);

    /**
     *
     * @param tower Tower
     */
    public TowerDecorator(Tower tower){
        this.t = tower;
    }

    /**
     *
     */
    public void step(){
        t.step();
    }

    /**
     *
     * @param ms ArrayList<Monster>
     */
    public void arrangeAttack(ArrayList<Monster> ms){
        t.arrangeAttack(ms);
    }

    /**
     *
     * @return int
     */
    public int getKillCount(){
        return t.getKillCount();
    }

    /**
     *
     * @return String
     */
    public String getStr(){
        return "|";}

    /**
     *
     * @return Vector2D
     */
    public Vector2D posCalc(){return t.posCalc();}
}
