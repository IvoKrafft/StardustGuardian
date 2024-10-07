package Model;

import java.util.List;

public class Shop {
    private Inventory waresToSell;

    Shop(){
        waresToSell = new Inventory();
    }

    //MACAM
    public static Shop makeShop(List<Integer> itemIds){
        Shop returnValue = new Shop();
        returnValue.waresToSell.addItemListPerId(itemIds);
        return returnValue;
    }

    //MACAM
    public String showInventory(){
        List<Item> items = this.waresToSell.getItemList();
        String ausgabe = "Der Shop hat folgende waren anzubieten: \n";
        for(int i=0;i < items.size();i++){
            ausgabe += i+1 + ". " + items.get(i).getName() + " x" + items.get(i).getAmount() + "\n";
        }
        return ausgabe;
    }

    public Inventory getWaresToSell() {
        return waresToSell;
    }
}
