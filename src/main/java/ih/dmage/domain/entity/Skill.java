package ih.dmage.domain.entity;

import ih.dmage.domain.primitive.DamageTraditionalSnippet;
import ih.dmage.domain.primitive.SkillSnippet;
import ih.dmage.domain.primitive.buff.BuffSnippet;
import ih.dmage.domain.primitive.buff.FreeControlSnippet;
import ih.dmage.domain.primitive.debuff.CritReduceSnippet;
import ih.dmage.domain.primitive.debuff.DebuffSnippet;
import ih.dmage.domain.primitive.debuff.QueenDomainSnippet;
import ih.dmage.domain.primitive.debuff.dot.Dot;
import ih.dmage.domain.primitive.debuff.dot.DotType;
import ih.dmage.domain.primitive.skillrange.friend.RangeFriendRandom;
import ih.dmage.domain.primitive.skillrange.opponent.RangeOpponentRandom;
import ih.dmage.domain.primitive.skillrange.opponent.RangeOpponentSpecificImplAspen;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 一个技能由多"段"技能组成，每段技能的类型不一，可能是：传统，真伤，减益，增益，上印记等等
 *
 * @author Sean Yu
 */

@Getter
public class Skill {
    int id;
    List<SkillSnippet> skillSnippets;


    public Skill(int id) {
        this.id = id;
        this.skillSnippets = getSkillSnippets(id);
    }


    private List<SkillSnippet> getSkillSnippets(int id) {
        switch (id) {
            case 1: return buildQueenSkill();
            case 2: return buildFloraSkill1();
            case 3: return buildFloraSkill2();
            case 4: return buildFloraSkill3();
            default: return null;
        }
    }

    /**
     * 花王第1次大招：
     * 对方全员施加1200%攻击力中毒，持续2回合
     * 第1次开大：加免控率
     */
    private List<SkillSnippet> buildFloraSkill1() {
        //全员施加中毒
        Dot s1 = Dot.builder()
                .dotType(DotType.POISON)
                .round(2)
                .ratio(12)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        //全员增加35%免控率
        BuffSnippet s2 = FreeControlSnippet.builder()
                .ratio(0.35)
                .range(RangeFriendRandom.builder().count(6).build())
                .build();
        return Arrays.asList(s1, s2);
    }

    /**
     * 花王第2次大招：
     * 对方全员施加1200%攻击力中毒，持续2回合
     * 第2次开大：加盾
     */
    private List<SkillSnippet> buildFloraSkill2() {
        //全员施加中毒
        Dot s1 = Dot.builder()
                .dotType(DotType.POISON)
                .round(2)
                .ratio(12)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        //加盾
        //todo
        return Arrays.asList(s1);
    }

    /**
     * 花王第3+次大招：
     * 对方全员施加1200%攻击力中毒，持续2回合
     * 第3次开大：自身每回合增加12%攻击力，对中毒单位增加10%伤害，队友享受1/3效果
     */
    private List<SkillSnippet> buildFloraSkill3() {
        //全员施加中毒
        Dot s1 = Dot.builder()
                .dotType(DotType.POISON)
                .round(2)
                .range(RangeOpponentRandom.builder().count(6).build())
                .ratio(12)
                .build();
        //自身每回合增加12%攻击力
        BuffSnippet s2 = FreeControlSnippet.builder()
                .ratio(0.35)
                .range(RangeFriendRandom.builder().count(6).build())
                .build();
        return Arrays.asList(s1, s2);
    }

    /**
     * 女王大招：
     * 领域爆伤+25%，有25%概率额外增加一层，上限200%
     * 降低对面全员暴击率-10%，2回合
     * 全员流血2回合
     */
    private List<SkillSnippet> buildQueenSkill() {
        //领域爆伤+20%
        DebuffSnippet s1 = QueenDomainSnippet.builder()
                .triggerChance(1)
                .ratio(0.25)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        //有25%概率额外增加1层
        DebuffSnippet s2 = QueenDomainSnippet.builder()
                .triggerChance(0.25)
                .ratio(0.25)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        //施加流血2回合
        DebuffSnippet s3 = Dot.builder()
                .dotType(DotType.BLEED)
                .round(2)
                .ratio(0.12)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        //降低对方暴击率2回合
        DebuffSnippet s4 = CritReduceSnippet.builder()
                .round(2)
                .ratio(0.1)
                .range(RangeOpponentRandom.builder().count(6).build())
                .build();
        return Arrays.asList(s1, s2, s3, s4);
    }

    /**
     * 暗战打血线以上的单位
     * 造成2段1200%攻击伤害，本次攻击具有魔王之威特性： 额外造成前面总伤*1.2倍的伤害，并偷取boss 30%攻击力
     * 本次攻击之后，再对同排敌人造成1200%伤害
     */
    private List<SkillSnippet> buildAspenSkill() {
        //第1段1200
        DamageTraditionalSnippet s1 = DamageTraditionalSnippet.builder()
                .ratio(12)
                .range(RangeOpponentSpecificImplAspen.builder().build())
                .build();
        //第2段1200
        DamageTraditionalSnippet s2 = DamageTraditionalSnippet.builder()
                .ratio(12)
                .range(RangeOpponentSpecificImplAspen.builder().build())
                .build();
        //第3段魔王白字结算
        //todo

        //第4段，降低boss 30%攻

        //第5段，增加自身30%boss当前攻


        //第6段同排输出
        return Arrays.asList(s1, s2);
    }



}
