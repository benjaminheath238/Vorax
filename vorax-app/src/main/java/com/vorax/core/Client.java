package com.vorax.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import lombok.Getter;

public final class Client {
    @Getter
    private Server server;

    private JFrame frame;
    private JTextPane output;
    private JTextField input;

    public Client(Server server) {
        this.server = server;

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
            String text = input.getText();

            if (text == null || text.isEmpty() || text.isBlank()) {
                return;
            }

            int error = parse(text);

            if (error == 2) {
                stop();
            } else if (error != 0) {
                if (error != 1) {
                    input.setText(null);
                }
                
                println(decode(error));
            }
        });

        frame.add(new JScrollPane(output), BorderLayout.CENTER);
        frame.add(input, BorderLayout.SOUTH);
    }

    public int parse(String text) {
        return server.getParser().parse(text);
    }

    public String decode(int error) {
        return server.getParser().decode(error);
    }

    public void print(Object object) {
        output.setText(output.getText() + object);
    }

    public void printf(String format, Object... args) {
        print(String.format(format, args));
    }

    public void println(Object object) {
        print(object.toString());
        println();
    }

    public void println() {
        print("\n");
    }

    public void start() {
        frame.setVisible(true);

        server.getLoader().load(this);
    }

    public void stop() {
        frame.dispose();
    }
}
