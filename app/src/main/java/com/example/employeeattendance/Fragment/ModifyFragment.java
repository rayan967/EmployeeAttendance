package com.example.employeeattendance.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
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
import com.example.employeeattendance.ViewModel.ModifyViewModel;
import com.example.employeeattendance.ViewModelFactory.ModifyViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

public class ModifyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    View _rootView;

    ModifyViewModel mModifyViewModel;

    private OnFragmentInteractionListener mListener;

    public ModifyFragment() {
    }

    public static ModifyFragment newInstance(String param1, String param2) {
        ModifyFragment fragment = new ModifyFragment();
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
        if(_rootView==null)
        _rootView = inflater.inflate(R.layout.fragment_modify, container, false);
        return _rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        Button button=view.findViewById(R.id.edit);
        final TextView tv3=view.findViewById(R.id.textView);
        final View card3=view.findViewById(R.id.view3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                MainActivity.hideKeyboardFrom(getContext(),getView());
                TextView ecodeview=view.findViewById(R.id.ecode);
                TextView epassview=view.findViewById(R.id.epass);

                final String ecode=ecodeview.getText().toString();
                final String epass=epassview.getText().toString();

                if(!epass.equals("")&&!ecode.equals("")) {

                    final LottieAnimationView animationView = view.findViewById(R.id.animation_view);
                    animationView.playAnimation();
                    animationView.enableMergePathsForKitKatAndAbove(true);
                    animationView.setVisibility(View.VISIBLE);

                    ModifyViewModelFactory factory = new ModifyViewModelFactory(getActivity().getApplication(), ecode, epass);

                    mModifyViewModel = new ViewModelProvider(getActivity(), factory).get(ModifyViewModel.class);


                    mModifyViewModel.LogIn(ecode, epass);
                    mModifyViewModel.getValidate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {

                        @Override
                        public void onChanged(@Nullable final Boolean word) {
                            if (word == null) {
                            } else if (word) {

                                mModifyViewModel.getDetails().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
                                    @Override
                                    public void onChanged(Map<String, Object> stringObjectMap) {
                                        if (stringObjectMap.size() > 0) {

                                            animationView.pauseAnimation();
                                            animationView.setVisibility(View.GONE);
                                            ModifyFragment2 simpleFragment = ModifyFragment2.newInstance(stringObjectMap.get("Employee-Code").toString(),
                                                    stringObjectMap.get("Employee-Name").toString(), stringObjectMap.get("Employee-DOB").toString(),
                                                    stringObjectMap.get("Employee-Password").toString(), stringObjectMap.get("Employee-PhoneNo").toString(),
                                                    stringObjectMap.get("Employee-Email").toString());

                                            FragmentTransaction ft = getFragmentManager().beginTransaction()
                                                    .replace(R.id.content, simpleFragment)
                                                    .addToBackStack("ModifyFragment2")
                                                    .addSharedElement(card3, ViewCompat.getTransitionName(card3))
                                                    .addSharedElement(tv3, ViewCompat.getTransitionName(tv3));
                                            ft.commit();
                                        }
                                    }
                                });

                            } else {
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

        final ImageView back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
                animationView.setVisibility(View.GONE);
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

    @Override
    public void onDestroyView() {
        if (_rootView.getParent() != null) {
            ((ViewGroup)_rootView.getParent()).removeView(_rootView);
        }
        super.onDestroyView();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
