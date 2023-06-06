import {Namespace, Server, Socket as IOSocket} from "socket.io";

export abstract class Socket {

    private readonly io: Server;

    protected constructor(private namespace: string, io: Server) {
        this.io = io;
    }

    public connect(): void {
        let nameSpace: Namespace = this.io.of(this.namespace === null ? "" : this.namespace);
        nameSpace.on("connection", (socket): void => {
            this.onConnection(socket);

            this.registerEvents(socket);
        });
    }

    abstract onConnection(socket: IOSocket): void;

    abstract registerEvents(socket: IOSocket): void;
}