#version 330 core

uniform sampler2D sampler;

out vec4 FragColor;

in vec2 tex_coords;
in vec3 v_color;
in vec4 b_color;

void main() {
    if (texture(sampler, tex_coords).a != 1.0f) {
        if (b_color.w != 1.0f) {
            discard;
        } else {
            FragColor = b_color;
        }
    } else {
        FragColor = vec4(v_color, 1);
    }
}