package org.example.pz3;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FileSearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the directory path:");
        String dirPath = scanner.nextLine();

        System.out.println("Enter the file extension to search (e.g., .pdf):");
        String extension = scanner.nextLine();


        File rootDir = new File(dirPath);
        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        int count = pool.invoke(new FileSearchTask(rootDir, extension));
        long endTime = System.currentTimeMillis();

        System.out.println("Files found: " + count);
        System.out.println("Time Work Stealing: " + (endTime - startTime) + "ms");
    }
}