package com.hins.viewpagertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Story> mStories = new ArrayList<>();

    private String[] imagesUrl = {
            "https://pic4.zhimg.com/v2-9aa865e2ff2711f534c96efa42b18d6f.jpg",
            "https://pic3.zhimg.com/v2-614107a2272793ca9eff2408d56c4d72.jpg",
            "https://pic3.zhimg.com/v2-0b0550c19361a0702de6735f1652a3ee.jpg",
            "https://pic1.zhimg.com/v2-4035a071bada2917817e06a308ae88ac.jpg",
            "https://pic4.zhimg.com/v2-b4a7d5cfedc3323db8c42066dfaaf64b.jpg"
    };

    private String[] titles = {"Title1", "Title2", "Title3", "Title4", "Title5"};

    private List<ImageView> mImages = new ArrayList<>();

    private ViewPager mHeaderViewPager;
    private LinearLayout mHeaderIndicator;
    private TextView mHeaderTitle;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeaderViewPager = (ViewPager) findViewById(R.id.header_view_pager);
        mHeaderIndicator = (LinearLayout) findViewById(R.id.header_indicator);
        mHeaderTitle = (TextView) findViewById(R.id.header_title);

        for (int i = 0; i < imagesUrl.length; i++) {
            Story story = new Story(titles[i], imagesUrl[i]);
            mStories.add(story);
        }

        int index = 0;
        mHeaderTitle.setText(titles[index]);
        for (Story s : mStories) {

            ImageView imageView = new ImageView(this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(this).load(s.getUrl()).into(imageView);

            mImages.add(imageView);



            ImageView pointImage = new ImageView(this);
            pointImage.setImageResource(R.drawable.shape_point_selector);

            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);

            if (index > 0) {
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.point_margin);
                pointImage.setSelected(false);
            } else {
                pointImage.setSelected(true);
            }

            pointImage.setLayoutParams(params);

            mHeaderIndicator.addView(pointImage);

            index++;
            
        }

        mHeaderViewPager.setAdapter(new MyAdapter());

        mHeaderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int lastPosition;
            @Override
            public void onPageSelected(int position) {

                // 页面被选中

                // 修改position
                position = position % mImages.size();

                mHeaderTitle.setText(titles[position]);

                // 设置当前页面选中
                mHeaderIndicator.getChildAt(position).setSelected(true);
                // 设置前一页不选中
                mHeaderIndicator.getChildAt(lastPosition).setSelected(false);

                // 替换位置
                lastPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPosition = mHeaderViewPager.getCurrentItem();

                if(currentPosition == mHeaderViewPager.getAdapter().getCount() - 1){
                    // 最后一页
                    mHeaderViewPager.setCurrentItem(0);
                }else{
                    mHeaderViewPager.setCurrentItem(currentPosition + 1);
                }

                // 一直给自己发消息
                mHandler.postDelayed(this,3000);
            }
        },3000);


    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mImages.size();
            // 将图片控件添加到容器
            container.addView(mImages.get(position));

            // 返回
            return mImages.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
