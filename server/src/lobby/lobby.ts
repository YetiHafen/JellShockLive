import { Socket } from "../socket";
import { Server, Socket as IOSocket } from "socket.io";
import { Game } from "../game/game";
import { JSLServer } from "../server";
import {User} from "../model/user";

export class Lobby extends Socket {

    constructor(io: Server) {
        super("", io);
    }

    onConnection(socket: IOSocket): void {
        let games: Game[] = Array.from(JSLServer.getInstance().getGames().values());
        let gameJSON: any = games.map(game=> game.toJSON());
        socket.emit("list", ...gameJSON);
    }

    async registerEvents(socket: IOSocket): Promise<void> {
        socket.on("reload", (): void => {
            let games: Game[] = Array.from(JSLServer.getInstance().getGames().values());
            let gameJSON: any = games.map(game=> game.toJSON());
            socket.emit("list", ...gameJSON);
        });

        socket.on("create", async (...args): Promise<void> => {
            const {name, password, map, maxPlayers} = args[0];
            const user = await User.findUser(args[1]);
            if (!user) {
                socket.emit("err");
                return;
            }
            await JSLServer.getInstance().createGame(name, password, map, maxPlayers, user);
        });

        socket.on("join", (...args): void => {
            JSLServer.getInstance().joinGame(args[0], args[1]);
            // Name, Password
        });
    }
}
