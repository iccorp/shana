import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public objet?: string,
        public contenu?: string,
        public abonneId?: number,
    ) {
    }
}
