package Model;

public class BaseItem implements Item {
    protected String name;
    protected int goldValue;
    protected int amount;
    protected String itemTyp;
    protected int scaling;

    //MACAM
    public static Item makeItem(int itemId){
        PropertyManager manager = PropertyManager.getPropertyManager();
        String itemF = PropertyManager.ITEM_FILE_NAME;
        String itemTyp = manager.getProperty(itemF, itemId+"."+PropertyManager.itemTyp);

        switch (itemTyp) {
            case "BaseItem":
                BaseItem baseItem = new BaseItem();
                baseItem.itemTyp = manager.getProperty(itemF, itemId + "." + PropertyManager.itemTyp);
                baseItem.name = manager.getProperty(itemF, itemId + "." + PropertyManager.itemName);
                baseItem.goldValue = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.goldValue));
                baseItem.amount = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.amount));
                return baseItem;
            case "Weapon":
                Weapon weapon = new Weapon();
                weapon.itemTyp = manager.getProperty(itemF,itemId + "." + PropertyManager.itemTyp);
                weapon.name = manager.getProperty(itemF, itemId + "." + PropertyManager.itemName);
                weapon.dexRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.dexReq));
                weapon.strRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.strReq));
                weapon.intRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.strReq));
                weapon.atkMod = Double.parseDouble(manager.getProperty(itemF, itemId + "." + PropertyManager.atkMod));
                weapon.goldValue = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.goldValue));
                weapon.amount = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.amount));
                weapon.scaling = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.scaling));
                return weapon;
            case "Armor":
                Armor armor = new Armor();
                armor.itemTyp = manager.getProperty(itemF,itemId + "." + PropertyManager.itemTyp);
                armor.name = manager.getProperty(itemF, itemId + "." + PropertyManager.itemName);
                armor.dexRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.dexReq));
                armor.strRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.strReq));
                armor.intRequierment = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.strReq));
                armor.defMod = Double.parseDouble(manager.getProperty(itemF, itemId + "." + PropertyManager.defMod));
                armor.goldValue = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.goldValue));
                armor.amount = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.amount));
                return armor;
            case "Potion":
                Potion potion = new Potion();
                potion.itemTyp = manager.getProperty(itemF,itemId + "." + PropertyManager.itemTyp);
                potion.name = manager.getProperty(itemF, itemId + "." + PropertyManager.itemName);
                potion.goldValue = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.goldValue));
                potion.amount = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.amount));
                potion.healpoints = Integer.parseInt(manager.getProperty(itemF, itemId + "." + PropertyManager.healpoints));
                return potion;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(int goldValue) {
        this.goldValue = goldValue;
    }

    public String getItemTyp() {
        return itemTyp;
    }

    @Override
    public int getscaling() {
        return this.scaling;
    }

    @Override
    public double getAtkMod() {
        return 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

}
