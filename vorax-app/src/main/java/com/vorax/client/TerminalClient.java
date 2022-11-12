package com.vorax.client;

import java.io.IOException;

import com.vorax.layer.TerminalIOLayer;
import com.vorax.server.Server;

public final class TerminalClient extends Client {
    private boolean running;

    public TerminalClient(Server server) {
        this.running = true;

        setIOLayer(new TerminalIOLayer(System.out, System.in));
        setServer(server);
    }

    @Override
    public void start() {
        while (running) {
            String text = read();

            if (text == null || text.isEmpty() || text.isBlank()) {
                return;
            }

            int error = parse(text);

            if (error == 2) {
                stop();
            } else if (error != 0) {
                println(decode(error));
            }
        }
    }

    @Override
    public void stop() {
        this.running = false;
        try {
            io.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
}
