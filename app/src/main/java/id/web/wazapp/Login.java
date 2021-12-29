package id.web.wazapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputLayout txtEmail,txtPassword;
    Button btnForgetPass,btnLogin,btnToReg;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.logemail);
        txtPassword = findViewById(R.id.logpassword);

        btnForgetPass = findViewById(R.id.forgetpassword);
        btnLogin = findViewById(R.id.login);
        btnToReg = findViewById(R.id.toregister);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            if(!validateEmail() | !validatePassword()){
                return;
            }
            else {
                isUser();
            }
        });

        btnToReg.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this,Registers.class);
            startActivity(intent);
            finish();
        });
    }

    private void isUser(){
        String email = txtEmail.getEditText().getText().toString();
        String password = txtPassword.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    if(task.getResult().getUser()!=null){
                        reload();
                    }else{
                        Toast.makeText(getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Failed Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateEmail(){
        String val = txtEmail.getEditText().getText().toString();

        if(val.isEmpty()){
            txtEmail.setError("Field cannot be empty");
            return false;
        }
        else{
            txtEmail.setError(null);
            txtEmail.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePassword(){
        String val = txtPassword.getEditText().getText().toString();

        if(val.isEmpty()){
            txtPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            txtPassword.setError(null);
            txtPassword.setErrorEnabled(false);
            return true;
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
}