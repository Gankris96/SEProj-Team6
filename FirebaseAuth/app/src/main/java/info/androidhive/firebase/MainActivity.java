package info.androidhive.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove, signOut,btnAccountDetails,btnmaps,btnAllocateBus;
    private Button userName;
    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String oldEmailString;
    boolean isUpdated=false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //wrap with IF for other user types. SO the .getReference(); will change to driverUsers or parentUsers
    DatabaseReference myref = database.getReference("studentsUsers");
    String userId;
    String userUSN,userPass,userAddr,userGender,userPhone,uName;
    Boolean busAlloc;
    static int signedOut=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    userName=(Button) findViewById(R.id.userName);
                    String text=(String)userName.getText();
                    userName.setText(text+" "+user.getEmail());
                }
            }
        };
        btnmaps=(Button) findViewById(R.id.maps);
        btnChangeEmail=(Button) findViewById(R.id.change_email_button);
        btnAllocateBus = (Button) findViewById(R.id.allocate_bus);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
        //btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        btnAccountDetails=(Button)findViewById(R.id.userdetails);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button) findViewById(R.id.changePass);
        sendEmail = (Button) findViewById(R.id.send);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);

        oldEmail = (EditText) findViewById(R.id.old_email);
        newEmail = (EditText) findViewById(R.id.new_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);
        if(!getIntent().getStringExtra("caller").equals("StudentLogin"))
            btnAllocateBus.setVisibility(View.GONE);
        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        //Toast.makeText(MainActivity.this, getIntent().getStringExtra("caller"), Toast.LENGTH_SHORT).show();
        if((getIntent().getStringExtra("caller").equals("StudentLogin")) && (LoginActivity.loggedIn==true)) { //signupBtn = yes/no based on busAllocate=no/yes
            Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
            try {
                myref.orderByChild("studentEmail").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            //HashMap<String, String> studDetails = (HashMap) childDataSnapshot.getValue();
                            StudentUser temp = childDataSnapshot.getValue(StudentUser.class);
                            busAlloc =  temp.getAllocationStatus();
                            Toast.makeText(MainActivity.this, String.valueOf(busAlloc), Toast.LENGTH_SHORT).show();
                            if(busAlloc)
                                btnAllocateBus.setVisibility(View.GONE);

                            else
                                btnAllocateBus.setVisibility(View.VISIBLE);

                        }

                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //do nothing, do not update if error
                    }
                });
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        else{
            Toast.makeText(MainActivity.this, "not a student", Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, getIntent().getStringExtra("caller"), Toast.LENGTH_SHORT).show();
        }


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        btnmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent=new Intent(getApplicationContext(), MapsActivity.class);
                mapIntent.putExtra("caller",getIntent().getStringExtra("caller"));
                startActivity(mapIntent);
                finish();
            }
        });

        btnAccountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountDet=new Intent(getApplicationContext(), AccountDetails.class);
                accountDet.putExtra("caller",getIntent().getStringExtra("caller"));
                startActivity(accountDet);
                finish();
            }
        });

        btnAllocateBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myref.orderByChild("studentEmail").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                //HashMap<String, String> studDetails = (HashMap) childDataSnapshot.getValue();
                                StudentUser temp = childDataSnapshot.getValue(StudentUser.class);

                                busAlloc =  temp.getAllocationStatus();
                                Toast.makeText(MainActivity.this, temp.getStudentUSN(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, String.valueOf(busAlloc), Toast.LENGTH_SHORT).show();
                                temp.setAllocationStatus(true);
                                myref.child(temp.getStudentUSN()).setValue(temp);
                                btnAllocateBus.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //do nothing, do not update if error
                        }
                    });
                } catch (Exception e) {
                    // e.printStackTrace();
                }

            }
        });
        //if user wants to change email
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });
        //then allow user to type new mailID and click on Change button
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    oldEmailString=user.getEmail();
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        if(getIntent().getStringExtra("caller").equals("StudentLogin")) {
                                            try {
                                                myref.orderByChild("studentEmail").equalTo(oldEmailString).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                                            HashMap<String, String> studDetails = (HashMap) childDataSnapshot.getValue();
                                                            uName = studDetails.get("studentName");
                                                            userId = studDetails.get("studentUserId");
                                                            userUSN = studDetails.get("studentUSN");
                                                            userPass = studDetails.get("studentPass");
                                                            userAddr = studDetails.get("studentAddr");
                                                            userGender = studDetails.get("studentGender");
                                                            userPhone = studDetails.get("studentPhno");
                                                        }
                                                        StudentUser UpdatedStudentUser = new StudentUser(userId, uName, newEmail.getText().toString().trim(), userPass, userUSN, userAddr, userPhone, userGender, true);
                                                        myref.child(userUSN).setValue(UpdatedStudentUser);
                                                        signOut();
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        //do nothing, do not update if error
                                                    }
                                                });
                                            } catch (Exception e) {
                                                // e.printStackTrace();
                                            }

                                        Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        //signOut();
                                        }
                                        else if(getIntent().getStringExtra("caller").equals("ParentLogin")) {
                                            try {
                                                myref.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                                            HashMap<String, String> parentDetails = (HashMap) childDataSnapshot.getValue();
                                                            userId=parentDetails.get("usn");
                                                        }
                                                        ParentUser parentUserUpdate = new ParentUser( newEmail.getText().toString().trim(),userId);
                                                        myref.child(userId).setValue(parentUserUpdate);
                                                        signOut();
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        //do nothing, do not update if error
                                                    }
                                                });
                                            } catch (Exception e) {
                                                // e.printStackTrace();
                                            }

                                            Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                            //signOut();
                                        }
                                        isUpdated=true;
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
                if(isUpdated==true) {
                    System.out.println("hello");

                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!oldEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    oldEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

       /* btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(MainActivity.this, StudentLogin.class));
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });*/

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SelectActivity.count=-1;
                signOut();
            }
        });

    }

    //sign out method
    public void signOut() {
        signedOut=1;
        LoginActivity.loggedIn=false;
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}