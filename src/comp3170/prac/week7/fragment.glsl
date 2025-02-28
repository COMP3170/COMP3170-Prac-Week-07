#version 410

uniform vec2 u_viewportSize;	// viewport size in pixels (width, height) 

layout(location = 0) out vec4 o_colour;	// output to colour buffer (r,g,b,a)

void main() {	
	o_colour = vec4(0,0,0,1);
}