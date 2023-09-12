package ih.dmage.domain.entity;

import lombok.Data;

/**
 * @author Sean Yu
 */
@Data
public class BattleModel {
    String name;
    Weapon weapon;

    int initAtt;
    double holyDmgRatio;
    double initHoly;
    double poisonDmgRatio;

    //女王 0.24 装备0.08 星灵0.15
    double allDmgRatio = 0.24 + 0.08 + 0.15;
    double accuracy;
    int speed;

    //攻击加成，这里只考虑大额加成，柯兹0.25 * 2，花王0.2 星印0.12 图5buff 0.3 * 2
    double attRatio = 0.5 + 0.2 + 0.12 + 0.6;

    double critChance = 0.57;
    double critRatio = 1.5;

    double abyssCritRatio = 0;

    Skill skill;
}
