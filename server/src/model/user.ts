import {collections} from "../mongo/mongodb";
import {ObjectId} from 'mongodb';

class User {

    constructor(public id: ObjectId, public name: string, public password: string, public sessionId: string, public position: number) {}

    public static async findUser(name: string, socketId: string): Promise<User> {

        const user = await collections.users.findOne({ name: name });
        if (user) {
            return new User(user._id, user.name, user.password, socketId, 0);
        } else {
            return null;
        }
    }
}

export { User };