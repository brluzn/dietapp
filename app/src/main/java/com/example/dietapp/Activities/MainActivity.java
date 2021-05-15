package com.example.dietapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbRequest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dietapp.Models.UserInfo;
import com.example.dietapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.DecimalFormat;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Button submit;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText name_input;
    EditText surname_input;
    EditText height_input;
    EditText weight_input;
    FrameLayout frameLayout;

    CircleImageView profile_image;

    Uri image_uri;
    private String my_uri="";

    FirebaseAuth auth= FirebaseAuth.getInstance();
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseUser currentUser=auth.getCurrentUser();
        FirebaseUser cUser=currentUser;

        storageProfilePicsRef= FirebaseStorage.getInstance().getReference().child("Profile Pics");
        profile_image=findViewById(R.id.profile_image);




        radioGroup=findViewById(R.id.radio_group);

        submit=findViewById(R.id.submit_btn);
        name_input=findViewById(R.id.name_input);
        surname_input=findViewById(R.id.surname_input);
        height_input=findViewById(R.id.height_input);
        weight_input=findViewById(R.id.weight_input);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(MainActivity.this);
            }
        });

        getUserInfo();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioId);
                String name=name_input.getText().toString();
                String surname=surname_input.getText().toString();
                String height=height_input.getText().toString();
                String weight=weight_input.getText().toString();
                String gender=radioButton.getText().toString();


                String bmi_state=new String();

                float height_m=(Float.parseFloat(height)/100);
                float bmi=(Float.parseFloat(weight))/((height_m)*(height_m));
                float roundedBmi = (float) (Math.round(bmi * 100.0) / 100.0);
                System.out.println("bmi"+bmi);

                if (bmi!=0){

                    if (0.0<bmi && bmi<15.9){
                        bmi_state="OverUnderweight";
                    }
                    else if (16.0<bmi && bmi<18.4){
                        bmi_state="Underweight";
                    }
                    else if (18.5<bmi && bmi<24.9){
                        bmi_state="Normal";
                    }
                    else if (25.0<bmi && bmi<29.9){
                        bmi_state="Overweight";
                    }
                    else if (30.0<bmi && bmi<34.9){
                        bmi_state="Obesity";
                    }
                    else
                        bmi_state=null;

                }


                System.out.println("bmi____"+roundedBmi);
                UserInfo user=new UserInfo(name,surname,gender,height,weight,String.valueOf(roundedBmi),bmi_state);

                SendDatabase(user,cUser);




                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }


        });




    }

    private void getUserInfo() {
        DatabaseReference reference;

        reference=FirebaseDatabase.getInstance().getReference().child("User");

        reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()>0){
                    if (snapshot.hasChild("image")){
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            image_uri=result.getUri();
            profile_image.setImageURI(image_uri);
            uploadProfileImage();
        }else
            System.out.println("URi ERROR");
    }

    public void checkRadioButton(View view){
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

    }

    public void SendDatabase(UserInfo user,FirebaseUser cUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        System.out.println(cUser);
        DatabaseReference myRef = database.getReference("Users").child(cUser.getUid());



        myRef.setValue(user);


    }



    private void uploadProfileImage() {
        DatabaseReference reference;
        reference=FirebaseDatabase.getInstance().getReference().child("User");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Set Your Profile Image");
        progressDialog.setMessage("Please wait,while we are setting your data");
        progressDialog.show();

        if (image_uri!=null){
            final StorageReference fileRef=storageProfilePicsRef.child(auth.getCurrentUser().getUid()+".jpg");
            uploadTask=fileRef.putFile(image_uri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull  Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull  Task<Uri> task) {
                    if (task.isSuccessful()){

                        Uri downloadUri=  task.getResult();
                        my_uri=downloadUri.toString();

                        HashMap<String,Object> userMap=new HashMap<>();
                        userMap.put("image",my_uri);

                        reference.child(auth.getCurrentUser().getUid()).updateChildren(userMap);

                        progressDialog.dismiss();



                    }
                }
            });
        }else
            progressDialog.dismiss();

    }



}