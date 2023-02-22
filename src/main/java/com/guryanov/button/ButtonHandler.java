package com.guryanov.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface ButtonHandler extends ActionListener {
    void actionPerformed(ActionEvent e);

}
