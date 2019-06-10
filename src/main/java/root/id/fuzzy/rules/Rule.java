package root.id.fuzzy.rules;

import root.id.fuzzy.input.InputInstanceAccessor;

public interface Rule {
    double apply(InputInstanceAccessor accessor);
}
