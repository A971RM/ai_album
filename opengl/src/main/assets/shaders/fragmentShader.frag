precision highp float;
varying vec2 v_texCoord;

uniform sampler2D s_texture0;
uniform sampler2D s_texture1;
void main()
{
  vec4 baseColor;
  vec4 lightColor;

  baseColor = texture2D( s_texture0, v_texCoord );
  lightColor = texture2D( s_texture1, v_texCoord );
  gl_FragColor = baseColor * (lightColor + 0.25);
}
