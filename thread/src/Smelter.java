import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

public class Smelter extends Producer {

    public Smelter(int id, int interval, int inCap, int outCap, Types type) {
        super(id, type, outCap, interval, inCap);

    }

    @Override
    public void run() {
        try {
            getLogger().Log(0, getId(), 0, 0, Action.SMELTER_CREATED);
            produce();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void produce() throws InterruptedException {
        while(checkActiveTransporters() || checkAbleToProduce()){
            try{
                getLock().lock();
                while( !checkAbleToProduce()) {
                    getIsInEmpty().await();
                    if(!checkActiveTransporters()){
                        if(!checkActiveTransporters() && !checkAbleToProduce()){
                            getLogger().Log(0, getId(), 0, 0, Action.SMELTER_STOPPED);
                            return;
                        }
                    }
                }
            }   finally {
                getLock().unlock();
            }
            


            try{
                getLock().lock();
                while(getOutStorage() == getOutCapacity()) {
                    getIsOutFull().await();
                }
            } finally {
                getLock().unlock();
            }
            try{
                getLock().lock();
                getLogger().Log(0, getId(), 0, 0, Action.SMELTER_STARTED);
                handleInStore();
                sleep();
                setOutStorage(1);
                getIsInFull().signal();
                getIsOutEmpty().signal();
                getLogger().Log(0, getId(), 0, 0, Action.SMELTER_FINISHED);
            } finally {
                getLock().unlock();
            }
        }
        try{
            getLock().lock();
            makeInactive();
        } finally {
            getLock().unlock();
        }
        getLogger().Log(0, getId(), 0, 0, Action.SMELTER_STOPPED);
    }

    public boolean checkAbleToProduce(){
        if (getProdType() == Types.IRON) {
            return getInStorage() >= 1;
        } else {
            return getInStorage() >= 2;
        }
    }

    public void handleInStore(){
        try{
            getLock().lock();
            if (getProdType() == Types.IRON) {
                this.setInStorage(-1);
            } else {
                this.setInStorage(-2);
            }
        } finally {
            getLock().unlock();
        }
    }

    public boolean checkActiveTransporters() {
        ArrayList<Transporter> list = getInTransList();
        for(Transporter t : list) {
            if (t.isActive()) {
                return true;
            }
        }

        return false;
    }
}
