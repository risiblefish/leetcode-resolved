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
        Hero h1 = Hero.builder().name("����").weapon(Weapon.MIRROR).speed(1).isImmuneSilent(true).energy(100).build();
        Hero h2 = Hero.builder().name("��ս").weapon(Weapon.OTHER).speed(2).build();
        Hero h3 = Hero.builder().name("Ů��").weapon(Weapon.DEMON_BELL).speed(3).isImmuneSilent(true).energy(100).build();
        Hero h4 = Hero.builder().name("����").weapon(Weapon.LOLLIPOP).speed(4).isImmuneSilent(true).build();
        Hero h5 = Hero.builder().name("����").weapon(Weapon.MIRROR).speed(5).isImmuneFear(true).energy(100).build();
        Hero h6 = Hero.builder().name("����").weapon(Weapon.LOLLIPOP).speed(6).isImmuneSilent(true).build();
        HEROES = Arrays.asList(h1, h2, h3, h4, h5, h6);
        HEROES.sort(Comparator.comparingInt(p -> p.speed));
        ROUND_START_EVENT = new ArrayDeque<>();
        ROUND_END_EVENT = new ArrayDeque<>();
        RAND = new Random();
        FEAR = new HashMap<>();
        SILENT = new HashMap<>();
    }

    private static void sim() {
        //�ȳ�ʼ��Ӣ������
        init();
        //����15�غϵ�ս��ģ��
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("---------------��%s�غϿ�ʼ---------------", i + 1));
            simOneRound();
            System.out.println(String.format("---------------��%s�غϽ���---------------", i + 1));
        }
    }

    private static void simOneRound() {
        //�غϿ�ʼʱ���¼���������ƻغ���-1
        performRoundStartMapEvent();
        //6����λ����
        for (int i = 0; i < 6; i++) {
            act(i);
        }
        //�������
        performBoss();
        //�غϽ�����ļ����¼������绨���ļӹ������ӵļ�������������
        performRoundEndSkillEvent();
        //�غϽ�����ĵ�ͼʱ�䣺������������ӹ�����߹���������
        performRoundEndMapEvent();
    }

    private static void performRoundStartMapEvent() {
        decControlRound();
    }

    private static void decControlRound() {
        decControlRound("�־�",FEAR);
        decControlRound("��Ĭ",SILENT);
    }

    //���п��ƵĻغ���-1���������0�����޳�
    private static void decControlRound(String controlType, Map<String, Integer> map) {
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() == 1) {
                String heroName = entry.getKey();
                System.out.println(String.format("%s��[%s]���",heroName, controlType));
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
        //ȫԱ����+50
        incAllEnergy(50);
        //�˴��̶�Ϊ��ս����
        Optional<Hero> aspen = HEROES.stream().filter(h -> h.name.equals("��ս")).findFirst();
        aspen.get().energy -= 100;
    }

    private static void performRoundEndSkillEvent() {
        while (!ROUND_END_EVENT.isEmpty()) {
            ROUND_END_EVENT.poll().run();
        }
    }

    private static void performBoss() {
        //todo: �Ǳ���+10�� ����+20�����㱩����δ֪���˴���ȡ50%
        int val = RAND.nextInt(2) == 0 ? 10 : 20;
        incAllEnergy(val);
        addCatastrophe(RAND.nextInt(2) == 0 ? 1 : 2);
    }

    private static void act(int turn) {
        //�ֵ�˭����
        Hero hero = HEROES.get(turn);
        performHero(hero);
    }

    private static void performHero(Hero hero) {
        System.out.println(String.format("��ʱ%sӵ��%s����", hero.name, hero.energy));
        if (hero.energy >= 100) {
            //���û����Ĭ���ض��ܿ���
            if (!SILENT.containsKey(hero.name)) {
                actSkill(hero);
            }
            //����Ĭ����û���־壬�����չ�
            else if (!FEAR.containsKey(hero)) {
                normalAttack(hero);
            }
            //����Ĭ�ұ��־壺 do nothing
        } else {
            //�����������Ҳ����־�
            if (!FEAR.containsKey(hero.name)) {
                normalAttack(hero);
            }
        }
    }

    //�����ֶ�
    private static void addCatastrophe(int cnt) {
        HEROES.forEach(h -> {
            h.catastrophe += cnt;
            if (h.catastrophe == 5) {
                System.out.println(String.format("��ʱ%s�ֶ�ﵽ5��", h.name));
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
        System.out.println(String.format("��ʱ%s�չ�������Ϊ%s", hero.name, hero.energy));
        addCatastrophe(1);
    }

    private static void actSkill(Hero hero) {
        performWeapon(hero);
        hero.energy = 0;
        hero.actSkillCnt++;
        System.out.println(String.format("��ʱ%s��������Ϊ0, Ŀǰ������%s��", hero.name, hero.actSkillCnt));
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
        System.out.println(String.format("ȫԱ���%s����", val));
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

