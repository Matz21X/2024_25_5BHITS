import { initShaderProgram } from "./initShader.ts";
import { initBuffers } from "./initBuffer.ts";
import { drawScene } from "./drawScene.ts";
let start = 0;
let rotation = 0;
// Vertex shader program
// Vertex shader program
const vsSource = `
    attribute vec4 aVertexPosition;
    attribute vec4 aVertexColor;

    uniform mat4 uModelViewMatrix;
    uniform mat4 uProjectionMatrix;

    varying lowp vec4 vColor;

    void main(void) {
      gl_Position = uProjectionMatrix * uModelViewMatrix * aVertexPosition;
      vColor = aVertexColor;
    }
  `;
// Fragment shader program
const fsSource = `
    varying lowp vec4 vColor;

    void main(void) {
      gl_FragColor = vColor;
    }
  `;
//
// start here
//
function main() {
    const canvas = document.querySelector("#glcanvas") as HTMLCanvasElement;
    // Initialize the GL context
    const gl = canvas.getContext("webgl") as WebGLRenderingContext;

    // Only continue if WebGL is available and working
    if (gl === null) {
        alert("Unable to initialize WebGL. Your browser or machine may not support it.");
        return;
    }
    const shaderProgram = initShaderProgram(gl, vsSource, fsSource);
    // Collect all the info needed to use the shader program.
    // Look up which attributes our shader program is using
    // for aVertexPosition, aVertexColor and also
    // look up uniform locations.
    const programInfo = {
        program: shaderProgram,
        attribLocations: {
            vertexPosition: gl.getAttribLocation(shaderProgram, "aVertexPosition"),
            vertexColor: gl.getAttribLocation(shaderProgram, "aVertexColor"),
        },
        uniformLocations: {
            projectionMatrix: gl.getUniformLocation(shaderProgram, "uProjectionMatrix"),
            modelViewMatrix: gl.getUniformLocation(shaderProgram, "uModelViewMatrix"),
        },
    };
    const buffer = initBuffers(gl);
    requestAnimationFrame(render);
    function render(now) {
        now = now * 0.001;
        const deltaTime = now - start;
        start = now;
        rotation += deltaTime;
        drawScene(gl, programInfo, buffer, rotation);
        requestAnimationFrame(render);
    }
}
main();
