package vn.uit.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText ediUserName, ediPassword;
    Button butLogIn;
    TextView texSignUp;
    Database mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        clicktexSignUp();
        clickbutLogIn();
    }

    private void initial()
    {
        ediUserName = (EditText) findViewById(R.id.ediUsernameMain);
        ediPassword = (EditText) findViewById(R.id.ediPasswordMain);
        butLogIn = (Button) findViewById(R.id.butLogInMain);
        texSignUp = (TextView) findViewById(R.id.textViewSignUp);
        mDatabase = new Database(MainActivity.this);
    }

    private void clicktexSignUp()
    {
        texSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickbutLogIn()
    {
        butLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = ediUserName.getText().toString().trim();
                String pass = ediPassword.getText().toString().trim();
                if (user.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter Username!", Toast.LENGTH_SHORT).show();
                else if(pass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter Password!", Toast.LENGTH_SHORT).show();
                else
                {
                    if(mDatabase.checkForSignIn(user, pass)) {

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("USERNAME", user);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this, "Check Information Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}