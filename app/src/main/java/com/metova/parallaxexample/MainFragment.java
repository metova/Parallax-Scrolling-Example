package com.metova.parallaxexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainFragment extends Fragment {

    private LinearLayoutManager mLayoutManager;
    private CardAdapter mCardAdapter;

    public MainFragment() {
    }

    public static MainFragment newInstance() {

        return new MainFragment();
    }

    @InjectView(R.id.house_header_layout)
    FrameLayout mHouseHeaderLayout;

    @InjectView(R.id.house_header_bar_content)
    RelativeLayout mHouseHeaderBarContent;

    @InjectView(R.id.house_icon)
    ImageView mHouseIcon;

    @InjectView(R.id.bar_layout)
    RelativeLayout mBarLayout;

    @InjectView(R.id.parallax_list)
    RecyclerView mParallaxList;

    private List<Object> mObjects;
    private int mParallaxScroll = 0;

    // Parallax % amounts
    private float mBarLayoutFactor = 1.0f;
    private float mHouseHeaderLayoutFactor = 0.4f;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        initViews();
        return rootView;
    }

    private void initViews() {

        initRecycler();
        setupParallaxScrolling();
    }

    private void initRecycler() {

        mObjects = Arrays.asList(new Object(), new Object(), new Object(), new Object(), new Object(), new Object());

        // header_bar + header_size + offset (offset is negative)
        int spacerSize = getResources().getDimensionPixelSize(R.dimen.header_bar) +
                getResources().getDimensionPixelSize(R.dimen.house_header_size) +
                getResources().getDimensionPixelSize(R.dimen.header_bar_offset);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCardAdapter = new CardAdapter(getActivity(), spacerSize, mObjects);

        mParallaxList.setLayoutManager(mLayoutManager);
        mParallaxList.setAdapter(mCardAdapter);
    }

    private void setupParallaxScrolling() {

        mParallaxList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                // When scrolling, update the header and bar translation
                mParallaxScroll -= dy;
                mParallaxScroll = (mParallaxScroll > 0 ? 0 : mParallaxScroll);

                mBarLayout.setTranslationY(mParallaxScroll * mBarLayoutFactor);
                mHouseHeaderLayout.setTranslationY(mParallaxScroll * mHouseHeaderLayoutFactor);
            }
        });
    }
}
