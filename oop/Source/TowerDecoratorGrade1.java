import java.awt.*;

public class TowerDecoratorGrade1 extends TowerDecorator{
    public TowerDecoratorGrade1(Tower tower) {
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
        g.drawChars(str.toCharArray(), 0, str.toCharArray().length, location.getIntX()+20, location.getIntY()+27);
    }
}

