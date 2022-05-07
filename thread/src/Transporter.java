public class Transporter extends Producer {
    private final Producer source;
    private final Producer target;
    private Types sourceType;
    private Types targetType;
    private int sourceId;
    private int targetId;


    public Transporter(int id, int interval, Producer source, Producer target, Types sourceT, Types targetT  ) {
        this.source = source;
        this.target = target;
        this.sourceId = source.getId();
        this.targetId = target.getId();
        this.sourceType = sourceT;
        this.targetType = targetT;

        source.addTransporterOut(this);
        target.addTransporterIn(this);
        setId(id);
        setInterval(interval);
    }

    @Override
    public void run()  {
        try {
            getLogger().Log(0,0, getId(), 0, Action.TRANSPORTER_CREATED);
            transport();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void transport() throws InterruptedException{

        logMessage(true, Action.TRANSPORTER_GO);

        while (source.isActive() || source.getOutStorage() > 0) {
            boolean breaker = false;
            sleep();
//            logMessage(true, Action.TRANSPORTER_ARRIVE);

            try{
                source.getLock().lock();
                while (source.getOutStorage() == 0) {
                    if(!source.isActive()){
                        breaker = true;
                        break;
                    }
                    source.getIsOutEmpty().await();
                }
            } finally {
                logMessage(true, Action.TRANSPORTER_ARRIVE);
                source.setOutStorage(-1);
                source.getLock().unlock();
            }

            if(breaker) {
                break;
            }

            logMessage(true, Action.TRANSPORTER_TAKE);

            try{
                source.getLock().lock();
                source.getIsOutFull().signal();
            } finally {
                source.getLock().unlock();
            }


            logCombinedMessage(Action.TRANSPORTER_GO);

            sleep();

            logMessage(false, Action.TRANSPORTER_ARRIVE);

            try{
                target.getLock().lock();
                while (target.getInStorage() == target.getInCapacity()) {
                   target.getIsInFull().await();
               }
               
            } finally {
                logMessage(false, Action.TRANSPORTER_DROP);
                target.setInStorage(1);
                target.getLock().unlock();
            }

            

            try{
                target.getLock().lock();
                target.getIsInEmpty().signal();
                if(!source.isActive() && source.getOutStorage() == 0){
                    makeInactive();
                    logCombinedMessage(Action.TRANSPORTER_GO);
                    getLogger().Log(0, 0, getId(), 0, Action.TRANSPORTER_STOPPED);
                    return;
                }
            } finally {
                target.getLock().unlock();
            }
            logCombinedMessage(Action.TRANSPORTER_GO);
        }

        
    }

    public synchronized void logMessage(boolean toSource, Action msg) {
        if (toSource) {
            if (sourceType == Types.MINER)
                getLogger().Log(sourceId,0, getId(), 0, msg);
            else
                getLogger().Log(0, sourceId, getId(), 0, msg);
        } else
            if (targetType == Types.SMELTER){
                getLogger().Log(0, targetId, getId(), 0, msg);
            } else
                getLogger().Log(0, 0, getId(), targetId, msg);


    }

    public synchronized void logCombinedMessage(Action msg) {
        //Todo: Transporter Info istenmis bazilarinda bunu sor
        if(sourceType == Types.MINER) {
            if(targetType == Types.SMELTER)
                getLogger().Log(sourceId, targetId, getId(), 0, msg);
            else
                getLogger().Log( sourceId, 0, getId(), targetId, msg);
        } else
            getLogger().Log( 0, sourceId, getId(), targetId, msg);
    }

}
