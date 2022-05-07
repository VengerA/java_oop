import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Database {
    private static final Database _inst = new Database();
    public static Database getInstance() { return _inst; }

    private List<Hero> heroes;
    private List<HeroAlliance> heroAlliances;
    private List<Alliance> alliances;
    private List<Player> players;

    public Database() {
        //TODO optional
        heroes = new ArrayList<Hero>();
        heroAlliances = new ArrayList<HeroAlliance>();
        alliances = new ArrayList<Alliance>();
        players = new ArrayList<Player>();
    }

    //bParse the CSV files and fill the four lists given above.
    public void parseFiles(String playerCSVFile) throws IOException {

        String heroCsv = "herostats.csv";
        String heroAllianceCsv = "heroalliances.csv";
        String alliencesCsv = "alliances.csv";

        if(heroes.size() == 0 || heroAlliances.size() == 0 || alliances.size() == 0) {
            FileInputStream f = new FileInputStream(heroCsv);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            br.lines().forEach(consumeHero);



            f = new FileInputStream(heroAllianceCsv);
            br = new BufferedReader(new InputStreamReader(f));
            br.lines().forEach(consumeHeroAlliance);

            f = new FileInputStream(alliencesCsv);
            br = new BufferedReader(new InputStreamReader(f));
            br.lines().forEach(consumeAlliance);
            Files.lines(Paths.get(alliencesCsv)).forEach(consumeAlliance);
        }

        //Todo: Check should we empty the players if new player csv is given
        players.removeAll(players);
        FileInputStream f = new FileInputStream(playerCSVFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        br.lines().forEach(consumePlayer);

    }

    Consumer<String> consumeHero = e -> {
        String[] data = e.split(",");
        addHero(new Hero(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9]), Integer.parseInt(data[10]), Integer.parseInt(data[11])));
    };

    Consumer<String> consumeHeroAlliance = e -> {
      String[] data = e.split(",");
      String name = data[0];
      int tier = Integer.parseInt(data[1]);
      ArrayList<String> dataList = new ArrayList<>(Arrays.asList(data));
      dataList.remove(0);
      dataList.remove(0);
      String[] alliances = dataList.toArray(new String[0]);
      addHeroAlliance(new HeroAlliance(name, tier, alliances));
    };

    Consumer<String> consumeAlliance = e -> {
        String[] data = e.split(",");
        addAlliance(new Alliance(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
    };

    Consumer<String> consumePlayer = e -> {
        String[] data = e.split(",");
        List<String> dataArray = new ArrayList<>(Arrays.asList(data));

        String name = data[0];
        dataArray.remove(0);
        List<Hero> heros = new ArrayList<Hero>();

        Consumer<String> consumePlayerHeros = malatya -> {
            String[] stats = malatya.split("\\|");
            heros.add(getHeroes().stream().filter(h -> h.getName().equals(stats[0]) &&  h.getLevel() == Integer.parseInt(stats[1])).findFirst().orElse(getHeroes().get(0)));
        };

        dataArray.forEach(consumePlayerHeros);

        addPlayer(new Player(name, heros));

    };

    //Gets the heroes belonging to a particular alliance and sorts them according to their DPS. It should only return
    //count number of heroes. Heroes should be distinct in a sense that, different levels of the same hero should not be
    //in the result.
    //15pts
    public List<Hero> getHeroesOfParticularAlliance(String alliance, int count) {
        //TODO
        List<String> heroNames = getHeroAlliances().stream().filter(a -> a.getAlliances().contains(alliance)).map(HeroAlliance::getName).distinct().collect(Collectors.toList());
        return getHeroes().stream().filter(h -> heroNames.contains(h.getName())).sorted(Comparator.comparing(Hero::getDPS).reversed()).limit(count).collect(Collectors.toList());
    }

    //Returns a map of HeroAlliances based on tier where the alliance required count and alliance level counts match.
    //15pts
    public Map<Integer, List<HeroAlliance>> getHeroAllianceMatchingTier(int allianceRequiredCount, int allianceLevelCount) {
        //TODO
        List<String> allianceNames = new ArrayList<String>();
        getAlliances().stream().filter(a -> a.getRequiredCount() == allianceRequiredCount && a.getLevelCount() == allianceLevelCount).map(Alliance::getName).forEach(s -> allianceNames.add(s));

        return getHeroAlliances().stream().filter(h -> h.getAlliances().stream().anyMatch(allianceNames::contains)).distinct().collect(Collectors.groupingBy(HeroAlliance::getTier));
    }


    //Return the heroes of each player that have bigger than the mana, health and move speed given as arguments.
    //10pts
    public List<List<Hero>> getPlayerHeros(int mana, int health, int moveSpeed) {
        List<List<Hero>> ae = new ArrayList<>();
        getPlayers().stream().map(Player::getHeroes).collect(Collectors.toList()).forEach(a -> {
            List<Hero> aaaa = a.stream().filter(hero -> hero.getMana() > mana  && hero.getHealth() > health && hero.getMoveSpeed() > moveSpeed).collect(Collectors.toList());
            ae.add(aaaa);

        });

        return ae;
    }

    //Calculate and print the average maximum damage of players whose heroes has minimum damage is bigger than the given first argument.
    //10 pts
    public void printAverageMaxDamage(int minDamage) {
        //TODO
        getPlayers().stream().forEach(player -> {
            List<Integer> aaa = player.getHeroes().stream().filter(h -> h.getDamageMin() > minDamage).map(Hero::getDamageMax).collect(Collectors.toList());
            double sum = aaa.stream().mapToInt(Integer::intValue).sum();
            if(aaa.size() == 0 ) {
                System.out.println(0);
            } else {
                System.out.println(sum/aaa.size());
            }
        });

    }

    //In this function, print each player and its heroes. However, you should only print heroes belonging to
    // any of the particular alliances and whose attack speed is smaller than or equal to the value given.
    //30pts
//    public void printAlliances(String[] alliances, double attackSpeed) {
//        //TODO
//        List<String> aa = Arrays.asList(alliances);
//        getPlayers().forEach(player -> {
//            List<Hero> heros = player.getHeroes().stream().filter(h -> h.getAttackSpeed() <= attackSpeed && aa.stream().anyMatch(Objects.requireNonNull(getHeroAlliances().stream().filter(f -> f.getName().equals(h.getName())).map(HeroAlliance::getAlliances).findFirst().orElse(null))::contains)).collect(Collectors.toList());
//            if(heros.size() > 0) {
//                System.out.println(player.getName());
//                heros.stream().forEach(h -> System.out.println("Name: " + h.getName()+ " Level: " + h.getLevel()));
//            }
//        });
//
//    }

    public void printAlliances(String[] alliances, double attackSpeed) {
        players.stream().filter(player->
                player.getHeroes().stream().anyMatch(hero ->
                        hero.getAttackSpeed()<=attackSpeed && Arrays.stream(alliances)
                                .anyMatch(ally -> heroAlliances.stream()
                                        .filter(ha -> ha.getName().equals(hero.getName())).anyMatch(ha->
                                                ha.getAlliances().contains(ally)))))
                .forEach(player-> {
                            List<Hero> h = player.getHeroes().stream().filter(hero ->
                                    hero.getAttackSpeed() <= attackSpeed && Arrays.stream(alliances)
                                            .anyMatch(ally -> heroAlliances.stream()
                                                    .filter(ha -> ha.getName().equals(hero.getName())).anyMatch(ha ->
                                                            ha.getAlliances().contains(ally)))).collect(Collectors.toList());
                            if(h.size() > 0) {
                                System.out.println(player.getName());
                                h.stream().forEach(hx -> System.out.println("Name: " + hx.getName()+ " Level: " + hx.getLevel()));
                            }
                        });
        //TODO
    }
//
    public static void main(String[] args) {
        try {
            getInstance().parseFiles("players.csv");

            //Tester for GetHeroesParticular Alliance
//            List<Hero> herox = getInstance().getHeroesOfParticularAlliance("Hunter", 5);
//            herox.forEach(hero1 -> System.out.println(hero1.toString()));

            //TESTER for getHeroAllianceMatchingTier
//            Map<Integer, List<HeroAlliance>> res = getInstance().getHeroAllianceMatchingTier(2, 2);
//            res.entrySet().forEach(entry -> {
//                System.out.println("Tier: " + entry.getKey());
//                entry.getValue().forEach(heroAlliance -> System.out.println("\t" + heroAlliance.toString()));
//            });

//            //Tester for getPlayerHeros
//            AtomicInteger i = new AtomicInteger(1);
//            getInstance().getPlayerHeros(80, 1800, 300).forEach(a -> {
//                System.out.println("Player " + i.getAndIncrement());
//                a.forEach(hero -> System.out.println(hero.toString()));
//            });

            //Tester for printAverageMax Damage
//            getInstance().printAverageMaxDamage(60);

            //Tester for printAlliances(

            getInstance().printAlliances(new String[]{"Mage"}, 1.3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Alliance> getAlliances() {
        return alliances;
    }

    public List<HeroAlliance> getHeroAlliances() {
        return heroAlliances;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setHeroAlliances(List<HeroAlliance> heroAlliances) {
        this.heroAlliances = heroAlliances;
    }

    public void setAlliances(List<Alliance> alliances) {
        this.alliances = alliances;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public void addHero(Hero h){
        this.heroes.add(h);
    }

    public void addHeroAlliance(HeroAlliance e){
        this.heroAlliances.add(e);
    }

    public void addAlliance(Alliance a){
        this.alliances.add(a);
    }

    public void addPlayer(Player p){
        this.players.add(p);
    }

    public void clearPlayers(){

        return;
    }
}

