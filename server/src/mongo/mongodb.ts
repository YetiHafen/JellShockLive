import * as mongodb from "mongodb";
import {Role} from "../model/user";
import * as process from "process";

export const collections: { users?: mongodb.Collection<User> } = {};

export class MongoDBConnection {

    private readonly client: mongodb.MongoClient;

    constructor(url: string) {
        this.client = new mongodb.MongoClient(url);
    }

    public async connect() {
        await this.client.connect();

        const database = this.client.db(process.env.DATABASE_NAME);
        collections.users = database.collection<User>(process.env.USER_COLLECTION_NAME);

        console.log(`Successfully connected to database: ${database.databaseName}`);
    }

    get mongoClient(): mongodb.MongoClient {
        return this.client;
    }
}

export interface User {
    id?: mongodb.ObjectId,
    name: string,
    role: Role,
    ip: string
}