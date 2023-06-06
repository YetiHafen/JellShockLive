import {Socket} from "../socket";
import {Server, Socket as IOSocket} from "socket.io";
import {Maps} from "../maps";
import {User} from "../model/user";

class Game extends Socket {

    public gameId: GameId;
    public name: string;
    public password: string;
    public map: Maps;
    public playerCount: number = 0;
    public maxPlayers: number = 8;
    public gameState: GameState = GameState.LOBBY;

    public admin: User;
    public users: User[] = [];

    constructor(io: Server, iGame: IGame) {
        super(`/${iGame.gameId}`, io);
        this.gameId = iGame.gameId;
        this.name = iGame.name;
        this.password = iGame.password;
        this.maxPlayers = iGame.maxPlayers;
        this.map = iGame.map;
        this.admin = iGame.admin;
    }

    async onConnection(socket: IOSocket): Promise<void> {
        socket.on("join", async (arg): Promise<void> => {
            const user: User = await User.findUser(arg, socket.id);
            if (user) {
                this.users.push(user);
                this.playerCount++;
            } else {
                socket.emit("err");
            }
        });
    }

    registerEvents(socket: IOSocket): void {
        socket.on("disconnect", (): void => {
            let user: User = this.users.find(user => user.sessionId === socket.id);
            console.log(`Client {${user.name}} disconnected`);
            this.users = this.users.filter(user => user.sessionId !== socket.id);
            this.playerCount--;
        });
    }

    toJSON(): any {
        return {
            gameId: this.gameId,
            name: this.name,
            password: this.password,
            map: this.map,
            playerCount: this.playerCount,
            maxPlayers: this.maxPlayers,
            gameState: this.gameState
        }
    }

}

interface IGame {
    gameId: GameId,
    name: string,
    password?: string,
    maxPlayers?: number,
    map: Maps,
    admin: User;
}

enum GameState {
    LOBBY,
    START,
    INGAME,
    ENDING
}

enum Team {
    BLUE,
    RED
}

type GameId = string;

export { Game, IGame, GameState, Team, GameId };