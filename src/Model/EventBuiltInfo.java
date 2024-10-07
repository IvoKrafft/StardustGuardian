package Model;

public class EventBuiltInfo {
    public EventType eventType;
    public int id;

    public EventBuiltInfo(){

    }
    //JGREWE
    public EventBuiltInfo(EventType eventType, int id){
        this.eventType = eventType;
        this.id = id;
    }

    @Override
    public String toString() {
        return "EventBuiltInfo{" +
                "eventType=" + eventType +
                ", id=" + id +
                '}';
    }
}

