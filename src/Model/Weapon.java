package Model;

public class Weapon extends BaseItem {
    protected int dexRequierment;
    protected int intRequierment;
    protected int strRequierment;
    protected double atkMod;

    public double getAtkMod() {
        return this.atkMod;
    }
}
