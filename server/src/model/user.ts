import {collections} from "../mongo/mongodb";
import {ObjectId} from 'mongodb';

class User {

    constructor(public id: ObjectId, public name: string, public password: string) {}

    public static async findUser(name: string): Promise<User> {

        const user = await collections.users.findOne({ name: name});
        if (user) {
            return new User(user._id, user.name, user.password);
        } else {
            return null;
        }
    }
}

export { User };