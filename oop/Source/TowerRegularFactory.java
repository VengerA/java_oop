public class TowerRegularFactory implements ITowerFactory {

    /**
     *
     * @param position Vector2D
     * @return Tower
     */
    @Override
    public Tower createTower(Vector2D position) {
        TowerRegular newT = new TowerRegular(position);
        return newT;
    }
}
