package com.example.employeeattendance.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.employeeattendance.Activity.MainActivity;
import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.RegisterViewModel;
import com.example.employeeattendance.ViewModelFactory.RegisterViewModelFactory;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class RegisterFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RegisterViewModel mRegisterViewModel;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState){

        final TextView dob=getView().findViewById(R.id.edob);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.hideKeyboardFrom(getActivity(),getView());
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        dob.setText(RegisterViewModel.getDateString(year,monthOfYear,dayOfMonth));
                                    }
                                }, 1990, 0, 1);
                        datePickerDialog.show();
            }
        });


        Button button=view.findViewById(R.id.edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                synchronized (RegisterFragment.this) {

                    final LottieAnimationView animationView = view.findViewById(R.id.animation_view);
                    animationView.playAnimation();
                    animationView.enableMergePathsForKitKatAndAbove(true);
                    animationView.setVisibility(View.VISIBLE);


                    TextInputEditText textInputEditText1 = getView().findViewById(R.id.ecode);
                    TextInputEditText textInputEditText2 = getView().findViewById(R.id.ename);
                    TextView textInputEditText3 = getView().findViewById(R.id.edob);
                    TextInputEditText textInputEditText4 = getView().findViewById(R.id.epass);
                    TextInputEditText textInputEditText5 = getView().findViewById(R.id.ephno);
                    TextInputEditText textInputEditText6 = getView().findViewById(R.id.eemail);

                    String ecode = textInputEditText1.getText().toString();
                    String ename = textInputEditText2.getText().toString();
                    String edob = textInputEditText3.getText().toString();
                    String epass = textInputEditText4.getText().toString();
                    String ephno = textInputEditText5.getText().toString();
                    String eemail = textInputEditText6.getText().toString();


                    if (RegisterViewModel.textFieldValidation(ecode, ename, edob, epass, ephno, eemail)) {
                        RegisterViewModelFactory factory = new RegisterViewModelFactory(getActivity().getApplication(), RegisterFragment.this);

                        mRegisterViewModel = new ViewModelProvider(getActivity(), factory).get(RegisterViewModel.class);

                        mRegisterViewModel.register(ecode, ename, edob, epass, ephno, eemail);
                        mRegisterViewModel.getChanged().observe(getActivity(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean isSuccessful) {
                                if (isSuccessful != null && !isSuccessful) {
                                    animationView.pauseAnimation();
                                    animationView.setVisibility(View.GONE);
                                    Snackbar.make(view, "Already Registered", Snackbar.LENGTH_SHORT).show();
                                } else if (isSuccessful != null && isSuccessful) {
                                    animationView.pauseAnimation();
                                    animationView.setVisibility(View.GONE);
                                    Snackbar.make(view, "Registered", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {

                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        new MaterialAlertDialogBuilder(getActivity())
                                .setTitle("Error")
                                .setMessage("Incorrect values")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
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

    public void onFailure()
    {
        Snackbar.make(getView(),"Unexpected Error", Snackbar.LENGTH_SHORT).show();
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
