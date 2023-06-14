import {Socket} from "../socket";
import {Server, Socket as IOSocket} from "socket.io";
import {Maps} from "../maps";
import {User} from "../model/user";

class Game extends Socket {

    public static readonly mapMiddle: number = 1500;
    public static readonly teamArea: number = 1200;

    public gameId: GameId;
    public name: string;
    public password: string;
    public map: Maps;
    public playerCount: number = 0;
    public maxPlayers: number = 8;
    public gameState: GameState = GameState.LOBBY;

    public admin: User;
    public users: Map<User, Team>;
    public cyanUsers: number = 0;
    public redUsers: number = 0;

    constructor(io: Server, iGame: IGame) {
        super(`/${iGame.gameId}`, io);
        this.gameId = iGame.gameId;
        this.name = iGame.name;
        this.password = iGame.password;
        this.maxPlayers = iGame.maxPlayers;
        this.map = iGame.map;
        this.admin = iGame.admin;
        this.users = new Map<User, Team>();
    }

    async onConnection(socket: IOSocket): Promise<void> {
        socket.on("join", async (arg): Promise<void> => {
            const user: User = await User.findUser(arg, socket.id);
            if (user) {
                let team: Team = this.randomTeam();
                this.users.set(user, team);
                console.log(`{${user.name}} joined ${this.gameId} in team ${team}`);
                this.playerCount++;
                let spawnPosition: number = this.evaluateSpawnPosition(team);
                console.log(spawnPosition);
                socket.emit("joindata", team, spawnPosition);
                socket.broadcast.emit("join", user.name, team, spawnPosition);
            } else {
                socket.emit("err");
            }
        });

        socket.emit("init", this.users);
    }

    registerEvents(socket: IOSocket): void {
        socket.on("disconnect", (): void => {
            let user: User = [...this.users.keys()].find(user => user.sessionId === socket.id);
            let team: Team = this.users.get(user);
            if (team === Team.CYAN) this.cyanUsers--;
            else this.redUsers--;
            console.log(`{${user.name}} left ${this.gameId}`);
            socket.emit("left", user.name);
            this.users.delete(user);
            this.playerCount--;
        });

        socket.on("movement", (arg) => {
            /*const user: User = User.*/
        });
    }

    private randomTeam(): Team {
        let memberPerTeam: number = this.maxPlayers / 2;
        if (!(this.users.size == 0)) {
            if (memberPerTeam == this.cyanUsers) {
                this.redUsers++;
                return Team.RED;
            } else if (memberPerTeam == this.redUsers) {
                this.cyanUsers++;
                return Team.CYAN;
            }
        }
        if (Math.random() < 0.5) {
            this.cyanUsers++;
            return Team.CYAN;
        } else {
            this.redUsers++;
            return Team.RED;
        }
    }

    private evaluateSpawnPosition(team: Team): number {
        let teamPositions: number = this.maxPlayers / 2;
        let positionDistance: number = Game.teamArea / teamPositions;
        console.log(`Pos: ${teamPositions} with distance ${positionDistance}`)
        if (team === Team.CYAN) {
            console.log(`Users: ${this.cyanUsers} and ${positionDistance}`);
            return 100 + (this.cyanUsers * positionDistance);
        } else {
            return Game.mapMiddle + 100 + (this.redUsers * positionDistance);
        }
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
    CYAN,
    RED
}

type GameId = string;

export { Game, IGame, GameState, Team, GameId };