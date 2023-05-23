import { Socket } from "../socket";
import { Namespace, Server } from "socket.io";

export class Lobby extends Socket {

    private playerCount: number;

    onConnection(io: Server): void {
        io.emit("");
    }

    registerEvents(io: Namespace): void {

    }

}
