import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import {EditorModule} from 'primeng/editor';
import {SharedModule} from 'primeng/components/common/shared';

import {
    ShanaSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';
import { PhotoPickerComponent } from '../entities/photo-picker/photo-picker.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatSelectModule} from '@angular/material/select';
import {DataScrollerModule} from 'primeng/datascroller';
import { DeviceDetectorModule } from 'ngx-device-detector';

@NgModule({
    imports: [
        ShanaSharedLibsModule,
        EditorModule,
        SharedModule,
        MatTabsModule,
        MatGridListModule,
        MatCardModule,
        MatButtonModule,
        MatExpansionModule,
        MatSelectModule,
        DataScrollerModule,
        DeviceDetectorModule.forRoot()
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        PhotoPickerComponent,
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'fr'
        },
    ],
    exports: [
        ShanaSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        EditorModule,
        DataScrollerModule,
        PhotoPickerComponent,
        MatTabsModule,
        MatGridListModule,
        MatCardModule,
        MatButtonModule,
        MatExpansionModule,
        MatSelectModule,
        DeviceDetectorModule
    ]
})
export class ShanaSharedCommonModule {}
