import java.awt.*;
import java.util.ArrayList;

public class TowerRegular extends Tower{

    /**
     *
     * @param position Vector2D
     */
    public TowerRegular(Vector2D position) {
        super.Tower(position);
        super.setRange(150);
    }

    /**
     *
     */
    private void attack() {
        if(getTargetMonster() != null) {
            Monster m = getTargetMonster();
            boolean isKilled = m.takeRegularDamage();
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
        if(this.getLastAttackStep() == 0) {
            attack();
            this.setLastAttackStep(20);
        }
        this.setLastAttackStep(getLastAttackStep()-1);
    }

    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        Vector2D location = posCalc();
        g.setColor(Color.RED);
        g.fillOval(location.getIntX()+5, location.getIntY()+5, Commons.TowerSize, Commons.TowerSize);
    }
}
