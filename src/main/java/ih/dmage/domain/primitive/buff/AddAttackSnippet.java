package ih.dmage.domain.primitive.buff;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;

/**
 * @author Sean Yu
 */
@Builder
public class AddAttackSnippet implements BuffSnippet{
    SkillRange range;
}
