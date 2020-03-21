package com.example.GreenWich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText name,email,password,telephone;
    Button signup;
    Customer customer;
    DatabaseReference dbRef;
    long _id=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        telephone = findViewById(R.id.telephone);
        signup = findViewById(R.id.signup);

        customer = new Customer();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _id = dataSnapshot.getChildrenCount() + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                boolean x = validateForm();

                if(x == true) {
                    customer.setName(name.getText().toString().trim());
                    customer.setEmail(email.getText().toString().trim());
                    customer.setPassword(password.getText().toString().trim());
                    customer.setTelephone(telephone.getText().toString().trim());

                    dbRef.child(String.valueOf(_id)).setValue(customer);
                    Toast.makeText(Register.this, "Data Saved Successfuly", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                }
            }

        });

    }
    private boolean validateForm(){

        String inputEmail = email.getText().toString();
        String inputName = name.getText().toString();
        String inputPassword = password.getText().toString();
        String inputTelephone = telephone.getText().toString();

        if(inputEmail.isEmpty() || inputName.isEmpty() || inputPassword.isEmpty() || inputTelephone.isEmpty() ){
            email.setError("Feild can't be empty");
            name.setError("Feild can't be empty");
            password.setError("Feild can't be empty");
            telephone.setError("Feild can't be empty !");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            email.setError("Please enter a valid email address");
            return false;
        }else if(!(telephone.length()==10)){
            telephone.setError("Invalid Telephone Number");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }

    public void Login(View view) {
        Intent intent1 = new Intent(this, Login.class);
        startActivity(intent1);
    }
}
