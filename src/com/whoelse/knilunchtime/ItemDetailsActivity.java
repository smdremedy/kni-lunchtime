package com.whoelse.knilunchtime;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.whoelse.knilunchtime.model.Item;

import java.io.IOException;
import java.io.InputStream;

public class ItemDetailsActivity extends Activity {
    private ImageView mDetailsImageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mDetailsImageView = (ImageView) findViewById(R.id.details_image_iv);

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra(Constants.ITEM_BUNDLE_KEY);

        InputStream bitmap = null;
        try {
            bitmap=getAssets().open(item.image);
            Bitmap bit= BitmapFactory.decodeStream(bitmap);
            mDetailsImageView.setImageBitmap(bit);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bitmap!=null) {
                try {
                    bitmap.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}