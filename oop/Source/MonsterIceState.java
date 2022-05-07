import java.awt.*;

public class MonsterIceState extends MonsterState{
    private Vector2D realDirection;

    /**
     *
     * @param m Monster
     */
    public MonsterIceState(Monster m) {
        super(m, 5);
        realDirection = m.getDirection();
    }


    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        if(getStateLength() > 0) {
            Vector2D pos = getMonster().getPos();
            g.setColor(Color.BLUE);
            g.drawRect(pos.getIntX(), pos.getIntY(), 30,30);
        }
    }

    /**
     *
     */
    @Override
    public void update() {
        if(getStateLength() > 0) {
             getMonster().setDirection(new Vector2D(realDirection.getX() * 0.2 * (5 - getStateLength() + 1), (float)realDirection.getY() * 0.2 * (5 - getStateLength() + 1)));
             decreaseState();
        }
    }

    /**
     *
     */
    @Override
    public void clearIce() {
        getMonster().setDirection(realDirection);
    }

}
