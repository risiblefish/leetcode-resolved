package ih.dmage.domain.primitive.debuff.dot;

import ih.dmage.domain.primitive.debuff.DebuffSnippet;
import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Getter
@Builder
public class Dot implements DebuffSnippet {
    private DotType dotType;
    private int round;
    private double ratio;
    private SkillRange range;
}

