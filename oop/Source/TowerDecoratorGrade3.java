import java.awt.*;

public class TowerDecoratorGrade3 extends TowerDecorator{
    public TowerDecoratorGrade3(Tower tower) {
        super(tower);
    }
    //TODO

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
        g.drawChars(str.toCharArray(), 0, str.toCharArray().length, location.getIntX()+28, location.getIntY()+27);
    }

}
