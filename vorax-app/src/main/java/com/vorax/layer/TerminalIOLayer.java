package com.vorax.layer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.vorax.layer.framework.IOLayer;

public final class TerminalIOLayer implements IOLayer {
    private Scanner input;
    private OutputStream output;

    public TerminalIOLayer(OutputStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public void write(String string) {
        try {
            output.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String read() {
        try {
            return input.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        output.close();
        input.close();
    }

    @Override
    public void clear(boolean input, boolean output) {
                
    }
}
