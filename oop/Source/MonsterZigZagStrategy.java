import java.util.Random;

public class MonsterZigZagStrategy implements IMonsterStrategy {

    private boolean isIncrease = false;
    private int change = 5;


    /**
     *
     * @param position Vector2D
     * @param direction Vector2D
     * @return Vector2D
     */
    @Override
    public Vector2D updateDirection(Vector2D position, Vector2D direction) {
        int posx= position.getIntX();
        int posy =  position.getIntY();
        posx += direction.getIntX();
        posy += direction.getIntY();

        if(direction.getX() == 0) {
            if((position.getX() < 15 && position.getX() > 0) || (position.getX() < 315 && position.getX() > 300)) {
                if (!isIncrease) {
                    isIncrease = true;
                }
                posx += change;
            }
            else if((position.getX() > 85 && position.getX() < 100) ||( position.getX() > 385 && position.getX() < 400)){
                if(isIncrease){
                    isIncrease = false;
                }
                posx -=change;
            } else{
                if(isIncrease){
                    posx += change;
                }else{
                    posx -= change;
                }
            }
        }else {
            if((position.getY() < 15 && position.getY() > 0) || (position.getY() < 315 && position.getY() > 300)) {
                if (!isIncrease) {
                    isIncrease = true;
                }
                posy += change;
            }
            else if((position.getY() > 85 && position.getY() < 100) ||( position.getY() > 385 && position.getY() < 400)){
                if(isIncrease){
                    isIncrease = false;
                }
                posy -=change;
            } else{
                if(isIncrease){
                    posy += change;
                }else{
                    posy -= change;
                }
            }
        }
        return (new Vector2D(posx, posy));
    }
}
