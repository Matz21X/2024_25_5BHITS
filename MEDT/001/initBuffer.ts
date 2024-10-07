interface Buffers {
    position: WebGLBuffer | null;
    color: WebGLBuffer | null;
}

function initBuffers(gl: WebGLRenderingContext): Buffers {
    const positionBuffer = initPositionBuffer(gl);
    const colorBuffer = initColorBuffer(gl);
    return {
        position: positionBuffer,
        color: colorBuffer
    };
}

function initPositionBuffer(gl: WebGLRenderingContext): WebGLBuffer | null {
    const positionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
    const positions = [
        1.0, 1.0,
        -1.0, 1.0,
        1.0, -1.0,
        -1.0, -1.0
    ];
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(positions), gl.STATIC_DRAW);
    return positionBuffer;
}

function initColorBuffer(gl: WebGLRenderingContext): WebGLBuffer | null {
    const colors = [
        1.0, 1.0, 1.0, 1.0,  // white
        1.0, 0.0, 0.0, 1.0,  // red
        0.0, 1.0, 0.0, 1.0,  // green
        0.0, 0.0, 1.0, 1.0   // blue
    ];
    const colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
    return colorBuffer;
}

export { initBuffers };
