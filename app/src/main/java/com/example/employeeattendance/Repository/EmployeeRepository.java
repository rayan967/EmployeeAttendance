package com.example.employeeattendance.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.employeeattendance.Adapter.AttendanceLogItem;
import com.example.employeeattendance.Fragment.ModifyFragment2;
import com.example.employeeattendance.ViewModel.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.validation.Validator;

public class EmployeeRepository {

    private String ecode;
    private String ename;
    private String edob;
    private String epass;
    private String ephno;
    private String eemail;
    MutableLiveData<Boolean> validate=new MutableLiveData<>();
    MutableLiveData<Map<String,Object>> details=new MutableLiveData<>();
    MutableLiveData<Boolean> changed=new MutableLiveData<>();
    RegisterViewModel mRegisterViewModel;

    private LogDao aDao;
    private LiveData<List<AttendanceLogItem>> mAllRows;

    public EmployeeRepository()
    {
    }

    public EmployeeRepository(String ecode, String ename, String edob, String epass, String ephno, String eemail, RegisterViewModel viewModel){

        this.ecode=ecode;
        this.ename=ename;
        this.edob=edob;
        this.epass=epass;
        this.ephno=ephno;
        this.eemail=eemail;
        mRegisterViewModel=viewModel;
    }

    public void register()
    {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference myRef =database.collection("Employees").document(ecode);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds;
                if(task.isSuccessful()) {
                    ds = task.getResult();
                    if (ds.exists()) {

                        changed.postValue(false);
                    }
                    else{
                        Map<String, Object> user = new HashMap<>();
                        user.put("Employee-Code", ecode);
                        user.put("Employee-Name", ename);
                        user.put("Employee-DOB", edob);
                        user.put("Employee-Password", epass);
                        user.put("Employee-PhoneNo", ephno);
                        user.put("Employee-Email", eemail);
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        database.collection("Employees").document(ecode).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                changed.postValue(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mRegisterViewModel.onFailure();
                                e.printStackTrace();
                            }
                        });
                    }

                }
                else
                {

                }

            }
        });
    }





    public EmployeeRepository(final String ecode, final String epass)
    {
        this.ecode=ecode;
        this.epass=epass;
        validate=new MutableLiveData<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference myRef =database.collection("Employees").document(ecode);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds;
                if(task.isSuccessful()) {
                    ds = task.getResult();
                    if (ds.exists()) {
                        if (ds.get("Employee-Password", String.class).equals(epass)) {
                            validate.postValue(true);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Employee-Code", ds.get("Employee-Code",String.class));
                            user.put("Employee-Name",  ds.get("Employee-Name",String.class));
                            user.put("Employee-DOB",  ds.get("Employee-DOB",String.class));
                            user.put("Employee-Password",  ds.get("Employee-Password",String.class));
                            user.put("Employee-PhoneNo",  ds.get("Employee-PhoneNo",String.class));
                            user.put("Employee-Email",  ds.get("Employee-Email",String.class));
                            details.setValue(user);
                        }
                        else
                            validate.setValue(false);
                    }
                    else
                        validate.setValue(false);
                }
                else
                    validate.setValue(false);
            }
        });
    }


    public void modifyData(final Map<String,Object> user)
    {
        ecode=(String)user.get("Employee-Code");
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference myRef =database.collection("Employees").document(ecode);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds;
                if(task.isSuccessful()) {
                    ds = task.getResult();
                    if (ds.exists()) {

                            database.collection("Employees").document(ecode).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    changed.postValue(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    changed.postValue(false);
                                    e.printStackTrace();
                                }
                            });
                    }
                }
                else
                {
                    changed.postValue(false);
                }

            }
        });
    }

    public void delete(final Map<String,Object> user)
    {
        ecode = (String) user.get("Employee-Code");
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        DocumentReference myRef = database.collection("Employees").document(ecode);

        myRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                changed.postValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                changed.postValue(false);
                e.printStackTrace();
            }
        });
    }

    public void addToLog(final String ecode, final String mode)
    {
        Date d=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String date=sdf.format(d);
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final DocumentReference myRef = database.collection("Employees").document(ecode);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds;
                if(task.isSuccessful()) {
                    ds = task.getResult();
                    if (ds.exists()) {
                        ename=ds.get("Employee-Name",String.class);
                        Map<String, Object> log=new HashMap<>();
                        log.put("Employee-Code",ecode);
                        log.put("Employee-Name",ename);
                        log.put("Timestamp",date);
                        log.put("Mode",mode);
                        CollectionReference attendance =database.collection("Attendance");
                        attendance.document(date).set(log).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                changed.postValue(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                changed.postValue(false);
                                e.printStackTrace();
                            }
                        });
                    }
                }
                else
                {
                    changed.postValue(false);
                }

            }
        });


    }

    public MutableLiveData<Boolean> isSuccessful()
    {
        return changed;
    }

    public MutableLiveData<Boolean> getValidateResult()
    {
        return validate;
    }

    public MutableLiveData<Map<String,Object>> getDetails()
    {
        return details;
    }


    public void getLogList(Application application)
    {
        LogRoomDatabase db = LogRoomDatabase.getDatabase(application);
        aDao = db.aDao();
        retrieveList();
        mAllRows = aDao.getAllRows();
    }

    public LiveData<List<AttendanceLogItem>> getAllRows()
    {
        return mAllRows;
    }

    public void retrieveList()
    {
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("Attendance")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            aDao.deleteAll();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AttendanceLogItem word = new AttendanceLogItem(document.get("Employee-Name", String.class)+
                                        " ("+document.get("Employee-Code",String.class)+")",
                                        document.get("Mode",String.class),
                                        document.get("Timestamp",String.class));
                                aDao.insert(word);
                            }
                        } else {

                        }
                    }
                });
    }

    public static void deleteLogItem(String time)
    {
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference myRef = database.collection("Attendance").document(time);
        myRef.delete();
    }

}
