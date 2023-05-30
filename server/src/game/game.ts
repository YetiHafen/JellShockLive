import {Socket} from "../socket";
import {Namespace, Server, Socket as IOSocket} from "socket.io";
import {Maps} from "../maps";
import {User} from "../model/user";

class Game extends Socket {

    public gameId: string;
    public name: string;
    public password: string;
    public map: Maps;
    public playerCount: number = 0;
    public gameState: GameState = GameState.LOBBY;

    public users: User[];

    constructor(io: Server, iGame: IGame) {
        super("/game", io);
        this.gameId = iGame.gameId;
        this.name = iGame.name;
        this.password = iGame.password;
        this.map = iGame.map;
    }

    onConnection(socket: IOSocket): void {
        // GameState changes
    }

    registerEvents(io: Namespace): void {

    }

    toJSON(): any {
        return {
            gameId: this.gameId,
            name: this.name,
            password: this.password,
            map: this.map,
            playerCount: this.playerCount,
            gameState: this.gameState
        }
    }

}

interface IGame {
    gameId: GameId
    name: string
    password?: string;
    map: Maps;
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