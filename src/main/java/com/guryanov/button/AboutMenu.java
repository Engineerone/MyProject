package com.guryanov.button;

import static com.guryanov.ui.AppFrame.userMessage;
import static com.guryanov.ui.AppFrame.version;

public class AboutMenu {

    public static void about() {
        userMessage.about("Anton Guryanov 'Engineer.one'\nguryanow@gmail.com\nver."+version);
    }
}
