package com.srost_studio.assignment.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoUtil {

    public static void setImage(Context context, String imageHref, int stub,
                                 ImageView intoImageView) {
        if (imageHref != null && !imageHref.isEmpty()) {
            Picasso.with(context)
                    .load(imageHref)
                    .placeholder(stub)
                    .error(stub)
                    .into(intoImageView);
        } else {
            intoImageView.setImageResource(stub);
        }
    }
}
