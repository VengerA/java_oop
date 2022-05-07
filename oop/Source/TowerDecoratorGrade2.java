import java.awt.*;

public class TowerDecoratorGrade2 extends TowerDecorator{
    public TowerDecoratorGrade2(Tower tower) {
        super(tower);
    }

    /**
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g) {
        Vector2D location = t.posCalc();
        String str = getStr();
        t.paint(g);
        g.setColor(Color.WHITE);
        g.drawChars(str.toCharArray(), 0, str.toCharArray().length, location.getIntX()+24, location.getIntY()+27);
    }

}
