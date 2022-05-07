import java.awt.*;

public class TowerPoison extends Tower{


    /**
     *
     * @param position Vector2D
     */
    public TowerPoison(Vector2D position) {
        super.Tower(position);
        super.setRange(75);
    }

    /**
     *
     */
    private void attack() {
        if(getTargetMonster() != null) {
            Monster m = getTargetMonster();
            boolean isKilled = m.takePoisonDamage();
            if(isKilled){
                increaseKillCount();
            }
        }
    }

    /**
     *
     */
    @Override
    public void step() {
        if(getLastAttackStep() == 0) {
            attack();
            setLastAttackStep(10);
        }
        setLastAttackStep(getLastAttackStep()-1);
    }

    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        Vector2D location = posCalc();
        g.setColor(Color.GREEN);
        g.fillOval(location.getIntX()+5, location.getIntY()+5, Commons.TowerSize, Commons.TowerSize);
    }
}
