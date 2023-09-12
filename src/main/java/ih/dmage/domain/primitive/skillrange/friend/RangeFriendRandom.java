package ih.dmage.domain.primitive.skillrange.friend;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Builder
@Getter
public class RangeFriendRandom implements SkillRange {
    int count;
}
