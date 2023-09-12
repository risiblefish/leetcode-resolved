package ih.dmage.domain.primitive.skillrange.opponent;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Builder
@Getter
public class RangeOpponentRandom implements SkillRange {
    private int count;
}
