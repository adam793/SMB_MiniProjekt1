package pl.pjatk.smb1.enums;


import android.graphics.Color;

import java.util.Objects;


/**
 * Created by user on 2016-11-05.
 */

public enum ColorTxt {
    BLACK(Color.BLACK,"Czarny",0),
    RED(Color.RED,"Czerwony",1),
    BLUE(Color.BLUE,"Niebieski",2);

    ColorTxt(int color, String displayName, int i) {
        this.color = color;
        this.displayName= displayName;
        this.position = i;
    }
    private int color;
    private String displayName;
    private int position;


    public int getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }


 public static ColorTxt getValue(String dispalName) {
        for(ColorTxt e: ColorTxt.values()) {
            if(Objects.equals(e.getDisplayName(), dispalName)) {
                return e;
            }
        }
        return BLACK;// not found
    }

    public int getPosition() {
        return position;
    }
}
