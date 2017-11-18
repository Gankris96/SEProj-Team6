package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AllocateBus extends AppCompatActivity {

    private Spinner displayBus,busRoutes;
    private Button btnAllocate;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference databaseStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_bus);
        displayBus=(Spinner)findViewById(R.id.display_buses);
        busRoutes=(Spinner)findViewById(R.id.bus_routes);
        btnAllocate=(Button) findViewById(R.id.btn_allocate);
        //logic to dynamically populate Spinners
        /*
        * DB read to get buses and populate the displayBuses Spinner*/

        /*
        * According to bus number display routes*/

        btnAllocate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //allocation logic based on gender etc..
            }
        });

    }

}
