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
        let gameJSON: any = games.map(game=> game.toJSON());
        socket.emit("list", ...gameJSON);
    }

    registerEvents(io: IOSocket): void {
        io.on("reload", (): void => {
            let games: Game[] = Array.from(JSLServer.getInstance().getGames().values());
            let gameJSON: any = games.map(game=> game.toJSON());
            io.emit("list", ...gameJSON);
        });

        io.on("create", (...args): void => {
            const { name, password, map, maxPlayers } = args[0];
            JSLServer.getInstance().createGame(name, password, map, maxPlayers);
        });

        io.on("join", (...args): void => {
            JSLServer.getInstance().joinGame(args[0], args[1]);
        });
    }
}
