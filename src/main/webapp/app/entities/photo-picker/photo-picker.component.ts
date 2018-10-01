import { Component, OnDestroy, OnInit, Input, ElementRef, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { Photo } from '../photo/photo.model';
import { PhotoService } from '../photo/photo.service';
import { JhiDataUtils } from '../../../../../../node_modules/ng-jhipster';
import { BASE_LINK_YOUTUBE_EMBED, BASE_LINK_YOUTUBE, BASE_LINK_YOUTUBE_VI } from '../../app.constants';
import { DomSanitizer } from '../../../../../../node_modules/@angular/platform-browser';

@Component({
    selector: 'jhi-photo-picker',
    templateUrl: './photo-picker.component.html',
    styleUrls: ['./photo-picker.component.css']
})
export class PhotoPickerComponent implements OnInit, OnDestroy, OnChanges {

    selectedPhoto: Photo = new Photo();
    videoLinkInput = '';
    @Input() activateVideoPicker: boolean;
    @Input() photoInitiale: Photo;
    @Input() vignettes: Photo[];
    @Input() titre: string;
    @Output() updatePhoto: EventEmitter<Photo> = new EventEmitter<Photo>();
    @Input() idVideo: string;
    @Output() updateVideo: EventEmitter<string> = new EventEmitter<string>();

    constructor(
        private dataUtils: JhiDataUtils,
        public sanitizer: DomSanitizer
    ) {}

    ngOnChanges(changes: SimpleChanges): void {
        if (this.photoInitiale) {
            this.selectedPhoto = this.photoInitiale;
        }
        if (this.idVideo) {
            this.videoLinkInput = BASE_LINK_YOUTUBE + this.idVideo;
        }
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {}

    outputPhoto() {
        this.updatePhoto.emit(this.selectedPhoto);
    }

    outputVideo() {
        this.updateVideo.emit(this.idVideo);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.selectedPhoto = new Photo();
        this.outputPhoto();
        /* this.dataUtils.clearInputImage(this.selectedPhoto, this.elementRef, field, fieldContentType, idInput); */
    }

    clearVideoInput() {
        this.videoLinkInput = '';
        this.idVideo = '';
    }

    setFileData(event, entity, field, isImage) {
        if (this.activateVideoPicker) {
            this.clearVideoInput();
        }
        this.selectedPhoto.id = null;
        this.selectedPhoto.idPhoto = null;
        this.dataUtils.setFileData(event, entity, field, isImage);
        console.log('selectedPhoto after setFile: ' + JSON.stringify(this.selectedPhoto));
        this.outputPhoto();
    }

    updateSelectedPhoto(vignette: Photo) {
        if (this.activateVideoPicker) {
            this.clearVideoInput();
        }
        this.selectedPhoto = vignette;
        this.outputPhoto()
    }

    updateVideoLink() {
        if (this.isInputVideoLinkValid()) {
            this.idVideo = this.extractIdVideo();
            this.clearInputImage('photo', 'photoContentType', 'file_photo');
            this.outputVideo();
        }
    }

    isInputVideoLinkValid() {
        let idVideo = this.videoLinkInput.split('?v=')[1] || '';
        if (idVideo.indexOf('&') >= 0) {
            idVideo = idVideo.split('&')[0] || '';
        }
        return idVideo.length > 1;
    }

    extractIdVideo() {
        let idVideo = this.videoLinkInput.split('?v=')[1] || '';
        if (idVideo.indexOf('&') >= 0) {
            idVideo = idVideo.split('&')[0] || '';
        }
        return idVideo;
    }

    getFinalVideoLink() {
        return this.sanitizer.bypassSecurityTrustResourceUrl(BASE_LINK_YOUTUBE_VI + this.idVideo + '/default.jpg');
    }

}
