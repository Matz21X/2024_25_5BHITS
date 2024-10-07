export function initShaderProgram(gl: WebGLRenderingContext, vsSource: string, fsSource: string): WebGLProgram | null {
    const vertexShader = loadShader(gl, gl.VERTEX_SHADER, vsSource);
    const fragmentShader = loadShader(gl, gl.FRAGMENT_SHADER, fsSource);

    // Sicherstellen, dass die Shader erfolgreich erstellt wurden
    if (!vertexShader || !fragmentShader) {
        return null; // Rückgabe von null, wenn einer der Shader nicht erstellt werden konnte
    }

    const shaderProgram = gl.createProgram();

    // Überprüfen, ob shaderProgram erfolgreich erstellt wurde
    if (!shaderProgram) {
        console.error('Failed to create shader program');
        return null;
    }

    gl.attachShader(shaderProgram, vertexShader);
    gl.attachShader(shaderProgram, fragmentShader);
    gl.linkProgram(shaderProgram);

    if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
        alert(`Unable to initialize the shader program: ${gl.getProgramInfoLog(shaderProgram)}`);
        gl.deleteProgram(shaderProgram); // Shader-Programm löschen, wenn es nicht verlinkt werden konnte
        return null;
    }

    return shaderProgram;
}

function loadShader(gl: WebGLRenderingContext, type: number, source: string): WebGLShader | null {
    const shader = gl.createShader(type);

    // Überprüfen, ob der Shader erfolgreich erstellt wurde
    if (!shader) {
        console.error('Failed to create shader');
        return null; // Rückgabe von null, wenn der Shader nicht erstellt werden konnte
    }

    gl.shaderSource(shader, source);
    gl.compileShader(shader);

    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
        alert(`An error occurred compiling the shaders: ${gl.getShaderInfoLog(shader)}`);
        gl.deleteShader(shader);
        return null;
    }

    return shader;
}

