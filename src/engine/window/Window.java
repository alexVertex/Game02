package engine.window;

import engine.render.*;
import engine.input.MainInput;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Objects;

import static engine._SetsStrings.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    public static int timesRedraw = 0;
    public static  int frames = 0;

    private static Cursor cursor;

    public static MainInput mainInput;
    public static Screen currentScreen;
    private static long window;
    private static final WindowSettings sets = new WindowSettings();
    public static void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() )
            throw new IllegalStateException(E_UNABLE_TO_MAKE_GLFW);
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, sets.vidMode == I_WINDOW_STYLE_WINDOWED ? GLFW_TRUE : GLFW_FALSE);
        if(sets.vidMode == I_WINDOW_STYLE_FULLSCREEN) {
            window = glfwCreateWindow(sets.width, sets.height, sets.name, glfwGetPrimaryMonitor(), NULL);
        } else {
            window = glfwCreateWindow(sets.width, sets.height, sets.name, NULL, NULL);
        }
        if ( window == NULL )
            throw new RuntimeException(E_UNABLE_TO_MAKE_WINDOW);
        if(sets.vidMode != I_WINDOW_STYLE_FULLSCREEN) {
            try (MemoryStack stack = stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);
                glfwGetWindowSize(window, pWidth, pHeight);
                GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                assert vidmode != null;
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );

            }
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Sprite.setGlobalBrightness(sets.brightness);
        mainInput = new MainInput(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        if (glfwRawMouseMotionSupported())
            glfwSetInputMode(window, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        cursor = new Cursor(S_CURSOR_DEF_TEXTURE,I_CURSOR_DEF_SIZE,I_CURSOR_DEF_SIZE,I_CURSOR_DEF_HOT_SPOT_OFFSET,I_CURSOR_DEF_HOT_SPOT_OFFSET);

    }

    public static void loop(){
        double frameTime = 0;
        double time = System.currentTimeMillis()/1000.0;
        double unprocessed = 0;
        while ( !glfwWindowShouldClose(window) ) {
            boolean canRender = false;
            double time2 = System.currentTimeMillis()/1000.0;
            double passed = time2-time;
            unprocessed += passed;
            frameTime += passed;
            time = time2;
            while (unprocessed >= sets.frameCap){
                mainInput.update();
                unprocessed -= sets.frameCap;
                canRender = true;
                if(frameTime > 1.0){
                    frameTime = 0;
                    timesRedraw = 0;
                    frames = 0;
                }
                currentScreen.input(mainInput);
                currentScreen.logic();
                glfwPollEvents();
            }
            if (canRender) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                currentScreen.render();
                frames++;
                cursor.render();
                glfwSwapBuffers(window);
            }

        }
    }

    public static void close(){
        glfwSetWindowShouldClose(window,true);
    }
    public static void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static float getWidth() {
        return sets.width;
    }

    public static float getHeight() {
        return sets.height;
    }

    public static WindowSettings getSets() {
        return sets;
    }
}
