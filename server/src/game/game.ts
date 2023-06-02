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

    public users: User[];

    constructor(io: Server, iGame: IGame) {
        super("/game/" + iGame.gameId, io);
        this.gameId = iGame.gameId;
        this.name = iGame.name;
        this.password = iGame.password;
        this.maxPlayers = iGame.maxPlayers;
        this.map = iGame.map;
    }

    onConnection(socket: IOSocket): void {
        // GameState changes
    }

    registerEvents(io: IOSocket): void {

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
    gameId: GameId
    name: string
    password?: string;
    maxPlayers?: number
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