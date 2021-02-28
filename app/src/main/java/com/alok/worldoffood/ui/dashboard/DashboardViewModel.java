package com.alok.worldoffood.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("For Viewpager 2 vertical camera videos full page media player video");
    }

    public LiveData<String> getText() {
        return mText;
    }
}