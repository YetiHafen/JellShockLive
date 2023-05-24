export class User {
    constructor(private uuid: string, private name: string) {}

    get getUuid(): string {
        return this.uuid;
    }

    get getName(): string {
        return this.uuid;
    }
}