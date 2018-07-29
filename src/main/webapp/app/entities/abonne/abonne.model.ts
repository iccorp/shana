import { BaseEntity } from './../../shared';

export class Abonne implements BaseEntity {
    constructor(
        public id?: number,
        public pseudo?: string,
        public email?: string,
        public motDePasse?: string,
    ) {
    }
}
