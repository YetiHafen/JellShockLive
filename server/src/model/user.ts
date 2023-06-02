class User {

    constructor(public id: string, public name: string, public role: Role) {}

    public static findUser(id: string) {

    }
}

enum Role {
    ADMIN = "Admin",
    USER = "User"
}

export { User, Role };