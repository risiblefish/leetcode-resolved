package ih.dmage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * ��DΪ�������ܵ����˺�
 * �˺���ʽ��
 * f = D * [1-���׼��� * ��1-�Ƽף�+0.7 * ����] * ��1+0.3 * ��׼�� * ��1 + �������ˣ�* �� 1 + ״̬���� + ְҵ���ˣ�* ��1+����ӡ��+����ӡ�ǣ� *  ��1+ȫ�˺���
 * <p>
 * ���У�
 * ��1�����׼��� = ���� * 5 /���ȼ�+9��/ 100
 * ��4��״̬���ˣ�ȼ�����ˡ��ж����ˡ���Ѫ���ˣ����硾ħ��buff�����Լ�һЩӢ�۱���������� ���� ���У�����������3����
 * ��5��ְҵ���ˣ�����ض�ְҵ����˺����ӣ�����5����ͨ��ɫ�������Լ�һЩ��Ӣ�۱��������˹Ī�ꡢѪ�е�
 * ��6�����㻤��2500���ң����������׼���ԼΪ0.3
 * ��7����׼���˲�������150%
 * ��8��ȫ�˺���Դ��Ů��0.2-0.24, �̿�����0-0.3, ʱ������0.135��ʱ��������������0.09��¹��ÿ�غ�9%���׼���ӡÿ�غ�10%
 * ��9������������ÿ�غ�50%��������30%���ˣ�����ÿ�غ�����15%����
 * ��10������������ÿ�غ�45%���ˣ�����5�غϺ�buff�����㾻��
 * <p>
 * user story:
 * ��׼ģ�����Թ����ĺͻ��е������ޣ��Լ�ƽ���˺�����
 * ���㹥�����Σ����Ľ�����Ӱ�찵ս͵������������ʧ���Σ�
 *
 * @author Sean
 *
 *
 *
 * IF(D87>0,((1.5+D92*2)*(D86+D90)+(D87+D90))*D83*(1+D91*0.3)*D102*D94*0.7*(1-D130+D131),(1.5+D92*2)*(D86+D90)*D83*(1+D91*0.3)*D102*D94*0.7*(1-D130+D131))
 */
public class DamageSimulator {
    final Hero[] HEROES = new Hero[6];
    final Random RAND = new Random();

    final Boss BOSS = new Boss();

    static int petEnergy;
    static int dmg;

    final int skillCnt = 5;

