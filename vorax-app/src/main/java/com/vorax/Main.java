package com.vorax;

import com.vorax.core.Client;
import com.vorax.core.FileSystem;
import com.vorax.core.Server;

public class Main {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");

        if (args.length != 0) {
            path = args[0];
            return;
        }

        new Client(new Server(new FileSystem(path))).start();
    }
}
