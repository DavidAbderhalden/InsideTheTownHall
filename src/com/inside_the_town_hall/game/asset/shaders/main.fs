#version 330 core

uniform sampler2D sampler;

out vec4 FragColor;

in vec2 tex_coords;

void main() {
    /*if (texture(sampler, tex_coords).a != 1.0f) {
        discard;
    }*/
    FragColor = texture(sampler, tex_coords);
}