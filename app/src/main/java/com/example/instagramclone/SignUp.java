package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText personname,kickpower,kickspeed,punchspeed,punchpower;
    private Button save,btnGetAllData;
    private TextView txtGetData;
    private String allBoxers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        personname = findViewById(R.id.NameStore);
        kickpower = findViewById(R.id.KickPower);
        kickspeed = findViewById(R.id.KickSpeed);
        punchpower = findViewById(R.id.PunchingPower);
        punchspeed = findViewById(R.id.PunchingSpeed);
        txtGetData = findViewById(R.id.txtGetData);
        save = findViewById(R.id.SaveToServer);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        save.setOnClickListener(SignUp.this);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Boxer");
                parseQuery.getInBackground("lIY2YjvVzz", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){
                            txtGetData.setText(object.get("Name")+"-Punching Power = "+object.get("Punching_Power"));
                        }
                    }
                });
            }
        });
        btnGetAllData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                allBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Boxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject Boxer:objects) {
                                    allBoxers = allBoxers + Boxer.get("Name") + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            }else{
                                FancyToast.makeText(SignUp.this, "Error.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SaveToServer:
                try {
                    final ParseObject Boxer = new ParseObject("Boxer");
                    Boxer.put("Name", personname.getText().toString());
                    Boxer.put("Punching_Power", punchpower.getText().toString());
                    Boxer.put("Punching_Speed", punchspeed.getText().toString());
                    Boxer.put("Kick_Power", kickpower.getText().toString());
                    Boxer.put("Kick_Speed", kickspeed.getText().toString());
                    Boxer.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, Boxer.get("Name") + " data is saved to server.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage() + " brings error.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }catch(Exception e){
                    FancyToast.makeText(SignUp.this, e.getMessage() + " brings error.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
                break;

        }
    }
}
