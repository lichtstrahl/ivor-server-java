package root.id.fuzzy.defuzzify;


public abstract class IterativeDefuzzificator implements Defuzzificator {
    private int countIter = 100;

    public void setCountIter(int count) {
        countIter = count;
    }

    public int getCountIter() {
        return countIter;
    }
}
