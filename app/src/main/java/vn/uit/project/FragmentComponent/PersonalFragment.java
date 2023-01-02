package vn.uit.project.FragmentComponent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.uit.project.Database.Client;
import vn.uit.project.Database.Database;
import vn.uit.project.R;

public class PersonalFragment extends Fragment {
    TextView tvName, tvUsername, tvPassword, tvAge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal, container, false);
        Init(view);
        SetUp();
        return view;
    }

    private void Init(View view) {
        tvAge = (TextView) view.findViewById(R.id.texAge);
        tvName = (TextView) view.findViewById(R.id.texName);
        tvUsername = (TextView) view.findViewById(R.id.texUsername);
        tvPassword = (TextView) view.findViewById(R.id.texPassword);
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
            tvName.setText(client.getName());
        }

        if (client.getAge() == 0) {
            tvAge.setText("No set");
            tvAge.setTypeface(null, Typeface.ITALIC);
            tvAge.setTextColor(getResources().getColor(R.color.neutral3));
        } else {
            tvAge.setText(client.getName());
        }

        tvUsername.setText(client.getUsername());
        String password = client.getPassword();
        String lastWord = password.charAt(password.length() - 1) +"";
        password = password.substring(0, password.length() - 1);

        tvPassword.setText(password.replaceAll("(?s).", "*") + lastWord);
    }
}
