package engine.render;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import  static org.lwjgl.opengl.GL11.*;
import  static org.lwjgl.opengl.GL15.*;
import  static org.lwjgl.opengl.GL20.*;

public class Model {

    private final int drawCount;
    private final int vID;
    private int tID;
    private final int iID;

    public Model(float[] vertices, float[] texCoords, int[] indices){
        drawCount = indices.length;
        vID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vID);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(vertices),GL_STATIC_DRAW);
        tID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,tID);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(texCoords),GL_STATIC_DRAW);
        iID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,iID);
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public void replaceTID(float leftTex, float topTex, float rightTex, float botTex){
        float[] tex = new float[]{
                leftTex,topTex,
                rightTex,topTex,
                rightTex,botTex,
                leftTex,botTex,
        };
        glDeleteBuffers(tID);
        tID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,tID);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(tex),GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public void dispose(){
        glDeleteBuffers(tID);
        glDeleteBuffers(vID);
        glDeleteBuffers(iID);
    }

    public void render(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER,vID);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,tID);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,iID);
        glDrawElements(GL_TRIANGLES,drawCount,GL_UNSIGNED_INT,0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
