public class MonsterCircularStrategy implements IMonsterStrategy {
    //TODO

    /**
     *
     * @param position  Vector2D
     * @param direction Vector2D
     * @return Vector2D
     */
    @Override
    public Vector2D updateDirection(Vector2D position, Vector2D direction) {
        double posx = position.getX();
        double posy =  position.getY();
        posx += direction.getIntX();
        posy += direction.getIntY();
        return (new Vector2D(posx, posy));
    }
}
