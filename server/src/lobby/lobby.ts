import { Socket } from "../socket";
import { Namespace, Server, Socket as IOSocket } from "socket.io";
import { Game } from "../game/game";
import {JSLServer} from "../server";

export class Lobby extends Socket {

    private gameList: Game[];

    constructor(io: Server) {
        super("", io);
        this.gameList = JSLServer.getInstance().getGames();
    }

    onConnection(socket: IOSocket): void {
        this.gameList = JSLServer.getInstance().getGames();
        // TODO: Fix max call stack
        socket.emit("list", ...this.gameList);
    }

    registerEvents(io: Namespace): void {

    }



}
