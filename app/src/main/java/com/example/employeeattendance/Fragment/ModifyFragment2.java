package com.example.employeeattendance.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.employeeattendance.Activity.MainActivity;
import com.example.employeeattendance.R;
import com.example.employeeattendance.ViewModel.ModifyViewModel;
import com.example.employeeattendance.ViewModel.RegisterViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ModifyFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    private String ecode;
    private String ename;
     String edob;
    private String epass;
    private String ephno;
    private String eemail;

    Map<String, Object> user;

    private OnFragmentInteractionListener mListener;

    public ModifyFragment2() {
    }

    public static ModifyFragment2 newInstance(String param1, String param2, String param3, String param4, String param5, String param6) {
        ModifyFragment2 fragment = new ModifyFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ecode = getArguments().getString(ARG_PARAM1);
            ename = getArguments().getString(ARG_PARAM2);
            edob = getArguments().getString(ARG_PARAM3);
            epass = getArguments().getString(ARG_PARAM4);
            ephno = getArguments().getString(ARG_PARAM5);
            eemail = getArguments().getString(ARG_PARAM6);
        }
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        final TextView dob=getView().findViewById(R.id.edob);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] dob_split=ModifyFragment2.this.edob.split("/");
                int mYear=Integer.parseInt(dob_split[2]);
                int mMonth=Integer.parseInt(dob_split[1])-1;
                int mDay=Integer.parseInt(dob_split[0]);

                MainActivity.hideKeyboardFrom(getActivity(),getView());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dob.setText(RegisterViewModel.getDateString(year,monthOfYear,dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });




        user = new HashMap<>();
        user.put("Employee-Code", ecode);
        user.put("Employee-Name", ename);
        user.put("Employee-DOB", edob);
        user.put("Employee-Password", epass);
        user.put("Employee-PhoneNo", eemail);
        user.put("Employee-Email", ephno);

        final TextInputEditText textInputEditText2=getView().findViewById(R.id.ename);
        final TextView textInputEditText3=getView().findViewById(R.id.edob);
        final TextInputEditText textInputEditText4=getView().findViewById(R.id.epass);
        final TextInputEditText textInputEditText5=getView().findViewById(R.id.ephno);
        final TextInputEditText textInputEditText6=getView().findViewById(R.id.eemail);

        textInputEditText2.setText(ename);
        textInputEditText3.setText(edob);
        textInputEditText4.setText(epass);
        textInputEditText5.setText(ephno);
        textInputEditText6.setText(eemail);

        final ModifyViewModel mViewModel= new ViewModelProvider(getActivity()).get(ModifyViewModel.class);


        Button b=view.findViewById(R.id.edit);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View.OnClickListener listener=this;

                ename=textInputEditText2.getText().toString();
                edob=textInputEditText3.getText().toString();
                epass=textInputEditText4.getText().toString();
                ephno=textInputEditText5.getText().toString();
                eemail=textInputEditText6.getText().toString();


                if(RegisterViewModel.textFieldValidation(ecode,ename,edob,epass,ephno,eemail)) {


                    final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
                    animationView.playAnimation();
                    animationView.enableMergePathsForKitKatAndAbove( true );


                    Map<String, Object> mUser = new HashMap<>();

                    mUser.put("Employee-Code", ecode);
                    mUser.put("Employee-Name", ename);
                    mUser.put("Employee-DOB", edob);
                    mUser.put("Employee-Password", epass);
                    mUser.put("Employee-PhoneNo", eemail);
                    mUser.put("Employee-Email", ephno);

                    if (!user.equals(mUser)) {
                        mViewModel.modifyElements(mUser);

                        mViewModel.getChanged().observe(getActivity(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean isSuccessful) {
                                if (isSuccessful) {
                                    animationView.pauseAnimation();
                                    animationView.setVisibility(View.GONE);
                                    Snackbar.make(view, "Changes Made", Snackbar.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }
                                if (!isSuccessful) {
                                    animationView.pauseAnimation();
                                    animationView.setVisibility(View.GONE);
                                    Snackbar.make(view, "An Error Occurred", Snackbar.LENGTH_SHORT)
                                            .setAction("RETRY", listener)
                                            .show();

                                }
                            }
                        });

                    } else {
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        Snackbar.make(view, "No changes made", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else
                {

                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle("Error")
                            .setMessage("Incorrect values")
                            .setPositiveButton("OK",null)
                            .show();
                }

            }
        });



        final Button del=view.findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View.OnClickListener listener=this;

                new MaterialAlertDialogBuilder(getActivity())
                        .setTitle("Warning")
                        .setMessage("Do you want to permanently delete the Employee's data?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final LottieAnimationView animationView=view.findViewById(R.id.animation_view);
                                animationView.playAnimation();
                                animationView.enableMergePathsForKitKatAndAbove( true );
                                animationView.setVisibility(View.VISIBLE);
                                mViewModel.delete(user);


                                mViewModel.getChanged().observe(getActivity(), new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean isSuccessful) {
                                        if(isSuccessful){
                                            animationView.pauseAnimation();
                                            animationView.setVisibility(View.GONE);
                                            Snackbar.make(view,"User Deleted", Snackbar.LENGTH_SHORT).show();
                                            getActivity().onBackPressed();
                                        }
                                        if(!isSuccessful)
                                        {
                                            animationView.pauseAnimation();
                                            animationView.setVisibility(View.GONE);
                                            Snackbar.make(view,"An Error Occurred", Snackbar.LENGTH_SHORT)
                                                    .setAction("RETRY", listener)
                                                    .show();
                                        }
                                    }
                                });
                                del.setOnClickListener(null);
                            }
                        }).show();

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
