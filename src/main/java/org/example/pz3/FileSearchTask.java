package org.example.pz3;

import java.io.File;
import java.util.concurrent.RecursiveTask;

public class FileSearchTask extends RecursiveTask<Integer> {
    private final File directory;
    private final String extension;

    public FileSearchTask(File directory, String extension) {
        this.directory = directory;
        this.extension = extension;
    }

    @Override
    protected Integer compute() {
        int count = 0;
        File[] files = directory.listFiles();
        if (files == null) return 0;

        for (File file : files) {
            if (file.isDirectory()) {
                FileSearchTask subTask = new FileSearchTask(file, extension);
                subTask.fork();
                count += subTask.join();
            } else if (file.getName().endsWith(extension)) {
                count++;
            }
        }
        return count;
    }
}