#version 130

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 tex_coords;
uniform float shiftX;
uniform float shiftY;
uniform float otnX;
uniform float otnY;
uniform float angle;
void main() {
	float w = angle;
	mat3 A = mat3( cos(w), sin(w),0,
		  -sin(w), cos(w),0,
	          0,0,0) ;

	mat3 S = mat3( otnX, 0,0,
		  0, otnY,0,
	          0,0,0) ;
	tex_coords = textures;
	gl_Position = vec4(shiftX,shiftY,0,0) + vec4(vertices*A*S,1);
}