import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as THREE from 'three';


@Component({
  selector: 'app-three-js-demo',
  templateUrl: './three-js-demo.component.html',
  styleUrls: ['./three-js-demo.component.scss'],
})
export class ThreeJsDemoComponent implements OnInit, AfterViewInit {

  @ViewChild('threejs')
  canvasRef!: ElementRef<HTMLCanvasElement>;
  scene!: THREE.Scene;
  camera!: THREE.PerspectiveCamera;
  renderer!: THREE.WebGLRenderer;
  cube!: THREE.Mesh<THREE.BoxGeometry, THREE.MeshBasicMaterial>;

  constructor() {
  }

  ngOnInit(): void {
        throw new Error('Method not implemented.');
    }


  ngAfterViewInit() {
    this.scene = new THREE.Scene();
    this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

    this.renderer = new THREE.WebGLRenderer({canvas: this.canvasRef.nativeElement});
    this.renderer.setSize(window.innerWidth, window.innerHeight);
    //document.body.appendChild(this.renderer.domElement);

    const geometry = new THREE.BoxGeometry( 1, 1, 1 );
    const material = new THREE.MeshBasicMaterial( { color: 0x55a7ce } );
    this.cube = new THREE.Mesh( geometry, material );
    this.scene.add( this.cube );
    this.camera.position.z = 5;
    this.renderer.setAnimationLoop(() => this.animate() );
  }

  animate() {
    this.cube.rotation.x += 0.01;
    this.cube.rotation.y += 0.01;
    this.renderer.render( this.scene, this.camera );
  }


}
