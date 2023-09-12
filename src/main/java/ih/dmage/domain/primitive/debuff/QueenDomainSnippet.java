package ih.dmage.domain.primitive.debuff;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Sean Yu
 */
@Getter
@Builder
public class QueenDomainSnippet implements DebuffSnippet{
    private double triggerChance;
    private SkillRange range;
    private double ratio;
}
