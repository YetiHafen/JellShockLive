import * as mongodb from "mongodb";
import * as process from "process";
import {OptionalId} from "mongodb";
import {User} from "../lobby/account";

export const collections: { users?: mongodb.Collection<OptionalId<User>> } = {};

export class MongoDBConnection {

    private readonly client: mongodb.MongoClient;

    constructor(url: string) {
        this.client = new mongodb.MongoClient(url);
    }

    public async connect() {
        await this.client.connect();

        const database = this.client.db(process.env.DATABASE_NAME);
        collections.users = database.collection<OptionalId<User>>(process.env.USER_COLLECTION_NAME);

        console.log(`Successfully connected to database: ${database.databaseName}`);
    }

    get mongoClient(): mongodb.MongoClient {
        return this.client;
    }
}