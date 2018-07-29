import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Abonne } from './abonne.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AbonneService {

    private resourceUrl = SERVER_API_URL + 'api/abonnes';

    constructor(private http: Http) { }

    create(abonne: Abonne): Observable<Abonne> {
        const copy = this.convert(abonne);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(abonne: Abonne): Observable<Abonne> {
        const copy = this.convert(abonne);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Abonne> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Abonne.
     */
    private convertItemFromServer(json: any): Abonne {
        const entity: Abonne = Object.assign(new Abonne(), json);
        return entity;
    }

    /**
     * Convert a Abonne to a JSON which can be sent to the server.
     */
    private convert(abonne: Abonne): Abonne {
        const copy: Abonne = Object.assign({}, abonne);
        return copy;
    }
}
