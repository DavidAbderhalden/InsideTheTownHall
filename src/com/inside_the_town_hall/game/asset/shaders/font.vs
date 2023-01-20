#version 330 core

layout (location = 0) in vec2 vertices;
layout (location = 1) in vec2 textures;
layout (location = 2) in vec3 in_color;
layout (location = 3) in vec4 bg_color;

uniform vec2 windowSize;

out vec2 tex_coords;

out vec3 v_color;
out vec4 b_color;

void main() {
    tex_coords = textures;
    v_color = in_color;
    b_color = bg_color;
    gl_Position = vec4(2.0/windowSize.x * vertices.x - 1.0, (2.0/windowSize.y * vertices.y - 1.0) * -1.0, 1, 1);
}