import { initShaderProgram } from "./initShader";
import { initBuffers} from "./initBuffer";
import { drawScene } from "./drawScene";

let start = 0;
let rotation = 0;

const vsSource = `...`;
const fsSource = `...`;

function main() {
    const canvas = document.querySelector("#glcanvas") as HTMLCanvasElement;
    const gl = canvas.getContext("webgl") as WebGLRenderingContext;

    if (gl === null) {
        alert("Unable to initialize WebGL. Your browser or machine may not support it.");
        return;
    }

    const shaderProgram = initShaderProgram(gl, vsSource, fsSource);

    const programInfo = {
        program: shaderProgram!,
        attribLocations: {
            vertexPosition: gl.getAttribLocation(shaderProgram!, "aVertexPosition"),
            vertexColor: gl.getAttribLocation(shaderProgram!, "aVertexColor"),
        },
        uniformLocations: {
            projectionMatrix: gl.getUniformLocation(shaderProgram!, "uProjectionMatrix"),
            modelViewMatrix: gl.getUniformLocation(shaderProgram!, "uModelViewMatrix"),
        },
    };

    const buffer = initBuffers(gl);

    requestAnimationFrame(render);

    function render(now: number) {
        now = now * 0.001;
        const deltaTime = now - start;
        start = now;
        rotation += deltaTime;
        drawScene(gl, programInfo, buffer, rotation);
        requestAnimationFrame(render);
    }
}

main();
