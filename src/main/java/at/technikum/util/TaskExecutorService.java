package at.technikum.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutorService {
    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(4, r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            });

    public static void execute(Runnable task) {
        executorService.submit(task);
    }
}
