#version 330 core

layout (location = 0) in vec2 vertices;
layout (location = 1) in vec2 textures;

uniform vec2 windowSize;

out vec2 tex_coords;

void main() {
    tex_coords = textures;
    gl_Position = vec4(2.0/windowSize.x * vertices.x - 1.0, (2.0/windowSize.y * vertices.y - 1.0) * -1.0, 1, 1);
}