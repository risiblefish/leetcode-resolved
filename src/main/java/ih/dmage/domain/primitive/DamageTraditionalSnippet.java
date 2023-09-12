package ih.dmage.domain.primitive;

import ih.dmage.domain.primitive.skillrange.SkillRange;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class DamageTraditionalSnippet implements SkillSnippet{
    private boolean isTrue = false;
    private double ratio;
    private SkillRange range;
}