package ih.dmage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 令D为攻方技能单段伤害
 * 伤害公式：
 * f = D * [1-护甲减免 * （1-破甲）+0.7 * 神伤] * （1+0.3 * 精准） * （1 + 狐狸增伤）* （ 1 + 状态增伤 + 职业增伤）* （1+观心印记+虚弱印记） *  （1+全伤害）
 * <p>
 * 其中，
 * （1）护甲减免 = 护甲 * 5 /（等级+9）/ 100
 * （4）状态增伤：燃烧增伤、中毒增伤、流血增伤，比如【魔兽buff】，以及一些英雄被动，如火男 牙姐 毒男，【花王被动3】等
 * （5）职业增伤：针对特定职业造成伤害增加，比如5种普通橙色神器，以及一些老英雄被动，如达斯莫戈、血刃等
 * （6）章鱼护甲2500左右，算下来护甲减免约为0.3
 * （7）精准增伤部分上限150%
 * （8）全伤害来源：女王0.2-0.24, 刺客星灵0-0.3, 时空星灵0.135，时空游侠合体星灵0.09，鹿角每回合9%，献祭星印每回合10%
 * （9）满配队伍基佬每回合50%概率增加30%神伤，期望每回合增加15%神伤
 * （10）满配队伍关心每回合45%增伤，期望5回合后buff被章鱼净化
 * <p>
 * user story:
 * 精准模拟评以估关心和基佬的上下限，以及平均伤害收益
 * 章鱼攻击几何？关心降攻会影响暗战偷攻，带来的损失几何？
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
        String msg = String.format("经过%s次模拟，暗战初始面板为%s万, 神伤为%s时，带[%s]+[%s], 平均伤害系数为%s",
                n, test.HEROES[2].initAtt, test.HEROES[2].initHoly, test.HEROES[4].name, test.HEROES[5].name, sum / n);
        System.out.println(msg);
    }

    private void init() {
        HEROES[0] = new Hero("花王", Weapon.DEMON_BELL, 6);
        HEROES[1] = new Hero("女王", Weapon.DEMON_BELL, 5);
        HEROES[2] = new Hero("暗战", Weapon.ANTLER, 2000, 0.9, 0, 1.5, Arrays.asList(12.0,12.0,12.0,12.0,12.0),4);
        HEROES[3] = new Hero("龙女", Weapon.LOLIPOP, 3);
        HEROES[4] = new Hero("狐狸", Weapon.LOLIPOP, 2);
//        HEROES[4] = new Hero("关心", Weapon.LOLIPOP, 2);
//        HEROES[5] = new Hero("关心", Weapon.LOLIPOP, 1);
        HEROES[5] = new Hero("基佬", Weapon.LOLIPOP, 1);
        Arrays.sort(HEROES, (h1, h2) -> h2.speed - h1.speed);
    }

    /**
     * 模拟并统计15回合后的增伤系数
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
     * 模拟1回合
     */
    private void simOneRound() {
        //英雄出手
        heroesAttack();

        //章鱼出手
        bossAttack();

        //回合结束后

        //全员状态更新
        updateHeroes();

        //章鱼随机净化
        updateBoss();

        //魔兽开大判定
        updatePet();
    }

    /**
     * 魔兽充能形式：
     * 我方开大+10能量， 回合结束满100触发魔兽技能， 否则+20能量
     */
    private void updatePet() {
        if (petEnergy >= 100) {
            //暗战有2/3的概率收到魔兽增伤加成
            HEROES[2].poisonDmgPercent += RAND.nextInt(3) < 2 ? 0.8 : 0;
            petEnergy = 0;
        } else {
            petEnergy += 20;
        }
    }

    private void updateBoss() {
        //章鱼随机净化,  共毒，流血，降暴，降攻，印记 5种负面
        int r = RAND.nextInt(5);
        //令roll到0表示净化降攻
        if (r == 0) {
            BOSS.att = 4000000;
        }
        //令roll到1表示净化印记
        if (r == 1) {
            BOSS.mark = 0;
        }
    }

    /**
     * 每回合结束后更新英雄状态
     */
    private void updateHeroes() {
        for (Hero hero : HEROES) {
            //能量+50
            hero.energy += 50;
            //双控层数-1
            hero.control--;
            //解除双控
            hero.isSilent = false;
            hero.isFear = false;
        }
        //攻击最高单位减少100能量
        HEROES[2].energy -= 100;
        //暗战鹿角增伤
        HEROES[2].fullDmgPercent += 0.09;
    }

    /**
     * 英雄出手，这里只统计暗战伤害系数
     */
    private void heroesAttack() {
        for (Hero hero : HEROES) {
            attack(hero);
        }
    }

    /**
     * 章鱼出手
     */
    private void bossAttack() {
        for (Hero hero : HEROES) {
            hero.energy += 10;
        }
    }

    private void attack(Hero hero) {
        //如果能开大，且没被沉默
        if (hero.energy >= 100 && !hero.isSilent) {
            if (hero.weapon == Weapon.DEMON_BELL) {
                charge(hero, RAND.nextInt(2) == 0 ? 20 : 30);
            }
            if(hero.name.equals("女王")) {
                BOSS.queenCrit += RAND.nextInt(4) == 3 ? 0.48 : 0.24;
                BOSS.queenCrit = Math.max(BOSS.queenCrit, 1.5);
            }
            if (hero.name.equals("花王")) {
                if (hero.mainCnt >= 3) {
                    addPoisonDmg();
                }
            }
            if (hero.name.equals("基佬")) {
                //基佬每次开大有0.5的几率给暗战加神伤
                HEROES[2].holyDmgPercent += RAND.nextInt(2) == 0 ? 0.3 : 0;
            }
            if (hero.name.equals("龙女")) {
                chargeNeighbor();
            }
            if (hero.name.equals("关心")) {
                BOSS.attackReducePercent += 0.25;
                BOSS.att *= Math.max(0, (1 - BOSS.attackReducePercent));
                BOSS.mark += 0.45;
                //关心上限300%
                BOSS.mark = Math.max(BOSS.mark, 3.0);
            }
            if (hero.name.equals("狐狸")) {
                BOSS.fox = 0.4;
            }
            if (hero.name.equals("暗战")) {
                for (Double s : hero.skillSection) {
                    dmg += oneSection(hero, s);
                }
                hero.att += BOSS.att * 0.3;
            }
            //双控层数+1
            addControl();
            //魔兽充能10点
            petEnergy += 10;
            //开大次数，主要用于花王计算
            hero.mainCnt++;
            //能量清空
            hero.energy = 0;
        } else {
            //如果没被恐惧，普攻加自身能量
            if (!hero.isFear) {
                hero.energy += 50;
                //双控层数+1
                addControl();
            }
        }
    }

    private double oneSection(Hero hero, double skillRatio){
        //f = 技能系数 * (当前攻击力/进场攻击力) *  [1-0.3 * （1-破甲）+0.7 * 神伤] * （1+0.3 * 精准） * （1 + 狐狸增伤）* （ 1 + 状态增伤）* （1+观心印记） *  （1+全伤害）
        double f = (1.0 * hero.att / hero.initAtt)
                * (1 + hero.attPercent) * (0.85 + 0.7 * (1 + hero.holyDmgPercent))
                * (1 + 0.3 * hero.accuracy)
                * (1 + BOSS.fox)
                * (1 + hero.poisonDmgPercent)
                * (1 + BOSS.mark)
                * (1 + hero.fullDmgPercent);

        //考虑爆伤和女王领域爆伤
        //crit = f * (1.5 + 暴击伤害 * 2) * （1 + 女王领域爆伤）
        f *= (1.5 + hero.critPercent * 2) * (1 + BOSS.queenCrit);

        //均衡后总伤害jh = f * [1+0.3*(1+全伤害+巨人+挑战) * (1 + 狐狸)]
        double jh = f * (1 + 0.3 * (1 + hero.fullDmgPercent + 1 + 0.3) * (1 + BOSS.fox));
        //绝地后总伤害jd = jh * [1 + 0.12* (1+全伤害+巨人+挑战) * (1 + 狐狸)]
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
                //工会科技有20%概率免疫，恐惧和沉默独立计算
                hero.isFear = RAND.nextInt(100) < 80;
                hero.isSilent = RAND.nextInt(100) < 80;
                //清空层数
                hero.control = 0;
            }
        }
    }

    /**
     * 龙女充能
     */
    private void chargeNeighbor() {
        HEROES[2].energy += 100;
    }

    /**
     * 铃铛充能
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

    //女王 0.24 装备0.08 星灵0.15
    double fullDmgPercent = 0.24 + 0.08 + 0.15;
    double accuracy;
    int speed;
    int energy;
    boolean isSilent;
    boolean isFear;

    //攻击加成，这里只考虑大额加成，柯兹0.25 * 2，花王0.2 星印0.12 图5buff 0.3 * 2
    double attPercent = 0.5 + 0.2 + 0.12 + 0.6;

    double critRatio = 0.57;
    double critPercent = 1.5;

    List<Double> skillSection;

    int control;

    //开大次数
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



