package ih.dmage.domain.primitive;

import lombok.Builder;
import lombok.Getter;

/**
 * 表示"真伤"，真伤只会是白字，不会触发暴击，只受全伤害加成
 */
@Builder
@Getter
public class DamageTrueSnippet implements SkillSnippet {

    double ratio;
    TrueDamageBase base;
    //上限： 0表示无上限
    double limit;

    /**
     * 本段伤害基数是什么，目前只有真伤用到这个参数
     */
    enum TrueDamageBase {
        SELF_ATTACK, OPPO_HP
    }

}