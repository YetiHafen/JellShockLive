import {Namespace, Server} from "socket.io";

export abstract class Socket {

    private readonly io: Server;

    constructor(private namespace: string, io: Server) {
        this.io = io;
    }

    public connect() {
        this.onConnection(this.io);
        this.registerEvents(this.io.of("/" + this.namespace === null ? "" : this.namespace));
    }

    abstract onConnection(io: Server): void;

    abstract registerEvents(io: Namespace): void;
}