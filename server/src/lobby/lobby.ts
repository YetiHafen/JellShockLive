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
        console.log(gameJSON);
    }

    registerEvents(io: IOSocket): void {
        io.on("reload", (): void => {
            let games: Game[] = Array.from(JSLServer.getInstance().getGames().values());
            let gameJSON = games.map(game=> game.toJSON());
            io.emit("list", ...gameJSON);
        });

        io.on("create", (...args): void => {
            const { name, password, map, maxPlayers } = args[0];
            JSLServer.getInstance().createGame(name, password, map, maxPlayers);
        });

        io.on("join", (socket): void => {
            console.log(socket);
        });
    }
}
