import { JhiHttpInterceptor, JhiEventManager } from 'ng-jhipster';
import { RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

export class ErrorHandlerInterceptor extends JhiHttpInterceptor {

    constructor(private eventManager: JhiEventManager) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        console.log('Intercepted 6: ' + JSON.stringify(options))
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return <Observable<Response>> observable.catch((error) => {
            if (!(error.status === 401 && (error.text() === '' ||
                (error.json().path && error.json().path.indexOf('/api/account') === 0 )))) {
                if (this.eventManager !== undefined) {
                    this.eventManager.broadcast( {name: 'shanaApp.httpError', content: error});
                }
            }
            console.log('Intercepted 7: ' + JSON.stringify(error))
            return Observable.throw(error);
        });
    }
}
