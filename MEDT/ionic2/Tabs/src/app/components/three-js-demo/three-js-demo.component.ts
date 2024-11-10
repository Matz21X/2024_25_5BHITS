import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {WebGLRenderer, Scene, PerspectiveCamera, BoxGeometry, Clock, MeshBasicMaterial, Mesh, TextureLoader} from "three";

@Component({
  selector: 'app-three-js-demo',
  templateUrl: './three-js-demo.component.html',
  styleUrls: ['./three-js-demo.component.scss'],
})
export class ThreeJsDemoComponent implements OnInit, AfterViewInit {

  @ViewChild('threejs') canvasRef!: ElementRef<HTMLCanvasElement>;

  scene!: Scene;
  camera!: PerspectiveCamera;
  renderer!: WebGLRenderer;
  cube!: Mesh;
  rotationSliderValueX: number = 0;
  rotationSliderValueY: number = 0;
  clock = new Clock();

  constructor() {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.scene = new Scene();
    this.camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.renderer = new WebGLRenderer({canvas: this.canvasRef.nativeElement});
    this.renderer.setSize(window.innerWidth, window.innerHeight);

    const geometry = new BoxGeometry(1, 1, 1);

    // Bildtextur laden und als Material anwenden
    const textureLoader = new TextureLoader();
    const texture = textureLoader.load('assets/1517573582485.jpg'); // Pfad zur Bilddatei
    const material = new MeshBasicMaterial({ map: texture });

    this.cube = new Mesh(geometry, material);
    this.scene.add(this.cube);
    this.camera.position.z = 5;

    this.renderer.setAnimationLoop(() => this.animate());
  }

  animate() {
    const elapsed = this.clock.getDelta()
    this.cube.rotation.x += (this.rotationSliderValueX / 100) * elapsed;
    this.cube.rotation.y += (this.rotationSliderValueY / 100) * elapsed;
    this.renderer.render(this.scene, this.camera);
  }
}
