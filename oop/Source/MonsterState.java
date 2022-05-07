public abstract class MonsterState implements IPaintable{
    private Monster monster;
    private int stateLength;
    //TODO

    /**
     *
     * @param m Monster
     * @param length int
     */
    public MonsterState(Monster m, int length){
        this.monster = m;
        this.stateLength = length;
    }

    /**
     *
     */
    public abstract void update();

    /**
     *
     * @return int
     */
    public int getStateLength(){return this.stateLength;}

    /**
     *
     */
    public void decreaseState(){this.stateLength--;}

    /**
     *
     * @return Monster
     */
    public Monster getMonster(){return this.monster;}

    /**
     *
     */
    public abstract void clearIce();
}
