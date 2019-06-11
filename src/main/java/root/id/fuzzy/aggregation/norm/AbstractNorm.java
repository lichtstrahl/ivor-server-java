package root.id.fuzzy.aggregation.norm;

public abstract class AbstractNorm implements Norm {
    private Type type;
    private Norm dual;

    protected AbstractNorm(Type type, Norm dual) {
        this.type = type;
        this.dual = dual;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public Norm duality() {
        return dual;
    }
}
