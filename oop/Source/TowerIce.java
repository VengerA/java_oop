import java.awt.*;

public class TowerIce extends Tower{

    /**
     *
     * @param position Vector2D
     */
    public TowerIce(Vector2D position) {
        super.Tower(position);
        super.setRange(100);
    }

    /**
     *
     */
    private void attack() {
        if(getTargetMonster() != null) {
            Monster m = getTargetMonster();
            boolean isKilled = m.takeIceDamage();
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
            setLastAttackStep(20);
        }
        setLastAttackStep(getLastAttackStep() - 1);
    }

//    public void attackMonster

    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        //TODO
        Vector2D location = posCalc();
        g.setColor(Color.BLUE);
        g.fillOval(location.getIntX()+5, location.getIntY()+5, Commons.TowerSize, Commons.TowerSize);
        g.setColor(Color.PINK);
//        g.drawOval(location.getIntX()-range+Commons.TowerZoneDivideLength/2, location.getIntY()-range+Commons.TowerZoneDivideLength/2, range*2, range*2);
    }
}

