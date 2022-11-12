package com.vorax.client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import com.vorax.layer.SwingIOLayer;
import com.vorax.server.Server;

public final class SwingClient extends Client {
    private JFrame frame;
    private JTextPane output;
    private JTextField input;

    public SwingClient(Server server) {
        this.frame = new JFrame();
        this.output = new JTextPane();
        this.input = new JTextField();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 500);
        frame.setTitle("Vorax");
        frame.setLocationRelativeTo(null);

        output.setEditable(false);

        input.addActionListener(e -> {
            String text = read();

            if (text == null || text.isEmpty() || text.isBlank()) {
                clear(true, false);
                return;
            }

            int error = parse(text);

            if (error == 2) {
                stop();
            } else if (error != 0) {
                if (error != 1) {
                    clear(true, false);
                }
                
                println(decode(error));
            }
        });

        frame.add(new JScrollPane(output), BorderLayout.CENTER);
        frame.add(input, BorderLayout.SOUTH);

        setIOLayer(new SwingIOLayer(input, output));
        setServer(server);
    }

    public void start() {
        frame.setVisible(true);

        server.getLoader().load(server.getParser(), this);
    }

    public void stop() {
        frame.dispose();
        try {
            io.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
