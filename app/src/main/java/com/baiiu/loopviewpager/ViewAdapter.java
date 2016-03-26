package com.baiiu.loopviewpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.baiiu.loopviewpager.base.BasePagerAdapter;

import java.util.List;

/**
 * author: baiiu
 * date: on 16/3/25 15:00
 * description:
 */
public class ViewAdapter extends BasePagerAdapter<Integer> {

    public ViewAdapter(Context context, List<Integer> list) {
        super(context, list);
    }

    @Override
    public View onCreateView(int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(list.get(position));
        return imageView;
//        View view = LayoutInflater.from(mContext).inflate(R.layout.vp_item, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setImageResource(list.get(position));
//        return view;
    }

}
