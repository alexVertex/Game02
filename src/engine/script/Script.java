package engine.script;

import engine.window.Actor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static engine._SetsStrings.*;

public class Script {

    private static int curLine = 0;
    private final static List<String> lines = new ArrayList<>();
    List<Blob> blobs = new ArrayList<>();
    public Script(String path) {
        lines.clear();
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeScript();
    }
    private void makeScript() {
        curLine = 0;
        for(String el : lines){
            String[] words = el.split(" ");
            makeNormalBlob(words);
            curLine++;
        }
    }



    private void makeNormalBlob(String[] words){
        Blob blob = new Blob();
        blob.commandName = words[0];
        blob.makeBlob(words);
        blobs.add(blob);
        if(words[0].equals(S_SCRIPT_KEYWORD_IF)) {
            makeIfBlob(blob);
        }
        if(words[0].equals(S_SCRIPT_KEYWORD_ELSE)) {
            makeElseBlob(blob);
        }
        blob.mineScript = this;
    }

    private void makeIfBlob(Blob blob){
        int levelOfIf = 0;
        for(int i = curLine; i < lines.size();i++){
            String firstWord = lines.get(i).split(" ")[0];
            if(firstWord.equals(S_SCRIPT_KEYWORD_ENDIF)){
                levelOfIf--;
            } else if(firstWord.equals(S_SCRIPT_KEYWORD_IF)){
                levelOfIf++;
            } else if(firstWord.equals(S_SCRIPT_KEYWORD_ELSE) && levelOfIf == 1){
                levelOfIf = 0;
            }
            if(levelOfIf == 0){
                if(firstWord.equals(S_SCRIPT_KEYWORD_ELSE)){
                    i++;
                }
                blob.teleport = i;
                break;
            }
        }
    }
    private void makeElseBlob(Blob blob){
        int levelOfIf = 1;
        for(int i = curLine; i < lines.size();i++){
            String firstWord = lines.get(i).split(" ")[0];
            if(firstWord.equals(S_SCRIPT_KEYWORD_ENDIF)){
                levelOfIf --;
            } else if(firstWord.equals(S_SCRIPT_KEYWORD_IF)){
                levelOfIf ++;
            }
            if(levelOfIf == 0){
                blob.teleport = i;
                break;
            }
        }
    }

    public void execute(Actor object){
        int currentBlob = 0;
        while (currentBlob < blobs.size()){
            Blob curBlob = blobs.get(currentBlob);
            currentBlob = curBlob.execute(currentBlob,object);
        }
    }
}
