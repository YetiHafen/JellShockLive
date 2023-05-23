import * as http from "http";
import { Server } from "socket.io";
import { User } from "./model/user";
import {Game} from "./game/game";

export class JSLServer {

    public static readonly PORT:number = 3000;
    private readonly server: http.Server;
    private io: Server;

    private games: Array<Game>;

    constructor() {
        this.server = http.createServer();
        this.io = new Server(this.server);
        this.listen();
    }

    private listen(): void {
        this.server.listen(JSLServer.PORT, () => {
            console.log(`JDL-Server started on port ${JSLServer.PORT}`);
        });

        this.io.on('connection', (socket: any) => {
            console.log("Client connected");

            socket.on('user', (user: User) => {
                console.log(`+ ${user.name} [uuid=${user.uuid}]`);
                this.io.emit('user', user);
            });

            socket.on('disconnecting', (reason: any) => {

            });

            socket.on('disconnect', (reason: any) => {
                console.log('Client disconnected Reason: ' + reason);
            });
        });
    }
}