import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

public class Constructor extends Producer {

    public Constructor(int id, Types type, int capacity, int interval) {
        super(id, type, 0, interval, capacity);

    }


    @Override
    public void run()  {
        try {
            getLogger().Log(0, 0, 0, getId(), Action.CONSTRUCTOR_CREATED);
            produce();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int count = 0;
    public void produce()  throws InterruptedException {

        while(checkActiveTransporters() || checkAbleToProduce()){
            try{
                getLock().lock();
                while(!checkAbleToProduce()) {
                    getIsInEmpty().await();
                    if(!checkActiveTransporters() && !checkAbleToProduce()){
                        getLogger().Log(0, 0, 0, getId(), Action.CONSTRUCTOR_STOPPED);
                        return;
                    }
                }
            } finally {
                getLock().unlock();
            }

            try {
                getLock().lock();
                getLogger().Log(0, 0, 0, getId(), Action.CONSTRUCTOR_STARTED);
                sleep();
                handleStores();
                getIsInFull().signal();
                getIsOutEmpty().signal();
                getLogger().Log(0, 0, 0, getId(), Action.CONSTRUCTOR_FINISHED);
            } finally {
                getLock().unlock();
            }
        }
        getLogger().Log(0, 0, 0, getId(), Action.CONSTRUCTOR_STOPPED);
        makeInactive();
    }

    public boolean checkAbleToProduce(){
        if (getProdType() == Types.COPPER) {
            return getInStorage() >= 1;
        } else {
            return getInStorage() >= 2;
        }
    }

    public void handleStores(){
        try{
            getLock().lock();
            if (getProdType() == Types.COPPER) {
                this.setInStorage(-1);
            } else {
                this.setInStorage(-2);
            }
        } finally {
            setOutStorage(1);
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