    public static void main(String[] args) {
        DamageSimulator test = new DamageSimulator();
        int n = 100000;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            test.init();
            sum += test.simulate();
        }
        String msg = String.format("����%s��ģ�⣬��ս��ʼ���Ϊ%s��, ����Ϊ%sʱ����[%s]+[%s], ƽ���˺�ϵ��Ϊ%s",
                n, test.HEROES[2].initAtt, test.HEROES[2].initHoly, test.HEROES[4].name, test.HEROES[5].name, sum / n);
        System.out.println(msg);
    }

    private void init() {
        HEROES[0] = new Hero("����", Weapon.DEMON_BELL, 6);
        HEROES[1] = new Hero("Ů��", Weapon.DEMON_BELL, 5);
        HEROES[2] = new Hero("��ս", Weapon.ANTLER, 2000, 0.9, 0, 1.5, Arrays.asList(12.0,12.0,12.0,12.0,12.0),4);
        HEROES[3] = new Hero("��Ů", Weapon.LOLIPOP, 3);
        HEROES[4] = new Hero("����", Weapon.LOLIPOP, 2);
//        HEROES[4] = new Hero("����", Weapon.LOLIPOP, 2);
//        HEROES[5] = new Hero("����", Weapon.LOLIPOP, 1);
        HEROES[5] = new Hero("����", Weapon.LOLIPOP, 1);
        Arrays.sort(HEROES, (h1, h2) -> h2.speed - h1.speed);
    }

    /**
     * ģ�Ⲣͳ��15�غϺ������ϵ��
     */
    private int simulate() {
        int totalDmg = 0;
        for (int i = 0; i < 15; i++) {
            dmg = 0;
            simOneRound();
            totalDmg += dmg;
        }
        return totalDmg;
    }

    /**
     * ģ��1�غ�
     */
    private void simOneRound() {
        //Ӣ�۳���
        heroesAttack();

        //�������
        bossAttack();

        //�غϽ�����

        //ȫԱ״̬����
        updateHeroes();

        //�����������
        updateBoss();

        //ħ�޿����ж�
        updatePet();
    }

    /**
     * ħ�޳�����ʽ��
     * �ҷ�����+10������ �غϽ�����100����ħ�޼��ܣ� ����+20����
     */
    private void updatePet() {
        if (petEnergy >= 100) {
            //��ս��2/3�ĸ����յ�ħ�����˼ӳ�
            HEROES[2].poisonDmgPercent += RAND.nextInt(3) < 2 ? 0.8 : 0;
            petEnergy = 0;
        } else {
            petEnergy += 20;
        }
    }

    private void updateBoss() {
        //�����������,  ��������Ѫ��������������ӡ�� 5�ָ���
        int r = RAND.nextInt(5);
        //��roll��0��ʾ��������
        if (r == 0) {
            BOSS.att = 4000000;
        }
        //��roll��1��ʾ����ӡ��
        if (r == 1) {
            BOSS.mark = 0;
        }
    }

    /**
     * ÿ�غϽ��������Ӣ��״̬
     */
    private void updateHeroes() {
        for (Hero hero : HEROES) {
            //����+50
            hero.energy += 50;
            //˫�ز���-1
            hero.control--;
            //���˫��
            hero.isSilent = false;
            hero.isFear = false;
        }
        //������ߵ�λ����100����
        HEROES[2].energy -= 100;
        //��ս¹������
        HEROES[2].fullDmgPercent += 0.09;
    }

    /**
     * Ӣ�۳��֣�����ֻͳ�ư�ս�˺�ϵ��
     */
    private void heroesAttack() {
        for (Hero hero : HEROES) {
            attack(hero);
        }
    }

    /**
     * �������
     */
    private void bossAttack() {
        for (Hero hero : HEROES) {
            hero.energy += 10;
        }
    }

    private void attack(Hero hero) {
        //����ܿ�����û����Ĭ
        if (hero.energy >= 100 && !hero.isSilent) {
            if (hero.weapon == Weapon.DEMON_BELL) {
                charge(hero, RAND.nextInt(2) == 0 ? 20 : 30);
            }
            if(hero.name.equals("Ů��")) {
                BOSS.queenCrit += RAND.nextInt(4) == 3 ? 0.48 : 0.24;
                BOSS.queenCrit = Math.max(BOSS.queenCrit, 1.5);
            }
            if (hero.name.equals("����")) {
                if (hero.mainCnt >= 3) {
                    addPoisonDmg();
                }
            }
            if (hero.name.equals("����")) {
                //����ÿ�ο�����0.5�ļ��ʸ���ս������
                HEROES[2].holyDmgPercent += RAND.nextInt(2) == 0 ? 0.3 : 0;
            }
            if (hero.name.equals("��Ů")) {
                chargeNeighbor();
            }
            if (hero.name.equals("����")) {
                BOSS.attackReducePercent += 0.25;
                BOSS.att *= Math.max(0, (1 - BOSS.attackReducePercent));
                BOSS.mark += 0.45;
                //��������300%
                BOSS.mark = Math.max(BOSS.mark, 3.0);
            }
            if (hero.name.equals("����")) {
                BOSS.fox = 0.4;
            }
            if (hero.name.equals("��ս")) {
                for (Double s : hero.skillSection) {
                    dmg += oneSection(hero, s);
                }
                hero.att += BOSS.att * 0.3;
            }
            //˫�ز���+1
            addControl();
            //ħ�޳���10��
            petEnergy += 10;
            //�����������Ҫ���ڻ�������
            hero.mainCnt++;
            //�������
            hero.energy = 0;
        } else {
            //���û���־壬�չ�����������
            if (!hero.isFear) {
                hero.energy += 50;
                //˫�ز���+1
                addControl();
            }
        }
    }

    private double oneSection(Hero hero, double skillRatio){
        //f = ����ϵ�� * (��ǰ������/����������) *  [1-0.3 * ��1-�Ƽף�+0.7 * ����] * ��1+0.3 * ��׼�� * ��1 + �������ˣ�* �� 1 + ״̬���ˣ�* ��1+����ӡ�ǣ� *  ��1+ȫ�˺���
        double f = (1.0 * hero.att / hero.initAtt)
                * (1 + hero.attPercent) * (0.85 + 0.7 * (1 + hero.holyDmgPercent))
                * (1 + 0.3 * hero.accuracy)
                * (1 + BOSS.fox)
                * (1 + hero.poisonDmgPercent)
                * (1 + BOSS.mark)
                * (1 + hero.fullDmgPercent);

        //���Ǳ��˺�Ů��������
        //crit = f * (1.5 + �����˺� * 2) * ��1 + Ů�������ˣ�
        f *= (1.5 + hero.critPercent * 2) * (1 + BOSS.queenCrit);

        //��������˺�jh = f * [1+0.3*(1+ȫ�˺�+����+��ս) * (1 + ����)]
        double jh = f * (1 + 0.3 * (1 + hero.fullDmgPercent + 1 + 0.3) * (1 + BOSS.fox));
        //���غ����˺�jd = jh * [1 + 0.12* (1+ȫ�˺�+����+��ս) * (1 + ����)]
        double jd = jh * (1 + 0.12 * (1 + hero.fullDmgPercent + 1 + 0.3) * (1 + BOSS.fox));
        return jd;
    }

    private void addPoisonDmg() {
        HEROES[2].attPercent += 0.04;
        HEROES[2].poisonDmgPercent += 0.033;
    }

    private void addControl() {
        for (Hero hero : HEROES) {
            if (++hero.control == 5) {
                //����Ƽ���20%�������ߣ��־�ͳ�Ĭ��������
                hero.isFear = RAND.nextInt(100) < 80;
                hero.isSilent = RAND.nextInt(100) < 80;
                //��ղ���
                hero.control = 0;
            }
        }
    }

    /**
     * ��Ů����
     */
    private void chargeNeighbor() {
        HEROES[2].energy += 100;
    }

    /**
     * ��������
     */
    private void charge(Hero self, int val) {
        for (Hero hero : HEROES) {
            if (hero == self) continue;
            hero.energy += val;
        }
    }
}

