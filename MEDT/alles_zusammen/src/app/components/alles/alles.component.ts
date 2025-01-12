import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {
  WebGLRenderer,
  Scene,
  PerspectiveCamera,
  MeshStandardMaterial,
  AmbientLight,
  Mesh,
  TextureLoader,
  Texture,
  BufferGeometry,
  BufferAttribute,
  PointLight,
  SphereGeometry,
  CylinderGeometry,
  BoxGeometry,
  MeshBasicMaterial,
  MeshPhongMaterial,
  PlaneGeometry, DoubleSide
} from "three";
import * as THREE from 'three';
import {GLTFLoader} from "three/examples/jsm/loaders/GLTFLoader";


@Component({
  selector: 'app-alles',
  templateUrl: './alles.component.html',
  styleUrls: ['./alles.component.scss'],
  standalone: true
})
export class AllesComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('map') canvasRef!: ElementRef<HTMLCanvasElement>;
  scene!: Scene;
  camera!: PerspectiveCamera;
  renderer!: WebGLRenderer;
  map!: Mesh;
  normalMapPlane!: Mesh;
  directionalLight!: PointLight;
  animationFrameId!: number;
  clock = new THREE.Clock();
  gltfLoader!: GLTFLoader;
  lightAngle: number = 0;

  isDragging = false; // Ob die Maus gedrückt ist
  lastMouseY = 0; // Letzte Y-Position der Maus
  rotationAngle = 0; // Aktueller Rotationswinkel um die X-Achse

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.scene = new Scene();
    this.camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.camera.position.set(0, 25, 25);
    this.camera.lookAt(0, 0, 0);

    this.renderer = new WebGLRenderer({canvas: this.canvasRef.nativeElement, antialias: true});
    this.renderer.setSize(window.innerWidth, window.innerHeight);
    this.renderer.shadowMap.enabled = true;
    this.gltfLoader = new GLTFLoader();

    // Beleuchtung hinzufügen
    this.directionalLight = new PointLight(0x94d1f1, 0.1); // Eisblau
    this.directionalLight.distance = 25;
    this.directionalLight.power = 1000;
    this.directionalLight.position.set(0, 9, 0);
    this.directionalLight.castShadow = true;
    this.directionalLight.shadow.mapSize.width = 4096;
    this.directionalLight.shadow.mapSize.height = 4096;
    this.directionalLight.shadow.camera.near = 0.5;
    this.directionalLight.shadow.camera.far = 100;
    this.directionalLight.shadow.bias = -0.002; // Bias anpassen, um Artefakte zu minimieren

    this.scene.add(this.directionalLight);

    const ambientLight = new AmbientLight(0x403040, 1);
    this.scene.add(ambientLight);

    this.addBalls(); // Balls hinzufügen


    const loader = new TextureLoader();
    loader.load('assets/textures/heightmap.png', (texture: Texture) => this.onTextureLoaded(texture));
    loader.load('assets/textures/normalmap.jpg', (texture: Texture) => this.onNormalMapLoaded(texture));
    this.addGltfObject();

    window.addEventListener('resize', this.onWindowResize.bind(this));
    this.renderer.domElement.addEventListener('mousedown', this.onMouseDown.bind(this));
    this.renderer.domElement.addEventListener('mousemove', this.onMouseMove.bind(this));
    this.renderer.domElement.addEventListener('mouseup', this.onMouseUp.bind(this));
    this.animate();
  }

  private onMouseDown(event: MouseEvent) {
    this.isDragging = true;
    this.lastMouseY = event.clientY; // Startpunkt speichern
  }

  private onMouseMove(event: MouseEvent) {
    if (!this.isDragging) return;

    const deltaY = event.clientY - this.lastMouseY; // Differenz berechnen
    this.lastMouseY = event.clientY;

    // Rotation anpassen
    const deltaRotation = deltaY  * 0.01; // Skaliere die Änderung für ein sanftes Drehen
    this.rotationAngle += deltaRotation;

    // Rotation begrenzen, damit sie nicht zu extrem wird
    this.rotationAngle = Math.max(Math.min(this.rotationAngle, Math.PI / 2), -Math.PI / 2); // -90° bis 90°

    // Die Szene um die X-Achse rotieren
    this.scene.rotation.x = this.rotationAngle;
  }


  private onMouseUp() {
    this.isDragging = false; // Dragging beenden
  }

  private addBalls() {
    const winwRedGlowMaterial = new MeshPhongMaterial({ color: 0x8b0000 }); // Weinrot
    const purpleGlowMaterial = new MeshBasicMaterial({ color: 0x800080}); // Lila
    const greenGlowMaterial = new MeshBasicMaterial({ color: 0x2fff00 }); // Giftgrün
    const ballGeometry = new SphereGeometry(2 , 32, 32);

    const redBall1 = new Mesh(ballGeometry, winwRedGlowMaterial);
    redBall1.position.set(-5, 10, 5);

    const redBall2 = new Mesh(ballGeometry, winwRedGlowMaterial);
    redBall2.position.set(-8, 8, 8);

    const greenBall = new Mesh(ballGeometry, greenGlowMaterial);
    greenBall.position.set(5, 5, 0);

    const purpleBall = new Mesh(ballGeometry, purpleGlowMaterial);
    purpleBall.position.set(10, 11, 10);

    this.scene.add(redBall1, redBall2, purpleBall, greenBall);
  }


  private onWindowResize() {
    this.camera.aspect = window.innerWidth / window.innerHeight;
    this.camera.updateProjectionMatrix();
    this.renderer.setSize(window.innerWidth, window.innerHeight);
  }

  private onTextureLoaded(texture: Texture) {
    console.log('Heightmap Textur geladen');

    const canvas = document.createElement('canvas');
    canvas.width = texture.image.width;
    canvas.height = texture.image.height;

    const context = canvas.getContext('2d') as CanvasRenderingContext2D;
    context.drawImage(texture.image, 0, 0);

    const data = context.getImageData(0, 0, canvas.width, canvas.height);
    this.generateTerrain(data);
  }

  private onNormalMapLoaded(texture: Texture) {
    console.log('Normalmap Textur geladen');
    this.addNormalMap(texture);
  }

  private generateTerrain(imageData: ImageData) {
    const vertices = [];
    const colorInfos = [
      [0.38, 0.68, 0.3],
      [0.91, 0.58, 0.41],
      [1, 1, 1]
    ];

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

    const material = new MeshStandardMaterial({vertexColors: true, flatShading: true});
    this.map = new Mesh(geometry, material);
    this.renderer.shadowMap.enabled = true;
    this.map.castShadow = true;
    this.map.receiveShadow = true;
    this.scene.add(this.map);
  }

  private addNormalMap(texture: Texture) {
    const geometry = new PlaneGeometry(35, 22, 100, 100);
    const material = new MeshPhongMaterial({
      color: 0xffffff,
      side: DoubleSide,
      normalMap: texture
    });

    this.normalMapPlane = new Mesh(geometry, material);
    this.normalMapPlane.rotation.x = Math.PI;
    this.normalMapPlane.rotation.y = Math.PI;

    this.normalMapPlane.receiveShadow = true;
    this.normalMapPlane.castShadow = true;

    // Normalmap hinter der Heightmap und versetzt nebeneinander
    this.normalMapPlane.position.set(0, 10, -16);


    this.scene.add(this.normalMapPlane);
  }

  private lastRenderTime = 0;
  private animate() {
    const now = performance.now();
    const deltaTime = now - this.lastRenderTime;

    // Nur jedes 16 ms einen neuen Frame rendern (ungefähr 60 FPS)
    if (deltaTime >= 10) {
      this.lastRenderTime = now;

      this.animationFrameId = requestAnimationFrame(() => this.animate());

      // Lichtposition anpassen
      this.lightAngle += 0.005;
      this.directionalLight.position.x = Math.sin(this.lightAngle) * 15;
      this.directionalLight.position.z = Math.cos(this.lightAngle) * 15;

      // Rendering
      this.renderer.render(this.scene, this.camera);
    } else {
      this.animationFrameId = requestAnimationFrame(() => this.animate());
    }
  }

  private addGltfObject(position = new THREE.Vector3(0, 15, 0)) {
    this.gltfLoader.load(
      'assets/blender_model/baum.glb',
      (gltf) => {
        const mesh = gltf.scene;

        mesh.scale.set(0.4, 0.4, 0.4)
        mesh.position.set(15, -20, 15);

        mesh.position.set(position.x, position.y, position.z);
        this.scene.add(mesh);

        if (gltf.animations && gltf.animations.length > 0) {
          const mixer = new THREE.AnimationMixer(mesh);

          gltf.animations.forEach((clip) => {
            mixer.clipAction(clip).play();
          });

          // add mixer to animation loop
          this.renderer.setAnimationLoop(() => {
            const delta = this.clock.getDelta();
            mixer.update(delta); // Animation aktualisieren
            this.animate(); // execute movement
          });
        } else {
          console.log('Keine Animationen im GLB-Modell gefunden.');
        }
      },
      undefined,
      (error) => {
        console.error('Fehler beim Laden der GLB-Datei:', error);
      }
    );
  }


  ngOnDestroy() {
    cancelAnimationFrame(this.animationFrameId);
    window.removeEventListener('resize', this.onWindowResize.bind(this));
    this.renderer.domElement.removeEventListener('mousedown', this.onMouseDown.bind(this));
    this.renderer.domElement.removeEventListener('mousemove', this.onMouseMove.bind(this));
    this.renderer.domElement.removeEventListener('mouseup', this.onMouseUp.bind(this));
    this.renderer.dispose();
  }
}
