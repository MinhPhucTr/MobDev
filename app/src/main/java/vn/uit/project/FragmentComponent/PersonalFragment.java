package vn.uit.project.FragmentComponent;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.uit.project.Database.Client;
import vn.uit.project.Database.Database;
import vn.uit.project.MainActivity;
import vn.uit.project.R;

public class PersonalFragment extends Fragment {
    TextView tvName, tvUsername, tvPassword, tvAge;
    Button butLogout, butChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal, container, false);
        Init(view);
        SetUp();
        clickButtonLogOut();
        clickButtonChangeInfo();
        return view;
    }

    private void clickButtonChangeInfo() {
        Database database = new Database(getContext());
        Bundle bundle = this.getArguments();
        butChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_information_layout);
                EditText ediOldPass = dialog.findViewById(R.id.ediOldPassChange);
                EditText ediNewPass = dialog.findViewById(R.id.ediNewPassChange);
                EditText ediName = dialog.findViewById(R.id.ediNameChange);
                EditText ediAge = dialog.findViewById(R.id.ediAgeChange);
                Button butUpdate = dialog.findViewById(R.id.butUpdate);
                Button butBack = dialog.findViewById(R.id.butBack2);
                butBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                butUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPass = ediOldPass.getText().toString();
                        String newPass = ediNewPass.getText().toString().trim();
                        String name = ediName.getText().toString();
                        String age = ediAge.getText().toString().trim();
                        if(oldPass.trim().length() <= 0)
                            Toast.makeText(getContext(), "Input Old Password!", Toast.LENGTH_SHORT).show();
                        else if(newPass.trim().length() <= 0)
                            Toast.makeText(getContext(), "Input New Password!", Toast.LENGTH_SHORT).show();
                        else if(name.trim().length() <= 0)
                            Toast.makeText(getContext(), "Input Name!", Toast.LENGTH_SHORT).show();
                        else if(age.trim().length() <= 0)
                            Toast.makeText(getContext(), "Input Age!", Toast.LENGTH_SHORT).show();
                        else
                        {
                            if(database.isOldPasswordCorrect(bundle.getString("Client"), oldPass))
                            {
                                Log.d("RESULT", "TRUE");
                                database.changeInformation(new Client(bundle.getString("Client"), newPass, name, Integer.valueOf(age)));
                                Log.d("NAME", name);
                                Log.d("AGE", age);
                                dialog.dismiss();
                                SetUp();
                                Toast.makeText(getContext(), "Update Account Successfully!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Log.d("RESULT", "FALSE");
                                Toast.makeText(getContext(), "Old Password is incorrect!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void clickButtonLogOut() {
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Init(View view) {
        tvAge = (TextView) view.findViewById(R.id.texAge);
        tvName = (TextView) view.findViewById(R.id.texName);
        tvUsername = (TextView) view.findViewById(R.id.texUsername);
        tvPassword = (TextView) view.findViewById(R.id.texPassword);
        butLogout = view.findViewById(R.id.butLogout);
        butChange = view.findViewById(R.id.butChangeInfo);
    }

    private void SetUp() {
        Database database = new Database(getContext());
        Bundle mBundle = this.getArguments();
        Client client = database.getClient(mBundle.getString("Client"));
        if (client.getName() == null) {
            tvName.setText("No set");
            tvName.setTypeface(null, Typeface.ITALIC);
            tvName.setTextColor(getResources().getColor(R.color.neutral3));
        } else {
            tvName.setText(client.getAge() + "");
        }

        if (client.getAge() == 0) {
            tvAge.setText("No set");
            tvAge.setTypeface(null, Typeface.ITALIC);
            tvAge.setTextColor(getResources().getColor(R.color.neutral3));
        } else {
            tvAge.setText(client.getAge() + "");
        }

        tvUsername.setText(client.getUsername());
        String password = client.getPassword();
        String lastWord = password.charAt(password.length() - 1) +"";
        password = password.substring(0, password.length() - 1);

        tvPassword.setText(password.replaceAll("(?s).", "*") + lastWord);
    }
}
