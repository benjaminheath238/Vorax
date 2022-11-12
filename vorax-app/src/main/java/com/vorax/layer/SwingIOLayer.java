package com.vorax.layer;

import java.io.IOException;

import javax.swing.text.JTextComponent;

import com.vorax.layer.framework.IOLayer;

public final class SwingIOLayer implements IOLayer {
    private JTextComponent input;
    private JTextComponent output;

    public SwingIOLayer(JTextComponent input, JTextComponent output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void write(String string) {
        output.setText(output.getText() + string);
    }

    @Override
    public String read() {
        String text = input.getText();
        return text;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void clear(boolean i, boolean o) {
        if (i) {
            input.setText(null);
        }
        
        if (o) {
            output.setText(null);
        }
    }
}
