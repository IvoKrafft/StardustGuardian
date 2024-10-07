package Tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmallLogger {


    private static final String LOGFILE_NAME = "logfile.txt";

    //JGREWE
    public static void resetLogger(){
        ensureLogger(true);
    }

    public static void startNewLoggingSession(){
        ensureLogger(false);
        SmallLogger.processLogging("\n",false);
        SmallLogger.logToFile("New Logging initialized.\n-------------------------------------------");
    }

    //JGREWE nach Vorbild von https://www.w3schools.com/java/java_files_create.asp
    public static void logToFile(String s){
        processLogging(s,true);
    }

    private static void processLogging(String s, boolean withTimestamp){
        try {
            FileWriter loggerWriter = new FileWriter(SmallLogger.LOGFILE_NAME,true);
            if (withTimestamp){
                loggerWriter.write(generateTimestamp() +  s + "\n");
            }else {
                loggerWriter.write(s + "\n");
            }
            loggerWriter.close();
        } catch (IOException e) {
            System.out.println("Error while Logging!");
            e.printStackTrace();
        }
    }

    //JGREWE
    private static String generateTimestamp(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd   HH:mm:ss");
        return (dtf.format(now) + ":   ");
    }

    //JGREWE
    private static void ensureLogger(boolean overwriteExisting){
        try {
            File loggerFile = new File(SmallLogger.LOGFILE_NAME);
            if (loggerFile.exists() && !overwriteExisting){
                return;
            }
            loggerFile.delete();
            if (loggerFile.createNewFile()) {
                SmallLogger.logToFile("Logger created");
            }
        } catch (IOException e){
            System.out.println("Error while create Logging!");
            e.printStackTrace();
        }
    }
}
