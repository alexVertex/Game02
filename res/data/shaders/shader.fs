#version 330

uniform sampler2D sampler;
uniform float red;
uniform float green;
uniform float blue;
uniform float alpha;

varying vec2 tex_coords;

void main() {
	gl_FragColor = vec4(red,green,blue,alpha) * texture2D(sampler,tex_coords);
}