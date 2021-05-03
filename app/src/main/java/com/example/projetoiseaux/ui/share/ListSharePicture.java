package com.example.projetoiseaux.ui.share;


import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.GridPictures.Picture;

import java.util.ArrayList;

/**
 * Modify by Fred on 09/02/2021.
 */
public class ListSharePicture extends ArrayList<Picture>{
    public static final long BUTTON_ADD = -1;

    private int nbPicture = 0;

    // TODO: 2021/4/25 Add a "plus" image

    public ListSharePicture(){
        add(0, new Picture("Null", BUTTON_ADD));
    }

    @Override
    public boolean add(Picture picture) {
        if(nbPicture < 9) {
            this.nbPicture++;
            super.add(this.size() - 1, picture);

            if(nbPicture == 9){
                remove(size() - 1);
            }
            return true;
        }

        return false;
    }
}

