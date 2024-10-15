package org.example.pz2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reader{

    public static void calculate(String filePath){
        ArrayList<String> words = readFile(filePath);
        for (String word : words) {
            if (!Main.wordCount.containsKey(word)) {
                Main.wordCount.put(word, 1);
                continue;
            }
            Main.wordCount.put(word, Main.wordCount.get(word) + 1);
        }
    }

    private static ArrayList<String> readFile(String filePath){
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> words = new ArrayList<>();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] wordsInLine = line.split(" ");
            words.addAll(Arrays.asList(wordsInLine));
        }
        return words;
    }

}