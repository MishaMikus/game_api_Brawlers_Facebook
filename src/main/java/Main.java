import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        while(true){
        new RecourceCollector().collect();
        Thread.sleep(300*1000);//5 min sleep
        }
    }
}
