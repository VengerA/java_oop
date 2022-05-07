import java.awt.*;
import java.util.Random;

public class Monster extends Entity {
    //TODO
    private final Random rand = new Random();
    private Graphics g;
    private int health;
    private final IMonsterStrategy strategy;

    private MonsterState state;

    private Vector2D pos = new Vector2D(rand.nextInt(Commons.StartWidth- 30), Commons.StartY);
    private Vector2D direction = new Vector2D(0, -rand.nextInt(3) - 1) ;
//    Vector2D direction = new Vector2D(0, -1) ;
    private boolean isDone = false;
    private boolean isDead = false;

    /**
     *
     * @param g Graphics
     * @param waveCount int
     */
    public Monster(Graphics g, int waveCount) {
        this.g = g;
        this.health = 100 + waveCount * 20;

        if(rand.nextBoolean()) {
            strategy = new MonsterCircularStrategy();
        }else {
            strategy = new MonsterZigZagStrategy();
        }
    }

    /**
     *
     */
    @Override
    public void step() {
        //TODO
        if(isDead) {
            return;
        }
        int newX = 0;
        int newY = 0;

        if( state != null && state.getStateLength() > 0){
            state.update();
        }

        if(direction.getY() + pos.getY() < 20 && direction.getIntX() == 0) {

            newY = 0;
            newX = -direction.getIntY();
            direction = new Vector2D(newX, newY);
        } else if (direction.getX() + pos.getX() > 360 && direction.getIntY() == 0 ) {
            newY = direction.getIntX();
            newX = 0;
            direction = new Vector2D(newX, newY);

        } else if  (direction.getY() + pos.getY() > 360 && direction.getIntX() == 0) {
            newY = 0;
            newX = -direction.getIntY();
            direction = new Vector2D(newX, newY);
        }

        pos = strategy.updateDirection(pos, direction);

        if(pos.getIntX() < 100 && pos.getY() > 300 ){
            setIsDone(true);
        }
    }

    /**
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        int posX = pos.getIntX();
        int posY = pos.getIntY();
        if(!isDone){
            if(isDead){
                return;
            }
            g.setColor(Color.orange);
            g.drawChars(Integer.toString(health).toCharArray(), 0, Integer.toString(health).toCharArray().length, posX, posY+18);
            g.setColor(Color.ORANGE);
            g.drawRect(posX, posY, 30,30);
            if(state != null){
                state.paint(g);
            }

        }
    }

    /**
     *
     * @return boolean
     */
    public boolean takePoisonDamage(){
        if(state != null){
            state.clearIce();
        }
        this.state = new MonsterPoisonState(this);
        this.health -= 5;
        if(health <= 0){
            isDead = true;
            isDone = true;
            return true;
        }
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean takeRegularDamage(){
        this.health -= 20;
        if (this.health <= 0) {
            this.isDead = true;
            this.isDone = true;
            return true;
        }
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean takeIceDamage(){
        this.state = new MonsterIceState(this);
        this.health -= 15;
        if (this.health <= 0) {
            this.isDead = true;
            this.isDone = true;
            return true;
        }
        return false;
    }

    /**
     *
     * @param x
     */
    private void setIsDone(boolean x) {
        this.isDone = x;
    }

    /**
     *
     * @return boolean
     */
    public boolean getIsDone(){
        return this.isDone;
    }

    /**
     *
     * @return boolean
     */
    public boolean getIsDead(){
        return this.isDead;
    }

    /**
     *
     * @return int
     */
    public int getDirectionMagnitude() {return -direction.getIntY();}

    /**
     *
     * @param other int
     * @return int
     */
    public int compareMagnitude(int other) {
        return getDirectionMagnitude() > other ? 1 : 0;
    }

    /**
     *
     * @return Vector2D
     */
    public Vector2D getPos() {return pos;}

    /**
     *
     * @param val int
     */
    public void decreaseHealth(int val) {this.health -= val;}

    /**
     *
     * @return Vector2D
     */
    public Vector2D getDirection(){return this.direction;}
    public void setDirection(Vector2D d){this.direction = d;}
}
