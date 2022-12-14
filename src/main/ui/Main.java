package ui;

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        new BlobEatBlob();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next);
                }
            }
        }));
    }
}
