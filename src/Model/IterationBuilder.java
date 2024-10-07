package Model;

import Tools.SmallLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IterationBuilder {
    private EventType[] eventType;
    private Event[] eventList;
    private PropertyManager propertyManager;

    public IterationBuilder(){
        this.propertyManager = PropertyManager.getPropertyManager();
    }

    //JGREWE
    public List<Event> buildEventList(List<EventBuiltInfo> builtInfos){
        List<Event> returnValue = new ArrayList<>();
        for (EventBuiltInfo currentInfo : builtInfos) {
            SmallLogger.logToFile("Event for " + currentInfo.toString() + " needs to be built...");
            switch (currentInfo.eventType){
                case GOODEVENT:
                    returnValue.add(createGoodEvent());
                    break;
                case ENEMYEVENT:
                    returnValue.add(createEnemyEvent());
                    break;
                case NEUTRALEVENT:
                    returnValue.add(createNeutralEvent());
                    break;
                case PRESETEVENT:
                    returnValue.add(createPresetEvent(currentInfo));
                    break;
            }
            SmallLogger.logToFile("...Event generation done");
        }
        return returnValue;
    }

    //JGREWE
    private Event createGoodEvent(){
        //Event Basis
        int newEventNo = this.getRandomEventIdByProbability(PropertyManager.GOOD_EVENT_FILE_NAME);

        //Location Basis
        String[] possibleLocationIDs = this.propertyManager.getProperty(PropertyManager.GOOD_EVENT_FILE_NAME,
                newEventNo + "." + PropertyManager.GOOD_EVENT_LOCATION_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        int locationIDIndex = getRandomNumberInRange(0,possibleLocationIDs.length-1);


        //Treasure Prepare
        String[] possibleItemIDs = this.propertyManager.getProperty(PropertyManager.GOOD_EVENT_FILE_NAME,
                newEventNo + "." + PropertyManager.GOOD_EVENT_ITEM_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        List<Integer> newItemNos = this.getRandomNosFromPool(3, possibleItemIDs);


        //Ability Check
        Abilities ability = Abilities.STRENGTH; //Default Value
        int abilitycheck = 0;
        if(getRandomNumberInRange(1,100) >= 70){
            ability = Abilities.values()[getRandomNumberInRange(0,Abilities.values().length-1)];
            abilitycheck = getRandomNumberInRange(0,25);
        }

        //Generation
        int locationID = Integer.parseInt(possibleLocationIDs[locationIDIndex]);
        Event temp = new Event().createTreasure(
                        newItemNos, this.propertyManager.getProperty(
                                PropertyManager.LOCATION_FILE_NAME,
                                locationID +"." + PropertyManager.LOCATION_TREASURE_TEXT)
                ).
                standardTexts(locationID).
                abilityCheck(ability,abilitycheck, this.propertyManager.getProperty(
                        PropertyManager.LOCATION_FILE_NAME,
                        locationID + "." + PropertyManager.LOCATION_ABILITY_FAILURE_TEXT
                ));
        if(isRandomlyInteractive()){
            temp.dialogTexts(locationID);
        }

        return temp;
    }
    //JGREWE
    //TODO
    private Event createNeutralEvent(){
        //Event Basis
        int newEventNo = this.getRandomEventIdByProbability(PropertyManager.NEUTRAL_EVENT_FILE_NAME);

        //Treasure Prepare
        String[] possibleItemIDs = this.propertyManager.getProperty(PropertyManager.NEUTRAL_EVENT_FILE_NAME,
                        newEventNo + "." + PropertyManager.NEUTRAL_EVENT_ITEM_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        List<Integer> newItemNos = this.getRandomNosFromPool(7, possibleItemIDs);

        //Location Basis
        int locationID = getRandomNumberInRange(1,
                Integer.parseInt(this.propertyManager.getProperty(PropertyManager.LOCATION_FILE_NAME,PropertyManager.DATASET_COUNT)));

        Event temp = new Event().
                createShop(newItemNos).
                standardTexts(locationID);
        return temp;
    }
    //JGREWE
    private Event createEnemyEvent(){
        //Event Basis
        int newEventNo = this.getRandomEventIdByProbability(PropertyManager.ENEMY_EVENT_FILE_NAME);

        //Location Basis
        String[] possibleLocationIDs = this.propertyManager.getProperty(PropertyManager.ENEMY_EVENT_FILE_NAME,
                        newEventNo + "." + PropertyManager.ENEMY_EVENT_LOCATION_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        int locationIDIndex = getRandomNumberInRange(0,possibleLocationIDs.length-1);


        //Treasure Prepare
        String[] possibleItemIDs = this.propertyManager.getProperty(PropertyManager.ENEMY_EVENT_FILE_NAME,
                        newEventNo + "." + PropertyManager.ENEMY_EVENT_ITEM_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        List<Integer> newItemNos = this.getRandomNosFromPool(1, possibleItemIDs);

        //Enemies
        String[] possibleEnemyIDs = this.propertyManager.getProperty(PropertyManager.ENEMY_EVENT_FILE_NAME,
                        newEventNo + "." + PropertyManager.ENEMY_EVENT_ENEMY_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        List<Integer> newEnemyNos = this.getRandomNosFromPool(1,possibleEnemyIDs);

        //Ability Check
        Abilities ability = Abilities.STRENGTH; //Default Value
        int abilitycheck = 0;
        if(getRandomNumberInRange(1,100) >= 90){
            ability = Abilities.values()[getRandomNumberInRange(0,Abilities.values().length-1)];
            abilitycheck = getRandomNumberInRange(7,15);
        }

        //Generation
        int locationID = Integer.parseInt(possibleLocationIDs[locationIDIndex]);
        Event temp = new Event().createTreasure(
                    newItemNos,
                    this.propertyManager.getProperty(
                            PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_TREASURE_TEXT
                    )
                ).
                createEnemies(newEnemyNos).
                abilityCheck(
                        ability,
                        abilitycheck,
                        this.propertyManager.getProperty(
                                PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_ABILITY_FAILURE_TEXT
                        )
                ).
                standardTexts(locationID);
        if(isRandomlyInteractive()){
            temp.dialogTexts(locationID);
        }

        return temp;
    }
    //JGREWE
    //TODO
    private Event createPresetEvent(EventBuiltInfo info){
        //Location Basis
        int locationID = Integer.parseInt(this.propertyManager.getProperty(PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,
                info.id + "." + PropertyManager.PRESET_EVENT_LOCATION_ID));

        //Treasure Prepare
        List<Integer> newItemNos = new ArrayList<>();
        String[] itemIDs = this.propertyManager.getProperty(PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,
                        info.id + "." + PropertyManager.PRESET_EVENT_ITEM_IDS).
                split(PropertyManager.DATASET_SEPARATION_SIGN);
        for (int i = 0; i < itemIDs.length; i++) {
            newItemNos.add(Integer.parseInt(itemIDs[i]));
        }

        //Interactive
        boolean isInteractive = Boolean.parseBoolean(this.propertyManager.getProperty(
                PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,info.id + "." + PropertyManager.PRESET_EVENT_INTERACTIVE));

        //Enemy
        List<Integer> enemies = new ArrayList<>();
        enemies.add(Integer.parseInt(
                this.propertyManager.getProperty(
                        PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,
                        info.id + "." + PropertyManager.PRESET_EVENT_ENEMY_ID)));


        //Generation
        Event temp = new Event().createTreasure(
                    newItemNos,
                    this.propertyManager.getProperty(
                            PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_TREASURE_TEXT
                    )
                ).
                standardTexts(locationID).
                abilityCheck(Abilities.values()[
                        Integer.parseInt(
                                this.propertyManager.getProperty(
                                        PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,
                                        info.id + "." +  PropertyManager.PRESET_EVENT_ABILITY_CHECK_ID
                                )
                        )
                        ],
                        Integer.parseInt(
                            this.propertyManager.getProperty(
                                PropertyManager.PRESET_EVENT_DETAILS_FILE_NAME,
                                info.id + "." + PropertyManager.PRESET_EVENT_ABILITY_CHECK_HEIGHT
                            )
                        ),
                        this.propertyManager.getProperty(
                                PropertyManager.LOCATION_FILE_NAME, locationID + "." + PropertyManager.LOCATION_ABILITY_FAILURE_TEXT
                        )
                ).
                createEnemies(enemies);
        if(isInteractive){
            temp.dialogTexts(locationID);
        }
        return  temp;
    }



    //JGREWE
    private List<Integer> getRandomNosFromPool(int maxItemCount, String[] pool){
        int randomisedQty = getRandomNumberInRange(1,maxItemCount);
        List<Integer> newItemNos = new ArrayList<>();
        for (int i = 0; i < randomisedQty; i++){
            int newRandomizedInt = getRandomNumberInRange(0,pool.length-1);
            newItemNos.add(Integer.parseInt(pool[newRandomizedInt]));
        }
        return newItemNos;
    }


    private int getRandomEventIdByProbability(String fileName){
        int dsCount = Integer.parseInt(this.propertyManager.getProperty(fileName,PropertyManager.DATASET_COUNT));
        int randomizerAggregate = 0;
        for (int i = 1; i <= dsCount; i++) {
            randomizerAggregate += Integer.parseInt(
                    this.propertyManager.getProperty(fileName, i + "." + PropertyManager.DATASET_PROBABILITY));
        }
        int randomNoChosen = getRandomNumberInRange(1,randomizerAggregate);
        for (int i = 1; i <= dsCount; i++) {
            randomNoChosen -= Integer.parseInt(
                    this.propertyManager.getProperty(fileName, i + "." + PropertyManager.DATASET_PROBABILITY));
            if (randomNoChosen <= 0){
                return i;
            }
        }

        return 1;
    }

    private boolean isRandomlyInteractive(){
        //Interactive
        if(getRandomNumberInRange(1,100) >= 70) {
            return true;
        }
        return false;
    }


    //JGREWE
    //https://mkyong.com/java/java-generate-random-integers-in-a-range/
    private static int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        if (min == max){
            return max;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
