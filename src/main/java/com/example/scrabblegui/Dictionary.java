package com.example.scrabblegui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Dictionary implements Serializable {
    private Set<String> wordSet;

    public Dictionary() {
        wordSet = new HashSet<>();
        try {
            addFileToDictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds words from a file to the dictionary.
     *
     * @throws FileNotFoundException If the file is not found.
     */
    public void addFileToDictionary() throws FileNotFoundException {
        File file = new File("words.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String word = sc.nextLine().toLowerCase();
                wordSet.add(word);
            }
        }
    }

    /**
     * Checks if a word is valid according to the dictionary.
     *
     * @param word The word to check.
     * @return True if the word is valid, false otherwise.
     */
    public boolean isValidWord(String word) {
        return wordSet.contains(word.toLowerCase());
    }
}
