import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {
  WebGLRenderer,
  Scene,
  PerspectiveCamera,
  MeshBasicMaterial,
  Mesh,
  TextureLoader,
  Texture, BufferGeometry, BufferAttribute, Vector2, MeshStandardMaterial, AmbientLight
} from "three";

@Component({
  selector: 'app-heightmap-demo',
  templateUrl: './heightmap-demo.component.html',
  styleUrls: ['./heightmap-demo.component.scss'],
})
export class HeightmapDemoComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild('heightmap') canvasRef!: ElementRef<HTMLCanvasElement>;
  scene!: Scene;
  camera!: PerspectiveCamera;
  renderer!: WebGLRenderer;
  map!: Mesh;
  animationFrameId!: number;

  // Variables for mouse interaction
  isDragging = false;
  previousMousePosition = new Vector2();

  constructor() {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.scene = new Scene();
    this.camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.camera.position.set(0, 25, 100);
    this.camera.lookAt(0, 0, 0);
    this.renderer = new WebGLRenderer({canvas: this.canvasRef.nativeElement});
    this.renderer.setSize(window.innerWidth, window.innerHeight);
    const light = new AmbientLight( 0xd64d5b ); // soft white light
    this.scene.add( light );

    const loader = new TextureLoader();
    loader.load('assets/textures/heightmap2.png', (texture: Texture) => this.onTextureLoaded(texture));

    window.addEventListener('resize', this.onWindowResize.bind(this)); // Resize-Listener

    // Mouse events for rotating the map
    this.renderer.domElement.addEventListener('mousedown', this.onMouseDown.bind(this));
    this.renderer.domElement.addEventListener('mousemove', this.onMouseMove.bind(this));
    this.renderer.domElement.addEventListener('mouseup', this.onMouseUp.bind(this));

    this.animate();
  }

  private onWindowResize() {
    this.camera.aspect = window.innerWidth / window.innerHeight;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(window.innerWidth, window.innerHeight);
  }

  private onTextureLoaded(texture: Texture) {
    console.log('Texture loaded');
    const canvas = document.createElement('canvas');
    canvas.width = texture.image.width;
    canvas.height = texture.image.height;

    const context = canvas.getContext('2d') as CanvasRenderingContext2D;
    context.drawImage(texture.image, 0, 0);

    const data = context.getImageData(0, 0, canvas.width, canvas.height);
    this.generateTerrain(data);
  }

  private generateTerrain(imageData: ImageData) {
    const vertices = [];
    const colorInfos = [[0.38, 0.68, 0.3], [0.91, 0.58, 0.41], [1, 1, 1]];
    const colors = [];

    for (let z = 0; z < imageData.height; z++) {
      for (let x = 0; x < imageData.width; x++) {
        const index = x * 4 + z * imageData.width * 4;
        const y = imageData.data[index] / 255;
        vertices.push(x - imageData.width / 2);
        vertices.push(y * 5);
        vertices.push(z - imageData.height / 2);

        if (y <= 0.5) {
          colors.push(...colorInfos[0], 1);
        } else if (y > 0.5 && y <= 0.8) {
          colors.push(...colorInfos[1], 1);
        } else {
          colors.push(...colorInfos[2], 1);
        }
      }
    }

    const indices = [];
    for (let j = 0; j < imageData.height - 1; j++) {
      let offset = j * imageData.width;
      for (let i = offset; i < offset + imageData.height - 1; i++) {
        indices.push(i, i + imageData.width, i + 1);
        indices.push(i + 1, i + imageData.width, i + 1 + imageData.width);
      }
    }

    const geometry = new BufferGeometry();
    geometry.setIndex(indices);
    geometry.setAttribute('position', new BufferAttribute(new Float32Array(vertices), 3));
    geometry.setAttribute('color', new BufferAttribute(new Float32Array(colors), 4));

    const material = new MeshStandardMaterial({ vertexColors: true, wireframe: true });
    this.map = new Mesh(geometry, material);
    this.scene.add(this.map);
  }

  private onMouseDown(event: MouseEvent) {
    this.isDragging = true;
    this.previousMousePosition.set(event.clientX, event.clientY);
  }

  private onMouseMove(event: MouseEvent) {
    if (this.isDragging && this.map) {
      const deltaMove = new Vector2(
        event.clientX - this.previousMousePosition.x,
        event.clientY - this.previousMousePosition.y
      );

      // Rotation around y-axis
      const rotationSpeed = 0.005;
      this.map.rotation.y += deltaMove.x * rotationSpeed;
      // Rotation around x-axis (optional, for tilting up/down)
      this.map.rotation.x += deltaMove.y * rotationSpeed;

      this.previousMousePosition.set(event.clientX, event.clientY);
    }
  }

  private onMouseUp() {
    this.isDragging = false;
  }

  private animate() {
    this.animationFrameId = requestAnimationFrame(() => this.animate());
    this.renderer.render(this.scene, this.camera);
  }

  ngOnDestroy() {
    cancelAnimationFrame(this.animationFrameId);
    window.removeEventListener('resize', this.onWindowResize.bind(this));
    this.renderer.dispose();

    // Remove mouse event listeners
    this.renderer.domElement.removeEventListener('mousedown', this.onMouseDown.bind(this));
    this.renderer.domElement.removeEventListener('mousemove', this.onMouseMove.bind(this));
    this.renderer.domElement.removeEventListener('mouseup', this.onMouseUp.bind(this));
  }
}
