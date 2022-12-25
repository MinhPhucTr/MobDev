package vn.uit.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.uit.project.Database.Database;

public class SignUpActivity extends Activity {
    EditText ediUserName, ediPassword;
    Button butSignUp;
    Database mDatabase;
    TextView texLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inital();
        clickTextLogin();
        clickButtonSignUp();
    }

    private void inital() {
        texLogin = (TextView) findViewById(R.id.textViewLogin);
        ediUserName = (EditText) findViewById(R.id.ediUsername);
        ediPassword = (EditText) findViewById(R.id.ediPassword);
        butSignUp = (Button) findViewById(R.id.butSignUp);
        mDatabase = new Database(SignUpActivity.this);
    }

    private void clickTextLogin(){
        texLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(login);
            }
        });
    }

    private void clickButtonSignUp()
    {
        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ediUserName.getText().toString().trim();
                String pass = ediPassword.getText().toString().trim();
                if (name.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter Username!", Toast.LENGTH_SHORT).show();
                else if(pass.equals(""))
                    Toast.makeText(SignUpActivity.this, "Please enter Password!", Toast.LENGTH_SHORT).show();
                else
                {
                    if(mDatabase.isAccountExisted(name))
                        Toast.makeText(SignUpActivity.this, "This account is used!", Toast.LENGTH_SHORT).show();
                    else
                    {
                        mDatabase.createAccount(name, pass);
                        Toast.makeText(SignUpActivity.this, "Create Account Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

}