package com.example.employeeattendance.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.AttendanceViewModel;

import static android.content.ContentValues.TAG;

public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        this.setEnterTransition(new Fade());
        this.setExitTransition(new Fade());
        final CardView card1=view.findViewById(R.id.cardView);
        final TextView tv=view.findViewById(R.id.textView);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment simpleFragmentB = RegisterFragment.newInstance(null,null);
                simpleFragmentB.setEnterTransition(new Fade());
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.content,simpleFragmentB,"RegisterFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(card1, ViewCompat.getTransitionName(card1));
                ft.addSharedElement(tv,ViewCompat.getTransitionName(tv));


                ft.commit();
            }
        });


        final CardView card2=view.findViewById(R.id.cardView2);
        final TextView tv2=view.findViewById(R.id.textView2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceFragment simpleFragmentB = AttendanceFragment.newInstance(null,null);
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.content,simpleFragmentB,"AttendanceFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(card2, ViewCompat.getTransitionName(card2));
                ft.addSharedElement(tv2,ViewCompat.getTransitionName(tv2));


                ft.commit();
            }
        });


        final CardView card3=view.findViewById(R.id.cardView3);
        final TextView tv3=view.findViewById(R.id.textView3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyFragment simpleFragmentB = ModifyFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Fade());
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.content,simpleFragmentB,"ModifyFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(card3, ViewCompat.getTransitionName(card3));
                ft.addSharedElement(tv3,ViewCompat.getTransitionName(tv3));


                ft.commit();
            }
        });


        final CardView card4=view.findViewById(R.id.cardView4);
        final TextView tv4=view.findViewById(R.id.textView4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendancelogFragment simpleFragmentB = AttendancelogFragment.newInstance(null,null);
                simpleFragmentB.setExitTransition(new Fade());
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.content,simpleFragmentB,"ModifyFragment")
                        .addToBackStack(TAG)
                        .addSharedElement(card4, ViewCompat.getTransitionName(card4));
                ft.addSharedElement(tv4,ViewCompat.getTransitionName(tv4));


                ft.commit();
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
