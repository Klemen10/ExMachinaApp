package com.reevel.testreevel.rest_client;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Klemen on 17.8.2016.
 */

public class PicassoClient implements UrlConstants {

    private Context context;

    public PicassoClient(Context context){
        this.context = context;
    }

    public void loadImageToView(String imageUuid, ImageView imageView){
        String imageUrl = IMAGE_SERVICE_ENDPOINT+imageUuid;
        Picasso.with(context).load(imageUrl).into(imageView);
    }

}
