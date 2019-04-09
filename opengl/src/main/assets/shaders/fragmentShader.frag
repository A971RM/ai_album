precision highp float;
varying vec2 v_texCoord;
void main()
{
  vec4 baseColor;
  vec4 lightColor;

  baseColor = vec4(1.0);
  lightColor = vec4(0.0);
  gl_FragColor = baseColor * (lightColor + 0.25);
}
