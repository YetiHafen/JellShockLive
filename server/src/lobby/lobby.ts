import { Socket } from "../socket";
import { Namespace, Server, Socket as IOSocket } from "socket.io";
import { Game } from "../game/game";
import { JSLServer } from "../server";

export class Lobby extends Socket {

    constructor(io: Server) {
        super("", io);
    }

    onConnection(socket: IOSocket): void {
        let games: Game[] = Array.from(JSLServer.getInstance().getGames().values());
        let gameJSON = games.map(game=> game.toJSON());
        socket.emit("list", ...gameJSON);
    }

    registerEvents(io: Namespace): void {
        io.on("reload", (socket) => {
            socket.emit("list", Array.from(JSLServer.getInstance().getGames().values()));
        });

        io.on("join", (socket) => {
            console.log(socket);
        });
    }
}
