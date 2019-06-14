import { Component, OnInit } from '@angular/core';
import { SettingsService } from './settings.service';
import { Photo, PhotoService } from '../../entities/photo';
import { Setting } from './settings.model';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    vignettes: Photo[];
    photoCouverture: Photo;

    constructor(
        private photoService: PhotoService,
        private settionService: SettingsService
    ) {
    }

    ngOnInit() {
        this.loadVignettesAndInitPhotoCouverture();
    }

    loadVignettesAndInitPhotoCouverture() {
        this.vignettes = [];
        this.photoService
            .findAllVignette()
            .subscribe((response) => {
                this.vignettes = response;
                this.setPhotoCouvertureInitiale();
            });
    }

    setPhotoCouvertureInitiale() {
        this.settionService.getSettings()
        .subscribe((setting: Setting) => {
            this.photoCouverture = new Photo();
            this.vignettes .forEach((vignette) => {
                if (vignette.idPhoto === setting.photoCouverture) {
                    this.photoCouverture.photo = vignette.photo;
                    this.photoCouverture.photoContentType = vignette.photoContentType;
                    this.photoCouverture.idPhoto = setting.photoCouverture;
                }
            });
        })
    }

    updatePhotoCouverture(photo: Photo) {
        this.photoCouverture.photo = photo.photo;
        this.photoCouverture.idPhoto = photo.idPhoto;
    }

    save() {
        if (this.photoCouverture && this.photoCouverture.photo && !this.photoCouverture.idPhoto) {
            console.log('cas nouvelle photo');
            this.photoService
            .create(this.photoCouverture)
            .subscribe((savedPhoto: Photo) => {
                this.photoCouverture.idPhoto = savedPhoto.idPhoto;
                console.log('dans le subscribe');
                this.savePhotoCouvertureSetting();
            });
        } else {
            console.log('cas ancienne photo');
            this.savePhotoCouvertureSetting();
        }
    }

    savePhotoCouvertureSetting() {
        const setting: Setting = new Setting(this.photoCouverture.idPhoto || '');
        console.log('Settings: ' + JSON.stringify(setting));
        this.settionService.setSetting(setting)
        .subscribe((res) => console.log('resUpdateSettings: ' + JSON.stringify(res)));
    }
}
