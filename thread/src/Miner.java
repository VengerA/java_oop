import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Miner extends Producer {
    private int totalOre;

    public Miner(int id, Types type, int capacity, int interval, int TotalOre) {
        super(id, type, capacity, interval, 0);
        this.totalOre = TotalOre;

    }

    @Override
    public void run() {
        try {
            produceMine();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void produceMine() throws InterruptedException{
        getLogger().Log(getId(), 0, 0, 0, Action.MINER_CREATED);
        while(true) {
            try{
                getLock().lock();
                if(getTotalOre() == 0) {
                    break;
                }
            } finally {
                getLock().unlock();
            }
            try {
                try{
                    getLock().lock();
                    while (getOutStorage() == getOutCapacity()) {
                        getIsOutFull().await();
                    }
                } finally {
                    getLock().unlock();
                }

                getLock().lock();
                getLogger().Log(getId(), 0, 0, 0, Action.MINER_STARTED);
                sleep();
                setOutStorage(1);
                setTotalOre(-1);
                getLogger().Log(getId(), 0, 0, 0, Action.MINER_FINISHED);
                getLock().unlock();
            } finally {
                try{
                    getLock().lock();
                    getIsOutEmpty().signal();
                } finally {
                    getLock().unlock();
                }
            }
        }
        try{
            getLock().lock();
            makeInactive();
        } finally {
            getLock().unlock();
        }
        try{
            getLock().lock();
            makeInactive();
        } finally {
            getLock().unlock();
        }
        getLogger().Log(getId(), 0, 0, 0, Action.MINER_STOPPED);
    }

    public int getTotalOre() {
        return totalOre;
    }

    public void setTotalOre(int val) {
        this.totalOre += val;
    }
}
