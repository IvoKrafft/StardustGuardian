package Model;

import java.util.ArrayList;
import java.util.List;

public class Event implements ViewEvent,EventEP{
    private int progressPoints;
    private int skillPoints;
    private Abilities abilities;
    private String name;
    private String textDialogWarmup;
    private List<String> diaglogOptions;
    private String startText;
    private String endText;
    private String treasureText;
    private String abilityFailureText;
    private boolean interactiv;
    private int abilitycheck;
    private PropertyManager propertyManager;

    private Shop shop;
    private List<EnemyCharacter> enemyCharacters;
    private List<Item> items;



    public Event(){
        this.init();
    }

    //JGREWE
    private void init(){
        this.diaglogOptions = new ArrayList<>();
        this.items = new ArrayList<>();
        this.propertyManager = PropertyManager.getPropertyManager();
        this.enemyCharacters = new ArrayList<>();
    }


    //JGREWE
    public Event createTreasure(List<Integer> ids, String treasureText){
        for (int i = 0; i < ids.size(); i++) {
            this.items.add(BaseItem.makeItem(ids.get(i)));
        }
        this.treasureText = treasureText;
        return this;
    }

    //JGREWE
    public Event abilityCheck(Abilities ability, int abilitycheck, String abilityFailureText){
        this.abilities = ability;
        this.abilitycheck = abilitycheck;
        this.abilityFailureText = abilityFailureText;
        return this;
    }

    //JGREWE
    public Event createEnemies(List<Integer> ids) {
        for (Integer i: ids) {
            this.enemyCharacters.add(EnemyCharacter.makeEnemy(i));
            this.skillPoints += Integer.parseInt(this.propertyManager.getProperty(PropertyManager.ENEMY_FILE_NAME, i + "." + PropertyManager.skillPoints));
            this.progressPoints += Integer.parseInt(this.propertyManager.getProperty(PropertyManager.ENEMY_FILE_NAME, i + "." + PropertyManager.progressPoints));
        }
        return this;
    }



    //JGREWE
    public Event dialogTexts(int locationID){
        this.interactiv = true;
        this.textDialogWarmup = this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME,locationID + "." + PropertyManager.LOCATION_DIALOG_WARMUP_TEXT);
        String texts = this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME,locationID + "." + PropertyManager.LOCATION_DIALOG_OPTIONS_TEXT);
        if (texts == null || texts.length() == 0){
            return this;
        }
        String parts[] = texts.split(PropertyManager.DATASET_SEPARATION_SIGN);
        for (int i = 0; i < parts.length; i++) {
            this.setDialogOptionsByID(i, "(" + (i + 1) +") " + parts[i]);
        }
        return this;
    }

    public Event standardTexts(int locationID){
        this.name = this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME,locationID + "." + PropertyManager.LOCATION_NAME);
        this.startText = this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_START_TEXT);
        this.endText = this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_END_TEXT);
        return this;
    }


    //JGREWE
    public Event createShop(List<Integer> ids){
        this.interactiv = true;
        this.shop = Shop.makeShop(ids);
        return this;
    }


    //JGREWE
    @Override
    public String getDiaglogOptions(int i) {
        return this.diaglogOptions.get(i);
    }

    //JGREWE
    @Override
    public String getStartText() {
        return startText;
    }

    //JGREWE
    @Override
    public String getEndText() {
        return endText;
    }

    //JGREWE
    private void setDialogOptionsByID(int id, String s){
        if (this.diaglogOptions.size()>=0){
            this.diaglogOptions.add(s);
        } else{
            this.diaglogOptions.set(0,s);
        }
    }

    //JGREWE
    public void setName(String name) {
        this.name = name;
    }

    //JGREWE
    private void fillItems(List<Integer>ids) {
        for (Integer i : ids) {
            this.items.add(BaseItem.makeItem(i));
        }
    }

    //IKRAFFT
    public int getSkillPoints() {
        return skillPoints;
    }

    //IKRAFFT
    public String getTextDialogWarmup() {
        return textDialogWarmup;
    }

    //IKRAFFT
    public List<String> getDiaglogOptions() {
        return diaglogOptions;
    }

    //IKRAFFT
    public int getAbilitycheck() {
        return abilitycheck;
    }

    //IKRAFFT
    public String getName() {
        return name;
    }

    //IKRAFFT
    public int getProgressPoints() {
        return progressPoints;
    }

    //IKRAFFT
    public Abilities getAbilities() {
        return abilities;
    }

    //IKRAFFT
    public boolean isInteractiv() {
        return interactiv;
    }

    //IKRAFFT
    public Shop getShop() {
        return shop;
    }

    //IKRAFFT
    public List<EnemyCharacter> getEnemyCharacters() {
        return enemyCharacters;
    }

    //IKRAFFT
    public List<Item> getItems() {
        return items;
    }

    //IKRAFFT
    public String getTreasureText() {
        return treasureText;
    }

    //IKRAFFT
    public String getAbilityFailureText() {
        return abilityFailureText;
    }
}
