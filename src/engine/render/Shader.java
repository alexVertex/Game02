package engine.render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static engine._SetsStrings.*;
import static org.lwjgl.opengl.GL20.*;
public class Shader {
    private final int program;

    public Shader(String fileName){
        program = glCreateProgram();
        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs,readFile(fileName+P_SHADERS_VERTICES_EXE));
        glCompileShader(vs);
        if(glGetShaderi(vs,GL_COMPILE_STATUS) != 1){
            System.err.println(glGetShaderInfoLog(vs));
            System.exit(1);
        }
        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs,readFile(fileName+P_SHADERS_FRAGMENTS_EXE));
        glCompileShader(fs);
        if(glGetShaderi(fs,GL_COMPILE_STATUS) != 1){
            System.err.println(glGetShaderInfoLog(fs));
            System.exit(1);
        }
        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glBindAttribLocation(program,0,"vertices");
        glBindAttribLocation(program,1,"textures");
        glLinkProgram(program);
        if(glGetProgrami(program,GL_LINK_STATUS) != 1){
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if(glGetProgrami(program,GL_VALIDATE_STATUS) != 1){
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void setUniform(String name, int value){
        int location = glGetUniformLocation(program,name);
        if(location != -1){
            glUniform1i(location, value);
        }
    }

    public void setUniformF(String name, float value){
        int location = glGetUniformLocation(program,name);
        if(location != -1){
            glUniform1f(location, value);
        }
    }

    public void bind(){
        glUseProgram(program);
    }

    private String readFile(String fileName){
        StringBuilder strings = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(P_SHADERS_PATH+fileName));
            String line;
            while ((line = br.readLine()) != null){
                strings.append(line);
                strings.append("\n");
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return strings.toString();
    }
}
