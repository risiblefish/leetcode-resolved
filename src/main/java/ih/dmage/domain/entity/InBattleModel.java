package ih.dmage.domain.entity;

import ih.dmage.domain.primitive.debuff.Debuff;
import ih.dmage.domain.primitive.debuff.dot.Dot;
import lombok.Data;

import java.util.List;
import java.util.Queue;

/**
 * 处于战斗场景中的人物模型
 * 额外多了 能量，控制情况，站位等信息
 */
@Data
public class InBattleModel extends BattleModel{
    int att;
    int energy;
    boolean isSilent;
    boolean isFear;
    int position;
    Queue<Dot> bleedQ;
    Queue<Dot> poisonQ;

    List<Queue<Debuff>> debuffList;

    public void charge(int val){
        this.energy += val;
    }

    public void addDot(Dot dot){
        switch (dot.getDotType()){
            case BLEED: bleedQ.add(dot); break;
            case POISON: poisonQ.add(dot); break;
        }
    }

    /**
     * 增加一层深渊领域
     */
    public void addOneAbyssDomain(){
        //todo 这里先默认点了根源
        reduceCritRatio(0.24);
        this.abyssCritRatio = Math.max(2, this.abyssCritRatio + 0.24);
    }

    /**
     * 降低爆伤
     */
    public void reduceCritRatio(double val){
        this.critRatio = Math.max(0, this.critRatio - val);
    }

    /**
     * 降低暴击率
     */
    public void reduceCritChance(double val){
        this.critChance = Math.max(0, this.critChance - val);
    }

    /**
     * 获得能量
     */
    public void receiveEnergy(int val){
        this.energy += val;
    }

    /**
     * 释放能量
     */
    public void releaseEnergy(){
        this.energy = 0;
    }

    /**
     * 回合结束后，更新自身buff, debuff状态
     */
    public void roundEndFlush(){

    }


}


