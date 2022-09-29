package jrogueclone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.PrintStream;

import jrogueclone.Global;

public class ScoreReport {
    public static void displayScores() {
        File file = new File(System.getProperty("user.home") + "/.jRogueScores");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            return;
        }
        
        Vector<Pair<String, Integer>> scores = new Vector<Pair<String, Integer>>();
        while(fileReader.hasNextLine()) {
            StringTokenizer tokenizer = new StringTokenizer(fileReader.nextLine());
            scores.add(new Pair<String, Integer>(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken())));
        }

        Scanner stdinReader = new Scanner(System.in);
        String name = "";
        do {
            System.out.print("Enter three letter name: ");
            name = stdinReader.nextLine();
        } while(name.length() <= 0 || name.length() != 3 || name.contains(" "));
        name = name.toUpperCase();
        
        scores.add(new Pair<String, Integer>(name, Global.getGameLoop().getCurrentLevel().getPlayer().getKillCount()));

        scores.sort((a, b) -> {
            return b.getValue() - a.getValue();
        });
          
        PrintStream oPrintStream = null;
        try {
            oPrintStream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("| Name | Score");
        System.out.println("|------|-------");
        for(Pair<String, Integer> scorePair : scores) {
            System.out.println("| " + scorePair.getKey() + "  | " + scorePair.getValue());
            oPrintStream.println(scorePair.getKey() + " " + scorePair.getValue());
        }
        
        oPrintStream.close();
        
    }
}
