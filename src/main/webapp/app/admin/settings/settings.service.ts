import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';
import { Setting } from './settings.model';

@Injectable()
export class SettingsService {
    constructor(private http: Http) { }

    getSettings(): Observable<Setting> {
        return this.http.get(SERVER_API_URL + 'api/settings/photo-couverture').map((res: Response) => res.json());
    }

    setSetting(setting: Setting): Observable<Setting> {
        return this.http.put(SERVER_API_URL + 'api/settings/photo-couverture', setting).map((res: Response) => res.json());
    }
}
