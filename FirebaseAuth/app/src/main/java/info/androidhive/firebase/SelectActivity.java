package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    Button _selectstudent;
    Button _selectdriver;
    Button _selectparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        _selectstudent=(Button)findViewById(R.id.btn_student_login) ;
        _selectdriver=(Button)findViewById(R.id.btn_driver_login) ;
        _selectparent=(Button)findViewById(R.id.btn_parent_login) ;

        _selectstudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity for Student
                Intent intent = new Intent(SelectActivity.this, LoginActivity.class);
                intent.putExtra("previous","select");
                intent.putExtra("caller","StudentLogin");
                startActivity(intent);
            }
        });

        _selectdriver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity for Driver
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        _selectparent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity for Student
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}
