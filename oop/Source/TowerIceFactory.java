import java.util.ArrayList;

public class TowerIceFactory implements ITowerFactory {

    /**
     *
     * @param position Vector2D
     * @return Tower
     */
    @Override
    public Tower createTower(Vector2D position) {
        TowerIce newT = new TowerIce(position);
        return newT;
    }
}
