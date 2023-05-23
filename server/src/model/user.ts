export class User {
    constructor(private _uuid: string, private _name: string) {}

    get uuid(): string {
        return this._uuid;
    }

    get name(): string {
        return this._name;
    }
}