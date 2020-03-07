package com.example.employeeattendance.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.employeeattendance.Adapter.AttendanceLogAdapter;
import com.example.employeeattendance.Adapter.AttendanceLogItem;
import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.AttendanceViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AttendancelogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<AttendanceLogItem> NPList=new ArrayList<>();
    AttendanceLogAdapter mAdapter;
    private AttendanceViewModel aViewModel;


    public AttendancelogFragment() {

    }


    public static AttendancelogFragment newInstance(String param1, String param2) {
        AttendancelogFragment fragment = new AttendancelogFragment();
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
                TransitionManager.beginDelayedTransition(cl, new Slide(Gravity.BOTTOM));
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
        return inflater.inflate(R.layout.fragment_attendancelog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mAdapter = new AttendanceLogAdapter(NPList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        final ProgressBar pb = view.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        aViewModel = new ViewModelProvider(getActivity()).get(AttendanceViewModel.class);

        aViewModel.getAttendance();
        aViewModel.getAllRows().observe(getViewLifecycleOwner(), new Observer<List<AttendanceLogItem>>() {
            @Override
            public void onChanged(@Nullable List<AttendanceLogItem> words) {
                if (words.size() > 0) {
                    pb.setVisibility(View.INVISIBLE);
                    mAdapter.setRows(words, getActivity());
                    NPList=words;



                    ImageView export=getView().findViewById(R.id.export);
                    export.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            DialogInterface.OnClickListener spreadsheet=new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                AttendanceViewModel.writeCSV(NPList);
                                    Snackbar.make(getView(),"Saved to Downloads",Snackbar.LENGTH_SHORT).show();
                                }
                            };



                            DialogInterface.OnClickListener text=new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AttendanceViewModel.writeTXT(NPList);
                                    Snackbar.make(getView(),"Saved to Downloads",Snackbar.LENGTH_SHORT).show();
                                }
                            };


                            new MaterialAlertDialogBuilder(getActivity())
                                    .setTitle("Export")
                                    .setMessage("Which format do you want to export to?")
                                    .setNeutralButton("Cancel",null)
                                    .setNegativeButton("Text File",text)
                                    .setPositiveButton("Spreadsheet", spreadsheet)
                                    .show();


                        }
                    });

                } }
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
