package ih.dmage.domain.primitive.buff;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Builder
@Getter
public class FreeControlSnippet implements BuffSnippet {
    private SkillRange range;
    private double ratio;
}
