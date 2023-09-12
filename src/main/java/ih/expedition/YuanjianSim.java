package ih.expedition;

import lombok.Builder;

import java.util.*;

/**
 * @author Sean Yu
 */
public class YuanjianSim {

    static List<Hero> HEROES;

    static Queue<Runnable> ROUND_START_EVENT;
    static Queue<Runnable> ROUND_END_EVENT;

    static Random RAND;

    static Map<String, Integer> FEAR;
    static Map<String, Integer> SILENT;

    public static void main(String[] args) {
        sim();
    }

    private static void init() {
        Hero h1 = Hero.builder().name("花王").weapon(Weapon.MIRROR).speed(1).isImmuneSilent(true).energy(100).build();
        Hero h2 = Hero.builder().name("暗战").weapon(Weapon.OTHER).speed(2).build();
        Hero h3 = Hero.builder().name("女王").weapon(Weapon.DEMON_BELL).speed(3).isImmuneSilent(true).energy(100).build();
        Hero h4 = Hero.builder().name("狐狸").weapon(Weapon.LOLLIPOP).speed(4).isImmuneSilent(true).build();
        Hero h5 = Hero.builder().name("镜子").weapon(Weapon.MIRROR).speed(5).isImmuneFear(true).energy(100).build();
        Hero h6 = Hero.builder().name("关心").weapon(Weapon.LOLLIPOP).speed(6).isImmuneSilent(true).build();
        HEROES = Arrays.asList(h1, h2, h3, h4, h5, h6);
        HEROES.sort(Comparator.comparingInt(p -> p.speed));
        ROUND_START_EVENT = new ArrayDeque<>();
        ROUND_END_EVENT = new ArrayDeque<>();
        RAND = new Random();
        FEAR = new HashMap<>();
        SILENT = new HashMap<>();
    }

    private static void sim() {
        //先初始化英雄数据
        init();
        //进行15回合的战斗模拟
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("---------------第%s回合开始---------------", i + 1));
            simOneRound();
            System.out.println(String.format("---------------第%s回合结束---------------", i + 1));
        }
    }

    private static void simOneRound() {
        //回合开始时的事件：比如控制回合数-1
        performRoundStartMapEvent();
        //6个单位出手
        for (int i = 0; i < 6; i++) {
            act(i);
        }
        //章鱼出手
        performBoss();
        //回合结束后的技能事件：比如花王的加工，镜子的加能量，净化等
        performRoundEndSkillEvent();
        //回合结束后的地图时间：比如回能量，加攻，最高攻降能量等
        performRoundEndMapEvent();
    }

    private static void performRoundStartMapEvent() {
        decControlRound();
    }

    private static void decControlRound() {
        decControlRound("恐惧",FEAR);
        decControlRound("沉默",SILENT);
    }

    //所有控制的回合数-1，如果降到0，则剔除
    private static void decControlRound(String controlType, Map<String, Integer> map) {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() == 1) {
                String heroName = entry.getKey();
                System.out.println(String.format("%s的[%s]解除",heroName, controlType));
                iterator.remove();
            } else {
                int old = entry.getValue();
                entry.setValue(old - 1);
            }
        }
        System.out.println("--------" + controlType + "---------------");
        System.out.println(map);
    }

    private static void performRoundEndMapEvent() {
        //全员能量+50
        incAllEnergy(50);
        //此处固定为暗战降攻
        Optional<Hero> aspen = HEROES.stream().filter(h -> h.name.equals("暗战")).findFirst();
        aspen.get().energy -= 100;
    }

    private static void performRoundEndSkillEvent() {
        while (!ROUND_END_EVENT.isEmpty()) {
            ROUND_END_EVENT.poll().run();
        }
    }

    private static void performBoss() {
        //todo: 非暴击+10， 暴击+20，章鱼暴击率未知，此处先取50%
        int val = RAND.nextInt(2) == 0 ? 10 : 20;
        incAllEnergy(val);
        addCatastrophe(RAND.nextInt(2) == 0 ? 1 : 2);
    }

    private static void act(int turn) {
        //轮到谁出手
        Hero hero = HEROES.get(turn);
        performHero(hero);
    }

    private static void performHero(Hero hero) {
        System.out.println(String.format("此时%s拥有%s能量", hero.name, hero.energy));
        if (hero.energy >= 100) {
            //如果没被沉默，必定能开大
            if (!SILENT.containsKey(hero.name)) {
                actSkill(hero);
            }
            //被沉默，但没被恐惧，可以普攻
            else if (!FEAR.containsKey(hero)) {
                normalAttack(hero);
            }
            //被沉默且被恐惧： do nothing
        } else {
            //能量不够，且不被恐惧
            if (!FEAR.containsKey(hero.name)) {
                normalAttack(hero);
            }
        }
    }

    //增加灾厄
    private static void addCatastrophe(int cnt) {
        HEROES.forEach(h -> {
            h.catastrophe += cnt;
            if (h.catastrophe == 5) {
                System.out.println(String.format("此时%s灾厄达到5层", h.name));
                if (!h.isImmuneSilent) {
                    SILENT.put(h.name, 2);
                }
                if (!h.isImmuneFear) {
                    FEAR.put(h.name, 2);
                }
                h.catastrophe = 0;
            }
        });
    }

    private static void normalAttack(Hero hero) {
        hero.energy += 100;
        System.out.println(String.format("此时%s普攻后，能量为%s", hero.name, hero.energy));
        addCatastrophe(1);
    }

    private static void actSkill(Hero hero) {
        performWeapon(hero);
        hero.energy = 0;
        hero.actSkillCnt++;
        System.out.println(String.format("此时%s开大，能量为0, 目前共开大%s次", hero.name, hero.actSkillCnt));
        addCatastrophe(1);
    }

    private static void performWeapon(Hero hero) {
        switch (hero.weapon) {
            case DEMON_BELL: {
                incAllEnergy(RAND.nextInt(2) == 0 ? 20 : 30);
                break;
            }
            case MIRROR: {
                ROUND_END_EVENT.add(() -> incAllEnergy(15));
                break;
            }
            default:
                break;
        }
    }

    private static void incAllEnergy(int val) {
        System.out.println(String.format("全员获得%s能量", val));
        HEROES.forEach(h -> h.energy += val);
    }
}


@Builder
class Hero {
    String name;
    Weapon weapon;
    int energy;
    int speed;
    int actSkillCnt;
    boolean isImmuneFear;
    boolean isImmuneSilent;
    int catastrophe;
}

enum Weapon {
    DEMON_BELL, MIRROR, LOLLIPOP, OTHER;
}

