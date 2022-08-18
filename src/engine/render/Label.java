package engine.render;

import engine.helpers.SpriteHelper;

import java.util.ArrayList;
import java.util.List;

import static engine._SetsStrings.*;

public class Label {
    public static final int LEFT = I_LABEL_TYPE_LEFT;
    public static final int RIGHT = I_LABEL_TYPE_RIGHT;
    public static final int CENTER = I_LABEL_TYPE_CENTER;
    public static final int WITH_RETURNS = I_LABEL_TYPE_WITH_RETURNS;

    private final static String[] lines = new String[100];

    public float red;
    public float green;
    public float blue;
    float width = 0;
    float x, y;
    String text;
    Font font;
    int style, zOrder;
    private int linesCount;

    public Label(Font font, String text, float X, float Y, int lZ, int typeStyle) {
        x = X;
        y = Y;
        this.text = text;
        this.font = font;
        createSprites(typeStyle,lZ);
    }

    public Label(Font font, String text, int X, int Y, int zOrder, int typeStyle, float red, float green, float blue) {
        x = X;
        y = Y;
        this.text = text;
        this.font = font;
        this.red = red;
        this.green = green;
        this.blue = blue;
        createSprites(typeStyle,zOrder);
    }

    public void createSprites(int style, int zOrder) {
        this.style = style;
        this.zOrder = zOrder;
        float inSymbolsDist = 1;
        if(style == WITH_RETURNS){
            drawWithReturns(inSymbolsDist);
            return;
        }
        linesCount = 1;
        int cursorX = 0;
        if (style == RIGHT || style == CENTER) {
            float totalSize = getTextWide();
            if (style == RIGHT) {
                Font.Glyph data1 = font.glyphs.get(text.charAt(text.length()-1));
                int shift = (int) (totalSize+(data1.size)/2);
                cursorX -= shift;
            }
            if (style == CENTER) {
                cursorX -= totalSize / 2;
            }
        }
        if(width > 0){
            float totalSize = getTextWide();
            float freeSpace = width - totalSize;
            inSymbolsDist = freeSpace / (text.length()-2);
        }
        drawLine(text,cursorX,0,inSymbolsDist);
    }
    private void drawWithReturns(float inSymbolsDist) {
        linesCount = 0;
        int cursorX,cursorY = 0;
        String[] words = text.split(" ");
        int lastWord = 0;
        if(words.length == 1){
            linesCount++;
            lines[0] = words[0];
        }
        while (lastWord +1 < words.length) {
            text = words[lastWord];
            String textOld = text;
            for (int i = lastWord + 1; i < words.length; i++) {
                text += " " + words[i];
                double len = getTextWide();
                if (len > width || i == words.length-1) {
                    if(i == words.length-1){
                        if(len <= width){
                            lines[linesCount] = text;
                        } else {
                            lines[linesCount] = textOld;
                            linesCount++;
                            lines[linesCount] = words[words.length-1];
                        }
                    } else {
                        lines[linesCount] = textOld;
                    }
                    linesCount++;
                    lastWord = i;
                    break;
                }
                textOld = text;
            }
        }
        for(int j = 0; j < linesCount;j++) {
            cursorY += -j*I_LABEL_MULTILINE_HEIGHT;
            cursorX = 0;
            drawLine(lines[j],cursorX,cursorY,inSymbolsDist);
        }
    }

    private void drawLine(String line, int cursorX,int cursorY,float inSymbolsDist){
        for(int i = 0; i < line.length();i++){
            Font.Glyph data = font.glyphs.get(line.charAt(i));
            cursorX += (int)data.size/2;
            Sprite sprite = new Sprite(font.name,cursorX+x,cursorY+y, zOrder,data.size,data.height,data.start,0,data.end,1,true,I_CAMERA_AFFECT_NO);
            sprite.red = red;
            sprite.green = green;
            sprite.blue = blue;
            cursorX += (int)data.size/2+inSymbolsDist;
            sprites.add(sprite);
        }
    }

    public List<Sprite> sprites = new ArrayList<>();

    public void hide() {
        setAlpha(0);
    }

    public void show() {
        setAlpha(1);
    }

    public void setPos(double x, double y) {
        double difX = this.x - x;
        double difY = this.y - y;
        this.x = (float) x;
        this.y = (float) y;
        for(Sprite el : sprites){
            el.setPos(el.locX-difX,el.locY-difY);
        }
    }

    public void changeText(String s, int style,int width) {
        this.style = style;
        this.width = width;
        changeText(s);
    }

    public void changeText(String text) {
        if(this.text.equals(text)) return;
        this.text = text;
        for (Sprite el : sprites){
            SpriteHelper.removeSprite(el);
        }
        sprites.clear();
        createSprites(style,zOrder);
        for (Sprite el : sprites){
            SpriteHelper.addSprite(el);
        }
    }

    public void setWitch(int width) {
        this.width = width;
    }

    public String getText() {
        return text;
    }

    public void setAlpha(float a) {
        for(Sprite el : sprites){
            el.alpha = a;
        }
    }

    public void setColor(float r, float g, float b) {
        for(Sprite el : sprites){
            el.red = r;
            el.green = g;
            el.blue = b;
        }
    }

    public int getLines() {
        return linesCount;
    }

    public float getTextWide() {
        return getTextWide(text);
    }

    public float getTextWide(String inText) {
        float totalSize = 0;
        float inSymbolsDist = 1;
        for(int i = 0; i < inText.length();i++){
            Font.Glyph data = font.glyphs.get(inText.charAt(i));
            int size = (int) data.size;
            if(size % 2 == 1) size--;
            totalSize += size + inSymbolsDist;
        }
        return totalSize;
    }
}
