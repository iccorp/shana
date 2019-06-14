import { Route } from '@angular/router';

import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        pageTitle: 'shanaApp.settings.title'
    }
};
