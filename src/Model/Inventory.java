package Model;

import java.util.ArrayList;
import java.util.List;


public class Inventory implements Item {
    private List<Item> itemList;

    public Inventory(){
        this.itemList = new ArrayList<>();
    }

    //MACAM
    public static Inventory addStartingItems(int itemID){
        Inventory returnValue = new Inventory();
        List<Item> itemList = new ArrayList();
        PropertyManager manager = PropertyManager.getPropertyManager();

        String itemF = PropertyManager.ITEM_FILE_NAME;
        String[] EquippedItems = manager.getProperty(itemF, itemID + "." + PropertyManager.startingItemsEquipped).
                split(PropertyManager.DATASET_SEPARATION_SIGN);

        for (String equippedItem : EquippedItems) {
            itemList.add(BaseItem.makeItem(Integer.parseInt(equippedItem)));
        }
        returnValue.itemList = itemList;

        return returnValue;
    }

    //MACAM
    public static Inventory addStartingBackpack(int itemID){
        Inventory returnValue = new Inventory();
        List<Item> itemList = new ArrayList();
        PropertyManager manager = PropertyManager.getPropertyManager();

        String itemF = PropertyManager.ITEM_FILE_NAME;
        String[] EquippedItems = manager.getProperty(itemF, itemID + "." + PropertyManager.startingItemsBackpack).
                split(PropertyManager.DATASET_SEPARATION_SIGN);

        for (String equippedItem : EquippedItems) {
            itemList.add(BaseItem.makeItem(Integer.parseInt(equippedItem)));
        }
        returnValue.itemList = itemList;

        return returnValue;
    }

    //MACAM
    public void addItems(List<Item> newItemList){
        this.itemList.addAll(newItemList);
    }

    //MACAM
    public void addItemListPerId(List<Integer> integerItemList){
        for(Integer i : integerItemList){
            this.itemList.add(BaseItem.makeItem(i));
        }
    }

    //MACAM
    public void addSingleItem(Item item){
            this.itemList.add(item);
    }

    //MACAM
    public void removeItem(Item item){
        if(findItem(item)) {
            this.itemList.remove(item);
        }
    }

    //MACAM
    public boolean findItem(Item item){
        return this.itemList.contains(item);
    }

    //MACAM
    public Item getItemByIndex(int index){
        return this.getItemList().get(index);
    }

    public List<Item> getItemList() {
        return itemList;
    }

    //Methoden werden nicht genutzt sondern die Methoden von BaseItem
    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public String getItemTyp() {
        return null;
    }

    @Override
    public int getscaling() {
        return 0;
    }

    @Override
    public double getAtkMod() {
        return 0;
    }

    @Override
    public int getGoldValue() {
        return 0;
    }
}
