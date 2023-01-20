#version 330 core

layout (location = 0) in vec2 vertices;
layout (location = 1) in vec4 in_color;

uniform vec2 windowSize;

out vec4 v_color;

void main() {
    v_color = in_color;
    gl_Position = vec4(2.0/windowSize.x * vertices.x - 1.0, (2.0/windowSize.y * vertices.y - 1.0) * -1.0, 1, 1);
}