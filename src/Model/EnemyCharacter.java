package Model;

public class EnemyCharacter extends Character {

    public EnemyCharacter(){
        super();
    }
    //JGREWE MACAM
    static EnemyCharacter makeEnemy(int id){
        //TODO
        PropertyManager propertyManager = PropertyManager.getPropertyManager();
        EnemyCharacter newEnemy = new EnemyCharacter();
        newEnemy.healthPoints = Integer.parseInt(propertyManager.getProperty(PropertyManager.ENEMY_FILE_NAME,id + "." + PropertyManager.hp));
        newEnemy.gold = Integer.parseInt(propertyManager.getProperty(PropertyManager.ENEMY_FILE_NAME,id + "." + PropertyManager.gold));
        newEnemy.name = propertyManager.getProperty(PropertyManager.ENEMY_FILE_NAME,id + "." + PropertyManager.characterName);
        newEnemy.equippedInventory = Inventory.addStartingItems(id);
        return newEnemy;
    }
}
