package Model;

import java.util.List;

public class PlayerCharacter extends Character implements PlayerCharacterEP {
    private int progressPoints;
    private int skillPoints;
    private Inventory backpack;

    public PlayerCharacter(){
        super();
        this.backpack = new Inventory();
        this.skillPoints = 0;
        this.progressPoints = 0;
    }

    //MACAM
    static public PlayerCharacter makeCharacter(int charID) {
        PropertyManager manager = PropertyManager.getPropertyManager();
        PlayerCharacter pC = new PlayerCharacter();

        String charF = PropertyManager.CHARACTER_FILE_NAME;

        pC.name = manager.getProperty(charF,charID + "." + PropertyManager.characterName);
        pC.healthPoints = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.hp));
        pC.dexterity = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.dexterity));
        pC.strength = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.strength));
        pC.intelligence = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.intelligence));
        pC.gold = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.gold));
        pC.progressPoints = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.progressPoints));
        pC.skillPoints = Integer.parseInt(manager.getProperty(charF,charID + "." + PropertyManager.skillPoints));
        pC.equippedInventory = Inventory.addStartingItems(charID);
        pC.backpack = Inventory.addStartingBackpack(charID);
        return pC;
    }

    //MACAM
    //Maximal 1 Waffe und 1 Ruestung tragbar // Item muss aus dem Backpack kommen bzw drin sein
    public void equipItem(Item item){
        //Was fuer ein Item soll benutzt werden
        String typ = item.getItemTyp();

        if(typ.equals("Weapon") || typ.equals("Armor")) {
            for (int i = 0; i < equippedInventory.getItemList().size(); i++) {
                if (equippedInventory.getItemList().get(i).getItemTyp().equals(typ)) {
                    //Erst die Tragende Waffe ins Inventar
                    backpack.addSingleItem(equippedInventory.getItemList().get(i));
                    //Dann die Tragende Waffe entfernen
                    equippedInventory.removeItem(equippedInventory.getItemList().get(i));
                    //Neue waffe anziehen
                    equippedInventory.addSingleItem(item);
                    //Neue Waffe aus dem Inventar entfernen
                    backpack.removeItem(item);
                }
            }
        }
    }

    //MACAM
    public static int getDataSet(){
        PropertyManager manager = PropertyManager.getPropertyManager();
        return Integer.parseInt(manager.getProperty(PropertyManager.CHARACTER_FILE_NAME,PropertyManager.DATASET_COUNT));
    }

    //MACAM
    //Ausgaben folgen ab hier
    public String showEquippedItems(){
        List<Item> items = this.equippedInventory.getItemList();
        StringBuilder ausgabe = new StringBuilder("Die aktuelle Ausruestung die der Charakter benutzt: \n");
        for(int i=0;i < items.size();i++){
            ausgabe.append(i+1).append(". ").append(items.get(i).getName()).append("\n");
        }
        return ausgabe.toString();
    }

    //MACAM
    public String showInventory(){
        List<Item> items = this.backpack.getItemList();
        String ausgabe = "Inventar: \n";
        for(int i=0;i < items.size();i++){
           ausgabe += i+1 + ". " + items.get(i).getName() + " x" + items.get(i).getAmount() + "\n";
        }
        return ausgabe;
    }

    //MACAM
    public String showCharStats(){
        String ausgabe = "Dieser Charakter hat folgende Eigenschaften: \n";
        ausgabe += "Name: " + this.getName() + "\n";
        ausgabe += "Lebenspunkte: " + this.getHealthPoints() + "\n";
        ausgabe += "Geschicklichkeit: " + this.getDexterity()+ "\n";
        ausgabe += "Staerke: " + this.getStrength()+ "\n";
        ausgabe += "Intelligenz: " + this.getIntelligence()+ "\n";
        ausgabe += "Gold: " + this.getGold()+ "\n";
        ausgabe += "Aktuelle Skillpunkte: " + this.getSkillPoints() + "\n";
        return ausgabe;
    }

    //MACAM
    public static String showCharStats(int charID){
        PlayerCharacter tmp = makeCharacter(charID);

        String ausgabe = "Dieser Charakter hat folgende Eigenschaften: \n";
        ausgabe += "Name: " + tmp.getName() + "\n";
        ausgabe += "Lebenspunkte: " + tmp.getHealthPoints() + "\n";
        ausgabe += "Geschicklichkeit: " + tmp.getDexterity()+ "\n";
        ausgabe += "Staerke: " + tmp.getStrength()+ "\n";
        ausgabe += "Intelligenz: " + tmp.getIntelligence()+ "\n";
        ausgabe += "Gold: " + tmp.getGold()+ "\n";

        ausgabe += "\nMoechtest du diesen Charakter erstellen? (1=Ja)";
        return ausgabe;
    }

    //MACAM
    public static String showCharacterSelection() {
        //TODO
        PropertyManager manager = PropertyManager.getPropertyManager();
        String[] availableCharacters = manager.getProperty(PropertyManager.CHARACTER_FILE_NAME,-1 + "." + PropertyManager.characters).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        String ausgabe = "Diese Charaktere stehen zur Auswahl: \n";

        for(int i=0;i < availableCharacters.length;i++){
            ausgabe += i+1 + ". " + availableCharacters[i] + "\n";
        }
        ausgabe += "\n" + "Bitte waehle einen Charakter aus";
        return ausgabe;
    }

    public Inventory getBackpack() {
        return backpack;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getProgressPoints() {
        return progressPoints;
    }

    //IKRAFFT
    public void setProgressPoints(int progressPoints) {
        this.progressPoints = progressPoints;
    }

    //IKRAFFT
    public void addProgressPoints(int progressPoints) {
        this.progressPoints += progressPoints;
    }

    //IKRAFFT
    public int getAbilities(String name) {
        if (name.equals("STRENGTH")){
            return this.getStrength();
        }
        if (name.equals("DEXTERITY")){
            return this.getDexterity();
        }
        if (name.equals("INTELLIGENCE")){
            return this.getIntelligence();
        }

        return 0;
    }

    //IKRAFFT
    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    //IKRAFFT
    public void addSkillPoints(int skillPoints) {
        this.skillPoints += skillPoints;
    }

    //IKRAFFT
    public void levelDexterity(){this.dexterity ++;}

    //IKRAFFT
    public void levelStrength(){this.strength ++;}

    //IKRAFFT
    public void levelIntelligence(){this.intelligence ++;}

    //IKRAFFT
    public void setBackpack(Inventory backpack) {
        this.backpack = backpack;
    }

    //IKRAFFT
    public void addItem(Item item) {
        this.backpack.getItemList().add(item);
    }

    //IKRAFFT
    public void addGold(int gold) {
        this.gold += gold;
    }
}
