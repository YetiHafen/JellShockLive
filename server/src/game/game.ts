import {Socket} from "../socket";
import {Namespace, Server, Socket as IOSocket} from "socket.io";
import {Maps} from "../maps";

class Game extends Socket {

    public id: string;
    public map: Maps = Maps.RANDOM;
    public playerCount: number;
    public gameState: GameState = GameState.LOBBY;

    constructor(io: Server) {
        super("/game", io);
    }

    onConnection(socket: IOSocket): void {
        // GameState changes
    }

    registerEvents(io: Namespace): void {

    }
}

enum GameState {
    LOBBY,
    START,
    INGAME,
    ENDING
}

export { Game, GameState};