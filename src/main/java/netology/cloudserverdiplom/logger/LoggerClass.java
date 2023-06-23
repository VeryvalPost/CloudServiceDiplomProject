package netology.cloudserverdiplom.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class LoggerClass {
    private static String loggerPath = "src/main/resources/log_server.log";
    private static AtomicInteger stringCount = new AtomicInteger(0);

    public LoggerClass() {
    }

    public synchronized static void writeLog(String logMsg) {
        try {
            FileWriter fileWriter = new FileWriter(loggerPath, true);
            Date currDate = new Date();
            stringCount.incrementAndGet();
            String index = String.valueOf(stringCount);
            fileWriter.write(index + ". " + currDate + ":  " + logMsg + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Write error =  log");
        }
    }
}


