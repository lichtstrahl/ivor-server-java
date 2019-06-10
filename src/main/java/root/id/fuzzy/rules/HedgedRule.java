package root.id.fuzzy.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import root.id.fuzzy.input.InputInstanceAccessor;

@AllArgsConstructor
@Data
public class HedgedRule implements Rule {
    private final FuzzyRule inner;
    private final Hedge hedge;

    @Override
    public double apply(InputInstanceAccessor accessor) {
        return hedge.apply(inner.apply(accessor));
    }

    @Override
    public String toString() {
        return hedge + "(" + inner + ")";
    }
}
