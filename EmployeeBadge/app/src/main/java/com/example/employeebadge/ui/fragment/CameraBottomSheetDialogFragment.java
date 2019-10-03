package com.example.employeebadge.ui.fragment;

/**
 * @author Created by duydt on 10/2/2019.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.employeebadge.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private OnPaperSizeSelectListener mListener;

    public static CameraBottomSheetDialogFragment newInstance(OnPaperSizeSelectListener listener){
        CameraBottomSheetDialogFragment fragment = new CameraBottomSheetDialogFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(OnPaperSizeSelectListener mListener) {
        this.mListener = mListener;
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN){
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btnTakePicture, R.id.btnSelectPicture, R.id.btnCancel}) protected void onSelectPaperSize(View view){
        if(view.getId() == R.id.btnTakePicture){
            mListener.onTakePicture();
        }
        if (view.getId() == R.id.btnSelectPicture) {
            mListener.onSelectedPicture();
        }
        dismiss();
    }

    public interface OnPaperSizeSelectListener{
        void onSelectedPicture();
        void onTakePicture();
    }

}
