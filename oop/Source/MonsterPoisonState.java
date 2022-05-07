import java.awt.*;

public class MonsterPoisonState extends MonsterState{

    public MonsterPoisonState(Monster m) {
        super(m, 3);
    }

    /**
     *
     */
    @Override
    public void update() {
        if (getStateLength() > 0) {
            getMonster().decreaseHealth(5);
            decreaseState();
        }
    }

    /**
     *
     */
    @Override
    public void clearIce() {}

    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        if(getStateLength() > 0){
            Vector2D pos = getMonster().getPos();
            g.setColor(Color.GREEN);
            g.drawRect(pos.getIntX(), pos.getIntY(), 30,30);
        }
    }

}
