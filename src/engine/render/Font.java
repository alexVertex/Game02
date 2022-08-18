package engine.render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static engine._SetsStrings.*;

public class Font {
    public static final String FONT_PATH = P_FONT_PATH;
    public static final String FONT_EXE = P_DATA_FILE_EXE;
    float height = 0;

    public String name;
    public Font(String name){
        this.name = name;
        float width = 0;
        BufferedReader br;
        Glyph glyph;
        try {
            br = new BufferedReader(new FileReader(FONT_PATH+name+FONT_EXE));
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null){
                String[] split = line.split(";");
                if(first){
                    first = false;
                    width = Float.parseFloat(split[0]);
                    height = Float.parseFloat(split[1]);
                } else {
                    glyph = new Glyph();
                    glyph.start = Float.parseFloat(split[1])/width;
                    glyph.end = Float.parseFloat(split[2])/width;
                    glyph.size = Float.parseFloat(split[2])-Float.parseFloat(split[1]);
                    glyph.height = height;
                    glyphs.put(split[0].charAt(0), glyph);
                }
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    HashMap<Character, Glyph> glyphs = new HashMap<>();
    public static class Glyph{
        float start, end;
        float size;
        float height;
    }
}
