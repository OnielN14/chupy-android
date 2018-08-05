package com.chopchop.chupy.feature.read.utilities;

import android.view.View;

public interface OnItemClickListener{
    void onItemClickListener(View v, int position);

    void onItemClickListener(int position);

    void onItemLongClickListener(View v, int position);
}
