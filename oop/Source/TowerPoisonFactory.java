public class TowerPoisonFactory implements ITowerFactory {


    /**
     *
     * @param position Vector2D
     * @return Tower
     */
    @Override
    public Tower createTower(Vector2D position) {
        TowerPoison newT = new TowerPoison(position);
        return newT;
    }
}
