#version 330 core

out vec4 FragColor;

in vec4 v_color;

void main() {
    /*if (v_color.w != 1.0) {
        discard;
    }*/
    FragColor = v_color;
}