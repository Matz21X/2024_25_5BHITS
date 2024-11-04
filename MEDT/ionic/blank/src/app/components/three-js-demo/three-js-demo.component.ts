import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {BoxGeometry, MeshBasicMaterial, Mesh, PerspectiveCamera, Scene, WebGLRenderer} from "three";

@Component({
  selector: 'app-three-js-demo',
  templateUrl: './three-js-demo.component.html',
  styleUrls: ['./three-js-demo.component.scss'],
})
export class ThreeJsDemoComponent implements AfterViewInit {
  @ViewChild('threejs') canvasRef!: ElementRef<HTMLCanvasElement>;
  scene!: Scene;
  camera!: PerspectiveCamera;
  renderer!: WebGLRenderer;
  cube!: Mesh<BoxGeometry, MeshBasicMaterial>;
  rotationSpeed: number = 0.01;  // Standardgeschwindigkeit der Rotation
  constructor() {
  }

  ngAfterViewInit() {
    this.scene = new Scene();
    this.camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    this.renderer = new WebGLRenderer({canvas: this.canvasRef.nativeElement});
    this.renderer.setSize(window.innerWidth, window.innerHeight);
    const geometry = new BoxGeometry(1, 1, 1);
    const material = new MeshBasicMaterial({color: 0x0000ff});
    this.cube = new Mesh(geometry, material);
    this.scene.add(this.cube);
    this.camera.position.z = 5;
    this.renderer.setAnimationLoop(() => this.animate());
  }

  animate() {
    this.cube.rotation.x += this.rotationSpeed; // Nutzt die Variable für die Geschwindigkeit
    this.cube.rotation.y += this.rotationSpeed;
    this.renderer.render(this.scene, this.camera);
  }
}
