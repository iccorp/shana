import { Component, OnDestroy, OnInit, Input, ElementRef, Output, EventEmitter, OnChanges } from '@angular/core';
import { Photo } from '../photo/photo.model';
import { PhotoService } from '../photo/photo.service';
import { JhiDataUtils } from '../../../../../../node_modules/ng-jhipster';

@Component({
    selector: 'jhi-photo-picker',
    templateUrl: './photo-picker.component.html',
    styleUrls: ['./photo-picker.component.css']
})
export class PhotoPickerComponent implements OnInit, OnDestroy, OnChanges {

    selectedPhoto: Photo = new Photo();

    @Input() photoInitiale: Photo;
    @Input() vignettes: Photo[];
    @Input() titre: string;
    @Output() updatePhoto: EventEmitter<Photo> = new EventEmitter<Photo>();

    constructor(
        private dataUtils: JhiDataUtils,
    ) {}

    ngOnChanges(): void {
        if (this.photoInitiale) {
            this.selectedPhoto = this.photoInitiale;
        }
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {}

    outputPhoto() {
        this.updatePhoto.emit(this.selectedPhoto);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.selectedPhoto = new Photo();
        /* this.dataUtils.clearInputImage(this.selectedPhoto, this.elementRef, field, fieldContentType, idInput); */
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
        this.outputPhoto();
    }
}
