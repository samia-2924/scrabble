package com.example.scrabblegui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private Set<String> words;

    public Dictionary() {
        words = new HashSet<>();
        loadWords();
    }

    private void loadWords() {
        String filePath = "words.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word);
    }
}