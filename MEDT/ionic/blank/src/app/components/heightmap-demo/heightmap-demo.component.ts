import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {
  WebGLRenderer,
  Scene,
  PerspectiveCamera,
  MeshBasicMaterial,
  Mesh,
  TextureLoader,
  Texture, BufferGeometry, BufferAttribute
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

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.scene = new Scene();
    this.camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.camera.position.set(0, 25, 50);  // Positioned further back for better view of rotation
    this.camera.lookAt(0, 0, 0);
    this.renderer = new WebGLRenderer({canvas: this.canvasRef.nativeElement});
    this.renderer.setSize(window.innerWidth, window.innerHeight);

    const loader = new TextureLoader();
    loader.load('assets/textures/bsp.png', (texture: Texture) => this.onTextureLoaded(texture));

    window.addEventListener('resize', this.onWindowResize.bind(this));
    window.addEventListener('wheel', this.onMouseWheel.bind(this));

    this.animate();
  }

  private onWindowResize() {
    this.camera.aspect = window.innerWidth / window.innerHeight;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(window.innerWidth, window.innerHeight);
  }

  private onMouseWheel(event: WheelEvent) {
    const zoomChange = event.deltaY * 0.1;  // Adjust zoom speed as needed
    this.camera.position.z += zoomChange;
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
    const colorInfos = [
      [0.53, 0.71, 0.42],  // Hellgrün
      [0.64, 0.77, 0.53],  // Grünlich
      [0.74, 0.82, 0.63],  // Hellgelb-grün
      [0.85, 0.87, 0.74],  // Gelblich
      [0.94, 0.91, 0.80],  // Hellgelb
      [0.97, 0.85, 0.64],  // Gelb-orange
      [0.91, 0.76, 0.51],  // Orange
      [0.79, 0.63, 0.43],  // Braun-orange
      [0.68, 0.53, 0.34],  // Dunkelbraun
      [0.50, 0.38, 0.23]   // Dunkelbraun / Fast Schwarz
  ];
    const colors = [];

    for (let z = 0; z < imageData.height; z++) {
      for (let x = 0; x < imageData.width; x++) {
        const index = x * 4 + z * imageData.width * 4;
        const y = imageData.data[index] / 255;
        vertices.push(x - imageData.width / 2);
        vertices.push(y * 5);
        vertices.push(z - imageData.height / 2);

        if (y >= 0.0 && y < 0.1) {
          colors.push(...colorInfos[0], 1);
        } else if (y >= 0.1 && y < 0.2) {
          colors.push(...colorInfos[1], 1);
        } else if (y >= 0.2 && y < 0.3) {
          colors.push(...colorInfos[2], 1);
        } else if (y >= 0.3 && y < 0.4) {
          colors.push(...colorInfos[3], 1);
        } else if (y >= 0.4 && y < 0.5) {
          colors.push(...colorInfos[4], 1);
        } else if (y >= 0.5 && y < 0.6) {
          colors.push(...colorInfos[5], 1);
        } else if (y >= 0.6 && y < 0.7) {
          colors.push(...colorInfos[6], 1);
        } else if (y >= 0.7 && y < 0.8) {
          colors.push(...colorInfos[7], 1);
        } else if (y >= 0.8 && y < 0.9) {
          colors.push(...colorInfos[8], 1);
        } else if (y >= 0.9 && y <= 1.0) {
          colors.push(...colorInfos[9], 1);
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

    const material = new MeshBasicMaterial({vertexColors: true, wireframe: true});
    this.map = new Mesh(geometry, material);
    this.scene.add(this.map);
  }

  private animate() {
    this.animationFrameId = requestAnimationFrame(() => this.animate());

    // Rotate the map around its own axis
    if (this.map) {
      this.map.rotation.y += 0.01;  // Adjust speed as needed
    }

    this.renderer.render(this.scene, this.camera);
  }

  ngOnDestroy() {
    cancelAnimationFrame(this.animationFrameId);
    window.removeEventListener('resize', this.onWindowResize.bind(this));
    window.removeEventListener('wheel', this.onMouseWheel.bind(this));
    this.renderer.dispose();
  }
}
