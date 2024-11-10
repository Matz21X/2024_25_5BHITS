import {IonicModule} from '@ionic/angular';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Tab2Page} from './tab2.page';
import {ExploreContainerComponentModule} from '../explore-container/explore-container.module';

import {Tab2PageRoutingModule} from './tab2-routing.module';
import {HeightmapDemoComponent} from "../components/heightmap-demo/heightmap-demo.component";

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    Tab2PageRoutingModule
  ],
  declarations: [Tab2Page, HeightmapDemoComponent]
})
export class Tab2PageModule {
}
