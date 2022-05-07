import java.awt.*;
import java.util.ArrayList;

public abstract class Tower extends Entity{
    private Vector2D positon;
    private boolean willAttack = false;
    private Monster targetMonster = null;
    private int lastAttackStep = 0;
    private int range = 150;
    private int fireRate;
    private int killCount = 0;
//    int

    /**
     *
     * @param pos Vector2D
     */
    public void Tower(Vector2D pos) {
        this.positon = pos;
    }

    /**
     *
     * @return Vector2D
     */
    public Vector2D posCalc(){
        int posX = positon.getIntX()*Commons.TowerZoneDivideLength + Commons.TowerZoneX;
        int posY = positon.getIntY()*Commons.TowerZoneDivideLength + Commons.TowerZoneY;

        return new Vector2D(posX, posY);
    }

    /**
     *
     * @param m Monster
     */
    public void setTargetMonster(Monster m) {
        targetMonster = m;
    }

    /**
     *
     * @return Monster
     */
    public Monster getTargetMonster(){
        return targetMonster;
    }

    /**
     *
     * @param x boolean
     */
    public void setWillAttack(boolean x){
        this.willAttack = x;
    }

    /**
     *
     * @return boolean
     */
    public boolean getWillAttack(){return this.willAttack;}

    /**
     *
     * @param isAttackDone boolean
     */
    public void changeLastAttackStep(boolean isAttackDone) {
        if (isAttackDone) {
            lastAttackStep = 0;
        } else {
            lastAttackStep++;
        }
    }

    /**
     *
     * @return int
     */
    public int getLastAttackStep() {return this.lastAttackStep;}

    public void setLastAttackStep(int val) {
        this.lastAttackStep = val;
        if(this.lastAttackStep < 0){
            this.lastAttackStep = 0;
        }
    }

    /**
     *
     * @param monsters ArrayList<Monster>
     */
    public void arrangeAttack(ArrayList<Monster> monsters) {
        targetMonster = null;
        for(Monster m : monsters){
            if(m.getIsDead() && m.getIsDone()){
                continue;
            }
            Vector2D mPos = m.getPos();
            int x = Commons.TowerZoneX + positon.getIntX() * Commons.TowerSize + 20;
            int y = Commons.TowerZoneY + positon.getIntY() * Commons.TowerSize + 20;
            if(mPos.getIntX() > (x - range) && (mPos.getIntX() < (x + range)) &&  (mPos.getIntY() > (y- range)) && (mPos.getIntY() < (y+ range))){
                this.targetMonster = m;
                break;
            }
        }
    }

    /**
     *
     * @param val int
     */
    public void setRange(int val) {
        this.range = val;
    }

    /**
     *
     * @return int
     */
    public int getRange() {
        return range;
    }

    /**
     *
     * @param val int
     */
    public void setFireRate(int val) {
        this.fireRate = val;
    }

    /**
     *
     * @return int
     */
    public int getFireRate(){return fireRate;}

    /**
     *
     * @return int
     */
    public int getKillCount(){return killCount;}

    /**
     *
     */
    public void increaseKillCount(){this.killCount++;}
}
