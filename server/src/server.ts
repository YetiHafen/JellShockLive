import * as http from "http";
import { nanoid } from "nanoid";
import { Server } from "socket.io";
import { Game, GameState } from "./game/game";
import { Lobby } from "./lobby/lobby";

export class JSLServer {

    public static readonly PORT:number = 3000;
    private static instance: JSLServer;
    private readonly server: http.Server;
    private readonly io: Server;

    private lobby: Lobby;
    private game1: Game;
    private game2: Game;
    private readonly games: Game[];

    constructor() {
        JSLServer.instance = this;
        this.server = http.createServer();
        this.io = new Server(this.server);
        this.games = new Array<Game>();
        this.listen();
    }

    private listen(): void {
        this.server.listen(JSLServer.PORT, () => {
            console.log(`JDL-Server started on port ${JSLServer.PORT}`);
        });

        this.game1 = new Game(this.io);
        this.game1.id = nanoid(5);
        this.game1.playerCount = 5;

        this.game2 = new Game(this.io);
        this.game2.id = nanoid(5);
        this.game2.playerCount = 2;
        this.game2.gameState = GameState.ENDING;

        this.lobby = new Lobby(this.io);
        this.lobby.connect();

        this.games.push(this.game1, this.game2);

        this.io.on('connection', (socket: any) => {
            console.log("Client connected");

            socket.on('disconnect', (reason: any) => {
                console.log('Client disconnected Reason: ' + reason);
            });
        });
    }

    public getGames() {
        return this.games;
    }

    public static getInstance(): JSLServer {
        if (!JSLServer.instance) JSLServer.instance = new JSLServer();
        return JSLServer.instance;
    }
}