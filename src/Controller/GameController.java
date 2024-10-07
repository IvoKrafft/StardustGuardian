package Controller;

import Model.*;
import Tools.Eingabe;
import Tools.SmallLogger;
import View.ViewEP;
import View.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private List<Event> eventList;
    private List<EventBuiltInfo> builtInfoList;
    private PlayerCharacter playerCharacter;
    private ViewEP view;
    private IterationBuilder builder;
    private PropertyManager propertyManager;
    private boolean gameFinished = false;
    private int nextLevel = 1;

    //JGREWE
    public GameController(){this.init();}

    private void init(){
        this.view = new View();
        this.builder = new IterationBuilder();
        this.propertyManager = PropertyManager.getPropertyManager();
    }

    //IKRAFFT
    public void startGame() {
        for (int i = 1; i <= 7; i++){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,1 + "." + i));
        }
        this.chooseCharacter();
        this.showCharStats();
       // this.playerCharacter = PlayerCharacter.makeCharacter(1);
       while(!this.gameFinished){
           this.builtOneIteration();
           this.playOneIteration();
           view.displayWhiteSpace();
       }
    }

    //MACAM
    private PlayerCharacter chooseCharacter() {
        int eingabe = -2;
        int bestaetigt;
        while(eingabe != 1337) {
            view.displayText(PlayerCharacter.showCharacterSelection());
            int amountOfCharacters = PlayerCharacter.getDataSet();
            eingabe = Eingabe.leseInt();

            if (eingabe > 0 && eingabe <= amountOfCharacters) {
                view.displayText(PlayerCharacter.showCharStats(eingabe));
                bestaetigt = Eingabe.leseInt();
                if(bestaetigt == 1) {
                    return this.playerCharacter = PlayerCharacter.makeCharacter(eingabe);
                }
            }
        }
        //Default Char nur fuer den Fall das nichts klappt / Cheat Charakter
        return this.playerCharacter = PlayerCharacter.makeCharacter(0);
    }

    //IKRAFFT
    private void showCharStats(){
        view.displayText(playerCharacter.showCharStats());
        view.displayText(playerCharacter.showInventory());
        view.displayText(playerCharacter.showEquippedItems());
    }

    //IKRAFFT
    private void playOneIteration(){
        if (!gameFinished){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,2 + "." + 1));
            int eingabe = -2;
            for (int i = 0; i < eventList.size(); i++){
                view.displayText("(" + (i+1) + ") " + eventList.get(i).getName());
            }
            view.displayText("("+(eventList.size()+1)+") " + propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,2+"."+3));
            while(eingabe != 1337) {
                eingabe = Eingabe.leseInt()- 1;
                if (eingabe >= 0 && eingabe <= eventList.size()) {
                    if (eingabe == eventList.size()){
                        this.showCharStats();
                        this.playOneIteration();
                        return;
                    }
                    this.playEvent(eingabe);
                    return;
                }
                view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME, 2 + "." + 2));
            }
        }
    }

    //IKRAFFT
    private void playEvent(int eventNum){
        Event event = this.eventList.get(eventNum);
        this.view.displayText(event.getStartText());
        if (event.isInteractiv() && event.getShop() != null){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,3 + "." + 1));
            shoping(event.getShop());
        }
        if (event.isInteractiv() && event.getTextDialogWarmup() != null){
            List<String> diaglogOptions = event.getDiaglogOptions();
            this.view.displayText(event.getTextDialogWarmup());
            for (String s : diaglogOptions){
                this.view.displayText(s);
            }
            int eingabe = Eingabe.leseInt() - 1;
            if (eingabe < 0 || eingabe > diaglogOptions.size()){
                playEvent(eventNum);
                return;
            }
            if (eingabe == 0){
                if (playerCharacter.getAbilities(event.getAbilities().name()) < event.getAbilitycheck()){
                    this.view.displayText(event.getAbilityFailureText());
                    playerCharacter.damage(10);
                } else {
                    this.view.displayText(event.getTreasureText());
                    List<Item> items = event.getItems();
                    for (Item item : items) {
                        this.view.displayText("Gefunden : " + item.getName());
                        this.playerCharacter.addItem(item);
                    }
                }
            }
            this.view.displayWhiteSpace();
        }
        if (!event.getEnemyCharacters().isEmpty()){
            fight(event);
        }
        statCheck(event);
        this.view.displayText(event.getEndText());
    }

    //IKRAFFT
    private void builtOneIteration(){
        this.builtInfoList = new ArrayList<>();

        if (playerCharacter.getProgressPoints() >= 6){
            this.finalFight();
        }else {
            Random random = new Random();
            int num;
            num = random.nextInt(6 - 2) + 2;

            for (int i = 0; i < num; i++) {
                buildRandomEventData();
            }

            this.eventList = builder.buildEventList(this.builtInfoList);
        }
    }

    //IKRAFFT
    private void gameOver(){
        view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,5 +"."+2));
        gameFinished = true;
    }

    //IKRAFFT
    private void gameWon(){
        if (!gameFinished){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,5 +"."+1));
            gameFinished = true;
        }
    }

    //IKRAFFT
    private void statCheck(Event event){
        if (playerCharacter.getHealthPoints() < 1){
            gameOver();
            return;
        }

        playerCharacter.addSkillPoints(event.getSkillPoints());
        if(playerCharacter.getSkillPoints() >= this.nextLevel){
            levelUp();
        }

        playerCharacter.addProgressPoints(event.getProgressPoints());

        view.displayText("PPoints: "+playerCharacter.getProgressPoints());
    }

    private void levelUp(){
        for (int i = 1; i <5;i++){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,7 +"."+i));
        }
        int eingabe = Eingabe.leseInt() - 1;
        if (eingabe < 0 || eingabe > 2){
            levelUp();
            return;
        }
        switch (eingabe) {
            case 0:
                playerCharacter.levelDexterity();
                break;

            case 1:
                playerCharacter.levelStrength();
                break;

            case 2:
                playerCharacter.levelIntelligence();
                break;

            default:
                levelUp();
                return;
        }
        this.nextLevel += 1;
    }

    private void finalFight(){
        this.builtInfoList.add(new EventBuiltInfo(EventType.PRESETEVENT,0));
        this.eventList = builder.buildEventList(this.builtInfoList);
        playEvent(0);
        gameWon();
    }

    private void fight(Event event){
        boolean fighting;
        List<EnemyCharacter> enemyCharacters = event.getEnemyCharacters();

        for (EnemyCharacter i : enemyCharacters){
            this.view.displayText(propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,6+"."+1) +" "+ i.getName());
            fighting = true;
            this.view.displayText(i.getName() + " HP: " + i.getHealthPoints());
            this.view.displayText("Deinen HP : " +this.playerCharacter.getHealthPoints());
            while (fighting){
                i.damage((int)this.playerCharacter.atkDamage());
                this.playerCharacter.damage((int)i.atkDamage());
                this.view.displayText(i.getName() + "HP: " + i.getHealthPoints());
                this.view.displayText("Deinen HP : " +this.playerCharacter.getHealthPoints());
                if (i.getHealthPoints() <= 0){
                    playerCharacter.addGold(10);
                    fighting = false;
                }
                if (this.playerCharacter.getHealthPoints() <= 0){
                    return;
                }
            }

        }
    }

    //IKRAFFT
    private void shoping(Shop shop){
        List<Item> items = shop.getWaresToSell().getItemList();
        int eingabe;
        view.displayText("Dein Gold : "+ playerCharacter.getGold());
        view.displayWhiteSpace();
        for (int i = 0; i < items.size(); i++){
            view.displayText("("+(i+1)+") "+items.get(i).getName() + " : " + items.get(i).getGoldValue());
        }
        view.displayText("("+(items.size()+1)+") "+this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,4 + "." + 2));
        eingabe = Eingabe.leseInt() - 1;
        if (eingabe < 0 || eingabe > items.size()){
            shoping(shop);
            return;
        } else if(eingabe == items.size()){
            return;
        }
        if (items.get(eingabe).getGoldValue() > playerCharacter.getGold()){
            view.displayText(this.propertyManager.getProperty(PropertyManager.GAME_PROMPTS_FILE_NAME,4 + "." + 1));
            shoping(shop);
            return;
        } else {
            playerCharacter.addItem(items.get(eingabe));
            playerCharacter.addGold((-(items.get(eingabe).getGoldValue())));
        }
        shoping(shop);
    }

    //IKRAFFT
    private void buildRandomEventData(){
        Random random = new Random();
        int num;
        int id;
        num = random.nextInt(101);

        if (num <33){
            id = random.nextInt(Integer.parseInt(this.propertyManager.getProperty(PropertyManager.GOOD_EVENT_FILE_NAME, PropertyManager.DATASET_COUNT))) +1;
            this.builtInfoList.add(new EventBuiltInfo(EventType.GOODEVENT,id));
        }
        if (num>=33 && num <66){
            id = random.nextInt(Integer.parseInt(this.propertyManager.getProperty(PropertyManager.ENEMY_EVENT_FILE_NAME, PropertyManager.DATASET_COUNT))) +1;
            this.builtInfoList.add(new EventBuiltInfo(EventType.ENEMYEVENT,id));
        }
        if (num>=66 && num <100){
            id = random.nextInt(Integer.parseInt(this.propertyManager.getProperty(PropertyManager.NEUTRAL_EVENT_FILE_NAME, PropertyManager.DATASET_COUNT))) +1;
            this.builtInfoList.add(new EventBuiltInfo(EventType.NEUTRALEVENT,id));
        }
    }

    //IKRAFFT
    private void buildPresetEventData(int id){
        this.builtInfoList.add(new EventBuiltInfo(EventType.PRESETEVENT,id));
    }
}
