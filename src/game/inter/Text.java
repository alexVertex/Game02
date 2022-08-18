package game.inter;

import engine.render.Label;
import engine.window.Actor;
import engine.helpers.FontHelper;
import engine.helpers.TextHelper;

public class Text extends Actor {
    Label label;
    public Text(String font, String text, int X, int Y, int Z, int style) {
        label = new Label(FontHelper.getFont(font), text, X, Y, Z + 1, style);
    }
    public Text(String font, String text, int X, int Y, int Z, int width, int style) {
        label = new Label(FontHelper.getFont(font), text, X, Y, Z + 1,style);
        label.setWitch(width);
    }
    @Override
    public void init() {
        TextHelper.addText(label);
    }

    public void dispose() {
        TextHelper.disposeText(label);
    }

    @Override
    public void hide() {
        label.hide();
    }

    @Override
    public void show() {
        label.show();
    }

    @Override
    public void use() {

    }

    public void changeText(String text) {
        label.changeText(text);
    }

    public void setText(String text) {
        label.changeText(text);
    }

    public String getText() {
        return label.getText();
    }

    public void setAlpha(float a) {
        label.setAlpha(a);
    }

    @Override
    public void doStuff(String command, String params) {

    }

    public void setColor(float r, float g, float b) {
        label.setColor(r,g,b);
    }

    public void setDefColor() {
        TextHelper.setDefColor(label);
    }
}
