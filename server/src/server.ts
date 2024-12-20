import * as http from "http";
import {nanoid} from "nanoid";
import {Server} from "socket.io";
import {Game, GameId, GameState, IGame} from "./game/game";
import {Lobby} from "./lobby/lobby";
import {Maps} from "./maps";
import {User} from "./model/user";
import {MongoDBConnection} from "./mongo/mongodb";
import * as process from "process";
import * as dotenv from 'dotenv';
import {Account} from "./lobby/account";

export class JSLServer {

    public static readonly PORT:number = 3000;
    private static instance: JSLServer;
    private readonly mongo: MongoDBConnection;
    private readonly server: http.Server;
    private readonly io: Server;

    private account: Account;
    private lobby: Lobby;
    private readonly games: Map<GameId, Game>;

    constructor() {
        dotenv.config();
        JSLServer.instance = this;
        let url = `mongodb://${process.env.USER}:${process.env.PASSWORD}@${process.env.HOSTNAME}:${process.env.PORT}/?authSource=admin`;
        this.mongo = new MongoDBConnection(url);
        this.server = http.createServer();
        this.io = new Server(this.server);
        this.games = new Map<string, Game>;
        this.listen();
    }

    public createGame(name: string, password: string, map: Maps, maxPlayers: number, user: User) {
        const options: IGame = {
            gameId: nanoid(6),
            name: name,
            password: password,
            maxPlayers: maxPlayers,
            map: map,
            admin: user
        }
        let game: Game = new Game(this.io, options);
        game.connect();

        this.games.set(options.gameId, game);

        setTimeout((): void => {
            if (!this.games.has(options.gameId)) return;
            this.checkGameEnd(game);
        }, 5000);
    }

    public async checkJoinGame(gameId: string, password: string): Promise<string> {
        let game: Game = this.findGame(gameId);

        if (game.playerCount === game.maxPlayers) return "max";
        if (game.gameState != GameState.LOBBY) return "started";
        if (game.password != password) return "pw";
        return "ok";
    }

    public findGame(gameId: string): Game | undefined {
        return this.games.get(gameId);
    }

    public checkGameEnd(game: Game) {
        if (!(game.gameState === GameState.ENDING)) return;
        this.games.delete(game.gameId);
        console.log("Stopped game " + game.gameId);
    }

    private listen(): void {
        this.mongo.connect().then((): void => {
            this.server.listen(JSLServer.PORT, (): void => {
                console.log(`JDL-Server started on port ${JSLServer.PORT}`);
            });

            this.account = new Account(this.io);
            this.account.connect();

            this.lobby = new Lobby(this.io);
            this.lobby.connect();

            this.io.on('connection', (socket: any): void => {
                console.log(`Client {${socket.id}} connected`);

                socket.on('disconnect', (reason: any): void => {
                    console.log('Client disconnected Reason: ' + reason);
                });
            });
        }).catch((error: Error): void => {
            console.error('Database connection failed', error);
            process.exit();
        });
    }

    public getGames(): Map<GameId, Game> {
        return this.games;
    }

    public getDatabase(): MongoDBConnection {
        return this.mongo;
    }

    public static getInstance(): JSLServer {
        if (!JSLServer.instance) JSLServer.instance = new JSLServer();
        return JSLServer.instance;
    }
}