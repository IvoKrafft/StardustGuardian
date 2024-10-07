package View;

import Model.Event;
import Model.PlayerCharacter;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class View implements ViewEP {
    private static final long TEXT_DELAY = 5;//change back to 30

    private PlayerCharacter playerCharacter;
    private Event[] eventList;
    Writer output;

    public View(){
        this.output = new PrintWriter(System.out);
    }

    //JGREWE
    public void displayText(String s){
        try {
            for (char c : s.toCharArray()) {
                output.write(c);
                output.flush();
                Thread.sleep(View.TEXT_DELAY);
            }
        } catch (InterruptedException | IOException e){
            System.out.println("Interrupt in View bei Anzeige eines Strings: " + s);
        }
        System.out.println();
    }

    //IKRAFFT
    public void displayWhiteSpace(){
        displayText("");
    }
}
