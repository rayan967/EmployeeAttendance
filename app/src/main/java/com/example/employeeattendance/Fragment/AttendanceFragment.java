package com.example.employeeattendance.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.employeeattendance.Activity.MainActivity;
import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.AttendanceViewModel;
import com.google.android.material.snackbar.Snackbar;


public class AttendanceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AttendanceViewModel mAttendanceViewModel;

    private OnFragmentInteractionListener mListener;

    public AttendanceFragment() {
    }


    public static AttendanceFragment newInstance(String param1, String param2) {
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);

        Transition sharedElementEnterTransition = (Transition) getSharedElementEnterTransition();

        sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                ConstraintLayout cl = (ConstraintLayout) getView().findViewById(R.id.cl);
                Slide slide = new Slide();
                TransitionManager.beginDelayedTransition(cl, new Fade());
                cl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        Button button=view.findViewById(R.id.checkin);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.hideKeyboardFrom(getContext(),getView());
                TextView ecodeview=view.findViewById(R.id.ecode);
                TextView epassview=view.findViewById(R.id.epass);

                final String ecode=ecodeview.getText().toString();
                final String epass=epassview.getText().toString();

                if(!epass.equals("")&&!ecode.equals("")){


                    final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
                    animationView.playAnimation();
                    animationView.enableMergePathsForKitKatAndAbove( true );
                    animationView.setVisibility(View.VISIBLE);
                AttendanceViewModel factory = new AttendanceViewModel(getActivity().getApplication());

                mAttendanceViewModel=new ViewModelProvider(getActivity()).get(AttendanceViewModel.class);


                mAttendanceViewModel.LogIn(ecode,epass);
                mAttendanceViewModel.getValidate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {

                    @Override
                    public void onChanged(@Nullable final Boolean word) {
                        if(word==null){

                        }
                        else if(word){

                            mAttendanceViewModel.recordAttendance(ecode,"CHECK IN");
                            mAttendanceViewModel.getChanged().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean changed) {
                                    if(changed)
                                    {
                                        animationView.pauseAnimation();
                                        animationView.setVisibility(View.GONE);
                                        Snackbar.make(view,"Attendance Recorded", Snackbar.LENGTH_SHORT).show();}
                                }
                            });
                        }
                        else {
                            animationView.pauseAnimation();
                            animationView.setVisibility(View.GONE);
                            Snackbar.make(view, "Invalid ID or Password", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
                    Snackbar.make(view, "Invalid ID or Password", Snackbar.LENGTH_SHORT).show();

            }

        });


        Button button2=view.findViewById(R.id.checkout);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                MainActivity.hideKeyboardFrom(getContext(),getView());
                TextView ecodeview=view.findViewById(R.id.ecode);
                TextView epassview=view.findViewById(R.id.epass);

                final String ecode=ecodeview.getText().toString();
                final String epass=epassview.getText().toString();

                if(!epass.equals("")&&!ecode.equals("")) {

                    final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
                    animationView.playAnimation();
                    animationView.enableMergePathsForKitKatAndAbove( true );
                    animationView.setVisibility(View.VISIBLE);

                    AttendanceViewModel factory = new AttendanceViewModel(getActivity().getApplication());

                mAttendanceViewModel=new ViewModelProvider(getActivity()).get(AttendanceViewModel.class);

                mAttendanceViewModel.LogIn(ecode,epass);
                mAttendanceViewModel.getValidate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {

                    @Override
                    public void onChanged(@Nullable final Boolean word) {
                        if(word==null){

                        }
                        else if(word){
                            mAttendanceViewModel.recordAttendance(ecode,"CHECK OUT");
                            mAttendanceViewModel.getChanged().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean changed) {
                                    if(changed){
                                        animationView.pauseAnimation();
                                        animationView.setVisibility(View.GONE);
                                        Snackbar.make(view,"Attendance Recorded", Snackbar.LENGTH_SHORT).show();}
                                }
                            });
                        }
                        else{
                            animationView.pauseAnimation();
                            animationView.setVisibility(View.GONE);
                            Snackbar.make(view,"Invalid ID or Password", Snackbar.LENGTH_SHORT).show();}
                    }
                });
            }
                else
                    Snackbar.make(view,"Invalid ID or Password", Snackbar.LENGTH_SHORT).show();
        }
        });



        final ImageView back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                back.setOnClickListener(null);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
