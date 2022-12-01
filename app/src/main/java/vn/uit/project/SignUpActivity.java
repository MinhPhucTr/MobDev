package vn.uit.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText ediUserName, ediPassword;
    Button butSignUp;
    Database mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inital();
        clickButtonSignUp();
    }

    private void inital() {
        ediUserName = (EditText) findViewById(R.id.ediUsername);
        ediPassword = (EditText) findViewById(R.id.ediPassword);
        butSignUp = (Button) findViewById(R.id.butSignUp);
        mDatabase = new Database(SignUpActivity.this);
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