package com.blogspot.rajbtc.G2GTechUser.my_activity.other_costing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.LoadingAlert;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Costing_Fragment extends Fragment {
    private String[] tabData={"Costing","Salary","Credit"};
    private String tabName,nid,mySiteID,myName,TAG="===Costing_Fragment===";
    private View view;
    private RecyclerView recyclerView;
    private  ArrayList<CostingItem> arrayList=new ArrayList<>();
    private FirebaseFirestore firestoreDB=FirebaseFirestore.getInstance();
    private LoadingAlert progress;
    SharedPreferences sp;

    public Costing_Fragment(String tabName,String nid) {
        this.tabName=tabName;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_employee_all_costing, container, false);

        nid=getContext().getSharedPreferences("login",Context.MODE_PRIVATE).getString("myNID","null");
        mySiteID=getContext().getSharedPreferences("login",Context.MODE_PRIVATE).getString("mySiteID","null");
        myName=getContext().getSharedPreferences("login",Context.MODE_PRIVATE).getString("myName","null");

        setUpView();
        showData();
        return view;
    }


    void setUpView(){
        sp= getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        progress=new LoadingAlert(getContext());
        recyclerView=view.findViewById(R.id.fragmentEmployee_allCostingRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(!tabName.equals(tabData[0]))
            ((FloatingActionButton)view.findViewById(R.id.fragmentEmployee_allCostingFlb)).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.fragmentEmployee_allCostingFlb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               firestoreDB.collection("InstrumentSug").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           ArrayList<String> costName=new ArrayList<>();
                           ArrayList<String> type=new ArrayList<>();

                           for(QueryDocumentSnapshot ds:task.getResult()){
                               costName.add(ds.getString("Name"));
                               type.add(ds.getString("Unit"));
                           }

                           setUpAlertDialog(costName,type);

                       } else {

                       }


                   }
               });

            }
        });
    }

    
    
    
    
    
    
    
    
    //AlertDialog will call when Instrument list for suggestion will load from firestore
    void setUpAlertDialog(ArrayList<String> instSugArray, final ArrayList<String> type){
        instSugArray.add("Other");
        type.add("Other");
        progress.start();
        LinearLayout layout=new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText quantityEt=new EditText(getContext());
        quantityEt.setHint("Quantity");
        quantityEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText inputSalary=new EditText(getContext());
        inputSalary.setHint("Total Cost");
        inputSalary.setInputType(InputType.TYPE_CLASS_NUMBER);

        final Spinner costSelSpinner=new Spinner(getContext());
        layout.addView(costSelSpinner);
        layout.addView(quantityEt);
        layout.addView(inputSalary);

        costSelSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,instSugArray));

        costSelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(type.get(i).equals("Other")){
                    quantityEt.setHint("Cost description");
                    quantityEt.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                else{
                    quantityEt.setHint("Input quantity in "+type.get(i));
                    quantityEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


   //Adding a new costing by employee
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String pay=((EditText)inputSalary).getText().toString();
                final String quantity=((EditText)quantityEt).getText().toString();
                final String payName=costSelSpinner.getSelectedItem().toString();
                if(pay.equals("") || payName.equals("") || quantity.equals("")){
                    Toast.makeText(getContext(),"Please input first",Toast.LENGTH_SHORT).show();
                    progress.stop();
                    return;
                }addData(payName,pay,quantity,type.get(costSelSpinner.getSelectedItemPosition()));


            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progress.stop();
            }
        }).setTitle("Input Cost").setView(layout).show();
    }
    





    
    //Adding data to my activity payment
    void addData(final String costName, final String payment, final String quantity, String quantityType){

         final SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
         final SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm:ss");
         final Date format = new Date(System.currentTimeMillis());


        if(nid.equals("null") || mySiteID.equals("null") || myName.equals("null")){
            Toast.makeText(getContext(),"Please restart the app",Toast.LENGTH_SHORT).show();
            progress.stop();
            return;
        }


        Map<String, Object> user = new HashMap<>();
        user.put("CostName",costName);
        user.put("Payment",payment);
        user.put("Type", "Pending");
        user.put("Date", dateFormat.format(format));
        user.put("Time", timeFormat.format(format));
        user.put("SiteID",mySiteID);
        user.put("QuantityType",quantityType);
        user.put("Quantity",quantity);
        user.put("Name",myName);


        firestoreDB.collection("EmpCosting").document(nid).collection(tabName)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"Data added",Toast.LENGTH_SHORT).show();
                        sendPushNotificationToAdmin();
                        arrayList.add(new CostingItem(documentReference.getId(),costName,quantity,payment,"Pending",timeFormat.format(format),dateFormat.format(format)));
                        recyclerView.setAdapter(new CostingRecyAdapter(getContext(),arrayList,nid,tabName));
                        progress.stop();
                        }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Data added failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Load and show costing, salary & credit from firestore
    void showData(){
        firestoreDB.collection("EmpCosting").document(nid).collection(tabName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           arrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                arrayList.add(new CostingItem(document.getId(),document.getString("CostName"),document.getString("Quantity"),document.getString("Payment"),document.getString("Type"),document.getString("Time"),document.getString("Date")));
                            }
                            recyclerView.setAdapter(new CostingRecyAdapter(getContext(),arrayList,nid,tabName));

                        } else {
                             Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }


    void sendPushNotificationToAdmin(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NewCosting");
        myRef.setValue(sp.getString("mySiteName","")+"\n Added by "+sp.getString("myName",""));
    }
}