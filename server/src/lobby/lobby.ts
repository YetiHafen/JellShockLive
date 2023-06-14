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
            const {name, password, map, maxPlayers, admin} = args[0];
            const user: User = await User.findUser(admin, socket.id);
            if (!user) {
                socket.emit("err");
                return;
            }
            await JSLServer.getInstance().createGame(name, password, map, maxPlayers, user);
        });

        socket.on("join", async (arg0, args1, callback): Promise<void> => {
            const result: string = await JSLServer.getInstance().checkJoinGame(arg0, args1);

            callback({
                status: result
            });
        });
    }
}