enum Weapon {
    ANTLER, DEMON_BELL, LOLIPOP;
}

class Hero {
    String name;
    Weapon weapon;

    int initAtt;
    int att;
    double holyDmgPercent;
    double initHoly;
    double poisonDmgPercent;

    //Ů�� 0.24 װ��0.08 ����0.15
    double fullDmgPercent = 0.24 + 0.08 + 0.15;
    double accuracy;
    int speed;
    int energy;
    boolean isSilent;
    boolean isFear;

    //�����ӳɣ�����ֻ���Ǵ��ӳɣ�����0.25 * 2������0.2 ��ӡ0.12 ͼ5buff 0.3 * 2
    double attPercent = 0.5 + 0.2 + 0.12 + 0.6;

    double critRatio = 0.57;
    double critPercent = 1.5;

    List<Double> skillSection;

    int control;

    //�������
    int mainCnt;

    public Hero(String name, Weapon weapon, int speed) {
        this.name = name;
        this.weapon = weapon;
        this.speed = speed;
    }

    public Hero(String name, Weapon weapon, int att, double holyDmgPercent, double poisonDmgPercent, double accuracy, List<Double> skillSection, int speed) {
        this.name = name;
        this.weapon = weapon;
        this.att = att;
        this.initAtt = att;
        this.holyDmgPercent = holyDmgPercent;
        this.initHoly = holyDmgPercent;
        this.poisonDmgPercent = poisonDmgPercent;
        this.accuracy = accuracy;
        this.skillSection = skillSection;
        this.speed = speed;
    }
}

class Boss {
    int att = 400;
    double mark;
    double fox;
    double attackReducePercent;
    double queenCrit;
}



