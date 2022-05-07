import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator {

    public static void main(String [] args){
        String file = "../inputs/inp8.txt";
        ArrayList<Miner> minerArr = new ArrayList<Miner>();
        ArrayList<Smelter> smelterArr = new ArrayList<Smelter>();
        ArrayList<Constructor> consArr = new ArrayList<Constructor>();
        ArrayList<Transporter> transArr = new ArrayList<Transporter>();

        try {
            File myFile = new File(file);
            Scanner reader = new Scanner(myFile);
                String data = reader.nextLine();

                for (int i=1; i <= Integer.parseInt(data); i++) {
                    Thread thread;
                    int interval = Integer.parseInt(reader.next());
                    int capacity = Integer.parseInt(reader.next());
                    int type = Integer.parseInt(reader.next());
                    int total = Integer.parseInt(reader.next());
                    Miner miner = new Miner(i, typeIdentifier(type), capacity, interval, total);
                    minerArr.add(miner);
                }

                data = reader.next();;

                for (int i=1; i <= Integer.parseInt(data); i++) {
                    Thread thread;

                    int interval = Integer.parseInt(reader.next());
                    int inCap = Integer.parseInt(reader.next());
                    int outCap = Integer.parseInt(reader.next());
                    int type = Integer.parseInt(reader.next());
                    Smelter smelter = new Smelter(i, interval, inCap, outCap, typeIdentifier(type));
                    smelterArr.add(smelter);
                }

                data = reader.next();

                for (int i=1; i <= Integer.parseInt(data); i++) {
                    int interval = Integer.parseInt(reader.next());
                    int capacity = Integer.parseInt(reader.next());
                    int type = Integer.parseInt(reader.next());
                    Constructor cons = new Constructor(i, typeIdentifier(type), capacity, interval);
                    consArr.add(cons);
                }

                data = reader.next();

                for (int i=1; i <= Integer.parseInt(data); i++) {
                    Producer source;
                    Types sourceType;
                    Producer target;
                    Types targetType;
                    int interval = Integer.parseInt(reader.next());
                    int sourceMiner = Integer.parseInt(reader.next());
                    int sourceSmelter = Integer.parseInt(reader.next());
                    int targetSmelter = Integer.parseInt(reader.next());
                    int targetConstructor = Integer.parseInt(reader.next());

                    if(sourceMiner > 0) {
                        source = minerArr.get(sourceMiner - 1);
                        sourceType = Types.MINER;
                    } else {
                        source = smelterArr.get(sourceSmelter - 1);
                        sourceType = Types.SMELTER;
                    }

                    if(targetSmelter > 0) {
                        target = smelterArr.get(targetSmelter - 1);
                        targetType = Types.SMELTER;
                    } else {
                        target = consArr.get(targetConstructor - 1);
                        targetType = Types.CONSTRUCTOR;
                    }
                    Transporter trans = new Transporter(i, interval, source, target, sourceType, targetType );
                    transArr.add(trans);
                }

                ExecutorService executor = Executors.newCachedThreadPool();

                for(Transporter m: transArr) {
                    executor.execute(m);
                }

                for(Miner m: minerArr) {
                    executor.execute(m);
                }

                for(Smelter m: smelterArr) {
                    executor.execute(m);
                }

                for(Constructor m: consArr) {
                    executor.execute(m);
                }
                executor.shutdown();
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Types typeIdentifier(int id) {
        switch (id){
            case 0:
                return Types.IRON;
            case 1:
                return Types.COPPER;
            case 2:
                return Types.LIMESTONE;
            default:
                return null;
        }
    }
}
