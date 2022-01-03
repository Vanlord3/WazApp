package id.web.wazapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registers extends AppCompatActivity {
    TextInputLayout etName,etUsername,etEmail,etPassword,etConfirmPass;
    Button btnReg,btnToLogin;
    ArrayList<User> listUser = new ArrayList<>();
    FirebaseAuth mAuth;

    AppDatabase db;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);

        etName = findViewById(R.id.name);
        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etConfirmPass = findViewById(R.id.password_confirmation);

        etUsername.setCounterMaxLength(8);

        btnReg = findViewById(R.id.register);
        btnToLogin = findViewById(R.id.tologin);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Registers.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait for a while");
        progressDialog.setCancelable(false);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DBProject").build();
        new getUser().execute();

        btnReg.setOnClickListener(view -> {
            if(!validateName() | !validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()){
                return;
            }

            String name = etName.getEditText().getText().toString();
            String username = etUsername.getEditText().getText().toString();
            String email = etEmail.getEditText().getText().toString();
            String password = etPassword.getEditText().getText().toString();
            String confirmpass = etConfirmPass.getEditText().getText().toString();




            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful() && task.getResult()!=null){
                        progressDialog.show();

                        FirebaseUser firebaseUser = task.getResult().getUser();

                        if(firebaseUser!=null){
                            String a = firebaseUser.getUid();
                            User u = new User(a,username,name,email,password);
                            new insertUser().execute(u);

                            database.getReference().child("Users").child(username).setValue(u);
                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reload();
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        btnToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Registers.this, Login.class);
            startActivity(intent);
            finish();
        });

    }
    private class getUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listUser.clear();
            listUser.addAll(db.userDAO().getUser());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            UserAdapter.notifyDataSetChanged();
        }
    }

    private class insertUser extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... Users) {
            db.userDAO().insertUser(Users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new getUser().execute();
//            etUser.setText("");
//            etJumlah.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(),Setup.class));
    }

    private void checkEmailExistsOrNot(){

    }

    private Boolean validateName(){
        String val = etName.getEditText().getText().toString();

        if(val.isEmpty()){
            etName.setError("Field cannot be empty");
            return false;
        }
        else{
            etName.setError(null);
            etName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val = etUsername.getEditText().getText().toString();

        if(val.isEmpty()){
            etUsername.setError("Field cannot be empty");
            return false;
        }else if(val.length() > 8){
            etUsername.setError("Username too long");
            return false;
        }
        else{
            etUsername.setError(null);
            etUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = etEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            etEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            etEmail.setError("Invalid Email Address");
            return false;
        }
        else{
            etEmail.setError(null);
            etEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = etPassword.getEditText().getText().toString();
        String passwordVal = "^                 # start-of-string\n" +
                "(?=.*[0-9])       # a digit must occur at least once\n" +
                "(?=.*[a-z])       # a lower case letter must occur at least once\n" +
                "(?=.*[A-Z])       # an upper case letter must occur at least once\n" +
//                "(?=.*[@#$%^&+=])  # a special character must occur at least once\n" +
                "(?=\\S+$)          # no whitespace allowed in the entire string\n" +
                ".{5,}             # anything, at least eight places though\n" +
                "$                 # end-of-string";

        if(val.isEmpty()){
            etPassword.setError("Field cannot be empty");
            return false;
        }
        else if(val.length() < 6){
            etPassword.setError("Password is too weak");
            return false;
        }
        else{
            etPassword.setError(null);
            etPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateConfirmPassword(){
        String val = etConfirmPass.getEditText().getText().toString();
        String val2 = etPassword.getEditText().getText().toString();

        if(val.isEmpty()){
            etConfirmPass.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(val2)){
            etConfirmPass.setError("Password doesn't match");
            return false;
        }
        else{
            etConfirmPass.setError(null);
            etConfirmPass.setErrorEnabled(false);
            return true;
        }
    }
}