package com.pamsillah.yanai.models;

import com.pamsillah.yanai.R;

public enum ModelOnboard {
    RED(R.string.action_go_to_signup, R.layout.onboard_screen1),
    BLUE(R.string.download, R.layout.onboard_screen2),
    GREEN(R.string.action_sign_in, R.layout.onboard_screen3);

    private int mTitleResId;
    private int mLayoutResId;

    ModelOnboard(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
