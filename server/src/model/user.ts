class User {

    constructor(public uuid: string, public name: string, public role: Role) {}

    public static findUser(uuid: string) {

    }
}

enum Role {
    ADMIN,
    USER
}

export { User, Role };