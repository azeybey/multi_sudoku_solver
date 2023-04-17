import java.util.HashSet;
import java.util.Set;

public class Cell {
    private int val;
    private Set<Integer> domain;

    public Cell (int val) {
        this.val = val;
        this.domain = new HashSet<>();

        if (val == 0) {
            for (int i = 1; i < 10; i++) {
                this.domainAdd(i);
            }
        } else {
            this.domainAdd(val);
        }
    }

    public int getVal() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Set<Integer> getDomain() {
        return this.domain;
    }

    public int getDomainSize() {
        return this.domain.size();
    }

    public void domainAdd(int val) { this.domain.add(val); }

    public void domainCollapse(int val) {
        this.domain.clear();
        this.domain.add(val);
    }

    public void setDomain(Set<Integer> domain) {
        this.domain = domain;
    }
}