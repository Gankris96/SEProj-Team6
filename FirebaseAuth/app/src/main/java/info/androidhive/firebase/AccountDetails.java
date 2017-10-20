package info.androidhive.firebase;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.StringTokenizer;

public class AccountDetails extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView uName,uEmail,uUsn,uAddr,uPhno,uGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detials);
        final String loginFrom=getIntent().getStringExtra("caller");
        uName=(TextView)findViewById(R.id.uname);
        uEmail=(TextView)findViewById(R.id.uemail);
        uUsn=(TextView)findViewById(R.id.uusn);
        uAddr=(TextView)findViewById(R.id.uaddr);
        uGender=(TextView)findViewById(R.id.uusrgender);
        uPhno=(TextView)findViewById(R.id.uphone);



        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        if(getIntent().getStringExtra("caller").equals("StudentLogin")){
            databaseReference=firebaseDatabase.getReference("studentsUsers");
        }
        databaseReference.orderByChild("studentEmail").equalTo(auth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childDataSnapshot:dataSnapshot.getChildren()){
                    HashMap<String,String> studDetails=(HashMap)childDataSnapshot.getValue();
                    if(loginFrom.equals("StudentLogin")) {
                        uName.setText("HELLO " + studDetails.get("studentName"));
                        uAddr.setText("ADDRESS " + studDetails.get("studentAddr"));
                        uPhno.setText("PHONE " + studDetails.get("studentPhno"));
                        uUsn.setText("USN " + studDetails.get("studentUSN"));
                        uGender.setText("Gender " + studDetails.get("studentGender"));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(loginFrom.equals("StudentLogin")){

            String text1="User Details Loading...";
            uName.setText(text1);
        }


    }

}
