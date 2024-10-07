package Model;

import Tools.SmallLogger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

//@Singleton
public class PropertyManager implements Property {
    public static final PropertyManager SINGLETON = new PropertyManager();
    public FileReader fr;
    public Properties p = new Properties();

    public final static String DATASET_SEPARATION_SIGN = ";";
    public final static String DATASET_COUNT = "DS_Count";
    public final static String DATASET_PROBABILITY = "probability";

    //Game Prompts
    public final static String GAME_PROMPTS_FILE_NAME = "GamePrompts";
    public final static String GAME_ART = "gameArt";

    //Good Events
    public final static String GOOD_EVENT_FILE_NAME = "GoodEvent";
    public final static String GOOD_EVENT_LOCATION_IDS = "locationIDs";
    public final static String GOOD_EVENT_ITEM_IDS = "itemIDs";

    //Enemy Event
    public final static String ENEMY_EVENT_FILE_NAME = "EnemyEvent";
    public final static String ENEMY_EVENT_LOCATION_IDS = "locationIDs";
    public final static String ENEMY_EVENT_ITEM_IDS = "itemIDs";
    public final static String ENEMY_EVENT_ENEMY_IDS = "enemyIDs";

    //Neutral Event
    public final static String NEUTRAL_EVENT_FILE_NAME = "NeutralEvent";
    public final static String NEUTRAL_EVENT_LOCATION_IDS = "locationIDs";
    public final static String NEUTRAL_EVENT_ITEM_IDS = "itemIDs";

    //Preset Events
    public final static String PRESET_EVENT_DETAILS_FILE_NAME = "PresetEventDetails";
    public final static String PRESET_EVENT_LOCATION_ID = "locationID";
    public final static String PRESET_EVENT_ITEM_IDS = "itemIDs";
    public final static String PRESET_EVENT_ENEMY_ID = "enemyID";
    public final static String PRESET_EVENT_ABILITY_CHECK_ID = "abilityCheckID";
    public final static String PRESET_EVENT_ABILITY_CHECK_HEIGHT = "abilityCheckHeight";
    public final static String PRESET_EVENT_INTERACTIVE = "interactive";


    //Item stats
    public final static String ITEM_FILE_NAME = "ItemList";
    public final static String itemTyp = "itemTyp";
    public final static String itemName = "itemName";
    public final static String dexReq = "dexReq";
    public final static String strReq = "strReq";
    public final static String intReq = "intReq";
    public final static String defMod = "defMod";
    public final static String atkMod = "atkMod";
    public final static String goldValue = "goldValue";
    public final static String amount = "amount";
    public final static String healpoints = "healpoints";
    public final static String scaling = "scaling"; //0 = dex , 1 = str , 2 = int

    //Characters
    public final static String CHARACTER_FILE_NAME = "CharacterPresets";
    public final static String ENEMY_FILE_NAME = "EnemyPresets";
    public final static String characters = "Characters";
    public final static String characterName = "name";
    public final static String hp = "hp";
    public final static String dexterity = "dexterity";
    public final static String strength = "strength";
    public final static String intelligence = "intelligence";
    public final static String gold = "gold";
    public final static String progressPoints = "progressPoints";
    public final static String skillPoints = "skillPoints";
    public final static String startingItemsEquipped = "startingItemsEquipped";
    public final static String startingItemsBackpack = "startingItemsBackpack";

    //Locations
    public final static String LOCATION_FILE_NAME = "Location";
    public final static String LOCATION_NAME = "Name";
    public final static String LOCATION_START_TEXT = "Start_Text";
    public final static String LOCATION_DIALOG_OPTIONS_TEXT = "Dialog_Text";
    public final static String LOCATION_DIALOG_WARMUP_TEXT = "Dialog_Text_Warmup";
    public final static String LOCATION_END_TEXT = "End_Text";
    public final static String LOCATION_TREASURE_TEXT = "Treasure_Text";
    public final static String LOCATION_ABILITY_FAILURE_TEXT = "Ability_Failure_Text";


    //MACAM
    @Override
    public String getProperty(String fileName, String property) {
        try{
            fr = new FileReader(System.getProperty("user.dir")+"\\src\\Model\\"+fileName+".properties");
            p.load(fr);
            return p.getProperty(property);
        } catch(IOException e){
            System.out.println("Fehler beim Lesen der Datei. IO Exception");
        }
        return "";
    }

    private PropertyManager() {
        SmallLogger.logToFile("Singleton created. Does not compute from anywhere else. Use Static method to gain access.");
    }

    public static PropertyManager getPropertyManager(){
        return PropertyManager.SINGLETON;
    }


}
