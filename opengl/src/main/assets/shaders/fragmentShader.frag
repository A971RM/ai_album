precision highp float;
varying vec2 v_texCoord;

uniform sampler2D s_texture0;
uniform sampler2D s_texture1;
uniform sampler2D s_texturetext;
void main()
{
  vec4 textColor;
  vec4 baseColor;
  vec4 lightColor;

  baseColor = texture2D( s_texture0, v_texCoord );
  lightColor = texture2D( s_texture1, v_texCoord );
  textColor = texture2D( s_texturetext, v_texCoord );
  gl_FragColor = mix(baseColor * (lightColor + 0.25), textColor, textColor.a);
}
