package com.luomor.yiaroundad.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luomor.yiaroundad.R;

/**
 * Created by peterzhang on 27/08/2018.
 */

public class LocationView extends FrameLayout {
    private TextView mAddressText;

    public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LocationView(Context context) {
        this(context, null);
    }

    public LocationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_location, this);
        mAddressText = (TextView) view.findViewById(R.id.location_text);
    }

    public void setAddressText(String text) {
        mAddressText.setText(text);
    }
}
