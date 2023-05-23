import {Socket} from "../socket";
import {Namespace, Server} from "socket.io";

export class Game extends Socket {

    private playerCount: number;

    constructor(io: Server) {
        super("/game", io);
    }

    onConnection(io: Server): void {
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