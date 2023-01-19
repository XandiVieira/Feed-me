package com.relyon.feedme.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.relyon.feedme.R;

public class OnBoardingAdapter extends PagerAdapter {

    private Context context;

    int sliderAllImages[] = {
            R.drawable.onboarding1,
            R.drawable.onboarding2,
            R.drawable.onboarding3,
    };
    int sliderAllTitle[] = {
            R.string.onboarding_title_1,
            R.string.onboarding_title_2,
            R.string.onboarding_title_3,
    };
    int sliderAllDesc[] = {
            R.string.onboarding_description_1,
            R.string.onboarding_description_2,
            R.string.onboarding_description_3,
    };

    public OnBoardingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderAllTitle.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_onboarding, container, false);

        ShapeableImageView sliderImage = view.findViewById(R.id.sliderImage);
        TextView sliderTitle = view.findViewById(R.id.slider_title);
        TextView sliderDesc = view.findViewById(R.id.slider_description);
        sliderImage.setImageResource(sliderAllImages[position]);
        sliderTitle.setText(this.sliderAllTitle[position]);
        sliderDesc.setText(this.sliderAllDesc[position]);

        if (position == 0) {
            sliderImage.setShapeAppearanceModel(sliderImage.getShapeAppearanceModel().toBuilder().setBottomLeftCorner(CornerFamily.ROUNDED, 288).build());
        } else if (position == 2) {
            sliderImage.setShapeAppearanceModel(sliderImage.getShapeAppearanceModel().toBuilder().setBottomRightCorner(CornerFamily.ROUNDED, 288).build());
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}