package ih.dmage.domain.primitive.debuff;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Builder
@Getter
public class CritReduceSnippet implements DebuffSnippet{
    private int round;
    private SkillRange range;
    private double ratio;
}
