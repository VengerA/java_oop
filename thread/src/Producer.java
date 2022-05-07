import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.DoubleStream;

public abstract class Producer implements Runnable {

    private int inCapacity;
    private int outCapacity;
    private Types prodType;
    private int interval;
    private int id;
    private final  HW2Logger logger = HW2Logger.getInstance();
    private Lock lock = new ReentrantLock();
    private boolean isActive = true;
    private int outStorage = 0;
    private int inStorage = 0;
    private ArrayList<Transporter> inTransList = new ArrayList<Transporter>();
    private ArrayList<Transporter> outTransList = new ArrayList<Transporter>();
    private Condition isOutFull = getLock().newCondition();
    private Condition isInEmpty = getLock().newCondition();
    private Condition isOutEmpty = getLock().newCondition();
    private Condition isInFull = getLock().newCondition();

    public Producer(int id, Types type, int outCapacity, int interval, int inCapacity) {
        this.prodType = type;
        this.outCapacity = outCapacity;
        this.inCapacity = inCapacity;
        this.interval = interval;
        this.id = id;
    }


    public Producer(){};

    public void run() {}


    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Types getProdType() {
        return prodType;
    }

    public int getId() {
        return id;
    }

    public int getOutStorage() {
        return outStorage;
    }

    public void setOutStorage(int val) {
        this.outStorage += val;
    }

    public int getInStorage() {
        return inStorage;
    }

    public void setInStorage(int val) {
        this.inStorage += val;
    }

    public HW2Logger getLogger() {
        return logger;
    }

    public Lock getLock() {
        return lock;
    }

    public boolean isActive() {
        return isActive;
    }

    public void makeInactive() {
        isActive = false;
    }

    public void addTransporterIn(Transporter tr) {
        inTransList.add(tr);
    }

    public void addTransporterOut(Transporter tr){
        outTransList.add(tr);
    }

    public ArrayList<Transporter> getInTransList() {
        return inTransList;
    }

    public ArrayList<Transporter> getOutTransList() {
        return outTransList;
    }

    public Condition getIsInEmpty() {
        return isInEmpty;
    }

    public Condition getIsInFull() {
        return isInFull;
    }

    public Condition getIsOutEmpty() {
        return isOutEmpty;
    }

    public Condition getIsOutFull() {
        return isOutFull;
    }

    public int getInCapacity() {
        return inCapacity;
    }

    public int getOutCapacity() {
        return outCapacity;
    }

    public void sleep () throws InterruptedException {
        Random random = new Random(System.currentTimeMillis());
        DoubleStream stream;
        stream = random.doubles(1, interval-interval*0.01, interval+interval*0.02);
        Thread.sleep((long) stream.findFirst().getAsDouble());
    }
}