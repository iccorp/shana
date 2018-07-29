import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Commentaire } from './commentaire.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentaireService {

    private resourceUrl = SERVER_API_URL + 'api/commentaires';

    constructor(private http: Http) { }

    create(commentaire: Commentaire): Observable<Commentaire> {
        const copy = this.convert(commentaire);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(commentaire: Commentaire): Observable<Commentaire> {
        const copy = this.convert(commentaire);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Commentaire> {
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
     * Convert a returned JSON object to Commentaire.
     */
    private convertItemFromServer(json: any): Commentaire {
        const entity: Commentaire = Object.assign(new Commentaire(), json);
        return entity;
    }

    /**
     * Convert a Commentaire to a JSON which can be sent to the server.
     */
    private convert(commentaire: Commentaire): Commentaire {
        const copy: Commentaire = Object.assign({}, commentaire);
        return copy;
    }
}
