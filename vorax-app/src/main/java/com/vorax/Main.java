package com.vorax;

import com.vorax.client.SwingClient;
import com.vorax.layer.FileSystemLayer;
import com.vorax.server.Server;

public class Main {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        
        if (args.length != 0) {
            path = args[0];
            return;
        }

        new SwingClient(new Server(new FileSystemLayer(path))).start();
    }
}
