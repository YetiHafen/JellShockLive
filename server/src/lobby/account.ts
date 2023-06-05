import {Socket} from "../socket";
import {Server, Socket as IOSocket} from "socket.io";
import {collections} from "../mongo/mongodb";
import {ObjectId} from 'mongodb';

export class Account extends Socket {

    constructor(io: Server) {
        super("/account", io);
    }

    onConnection(io: IOSocket): void {
    }

    async registerEvents(io: IOSocket): Promise<void> {
        io.on("data", async (arg1, args2, callback): Promise<void> => {
            console.log(arg1);
            console.log(args2);
            let ack = await this.checkUser(arg1, args2);
            if (ack === "ok" || ack === "nok") {

            }
            callback({
                status: ack
            });
        });
    }

    async checkUser(name: string, password: string): Promise<string> {
        try {
            const user = await collections.users.findOne({ name: name });
            console.log(user);
            if (user) return "ok";
            await this.insertUser(name, password);
            return "nok";
        } catch (err) {
            console.error(err);
            return "err";
        }
    }

    async insertUser(name: string, password: string): Promise<void> {
        try {
            const result =  await collections.users.insertOne({
                name: name,
                password: password
            });
        } catch (err) {
            console.error(err);
        }
    }
}

export interface User {
    _id: ObjectId,
    name: string,
    password: string
}