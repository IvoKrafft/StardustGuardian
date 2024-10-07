package Model;

import Model.Inventory;

public abstract class Character {
    protected String name;
    protected int healthPoints;
    protected int dexterity;
    protected int strength;
    protected int intelligence;
    protected int gold;
    protected Inventory equippedInventory;


    public Character(){
        this.equippedInventory = new Inventory();
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Inventory getEquippedInventory() {
        return equippedInventory;
    }

    public void setEquippedInventory(Inventory equippedInventory) {
        this.equippedInventory = equippedInventory;
    }

    public String getName() {
        return name;
    }

    //MACAM
    public double atkDamage(){
        //Inventar durchsuchen
        for (int i = 0; i < equippedInventory.getItemList().size(); i++) {
            //Check waffe
            if (equippedInventory.getItemList().get(i).getItemTyp().equals("Weapon")) {
                //Scaling von waffe holen
                int scaling = equippedInventory.getItemList().get(i).getscaling();
                double waffenSchaden = equippedInventory.getItemList().get(i).getAtkMod();

                //Schaden ausrechnen und zurueckgeben
                switch (scaling){ //0 = dex , 1 = str , 2 = int
                    case 0:
                        return waffenSchaden + this.dexterity;
                    case 1:
                        return waffenSchaden + this.strength;
                    case 2:
                        return waffenSchaden + this.intelligence;
                }
            } else {
                return this.strength; //Default wert
            }
        }
        return 0;
    }


    //IKRAFFT
    public void heal(int hp){
        this.healthPoints += hp;
    }

    //IKRAFFT
    public void damage(int hp){
        this.healthPoints -= hp;
    }
}
