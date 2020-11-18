package my.edu.wings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class Home_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView username;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
try{
    gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    googleApiClient=new GoogleApiClient.Builder(this)
            //.enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build();
}catch (Exception e){}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home__menu, null);
        navigationView.addHeaderView(header);
        username = (TextView) header.findViewById(R.id.txthusnmhome);

        username.setText(SaveSharedPreference.getUserName(Home_Menu.this));

        setTitle("Home");
        Fragment fragment = new homepage();
        FragmentManager fragmentManager = getSupportFragmentManager();
       fragmentManager.beginTransaction().replace(R.id.flContent, fragment,null).commit();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_home) {
            fragmentClass = homepage.class;
            setTitle("Home");

       }  else if (id == R.id.nav_profile) {
            fragmentClass=MyProfile.class;
            setTitle("My Profile");
        }

        else if (id == R.id.nav_video) {
            Intent intent= new Intent(Home_Menu.this,SelectCourse.class);
            intent.putExtra("nm","Learn Through Video");
            intent.putExtra("for","Learn Through Video");
            startActivity(intent);
        }else if (id == R.id.nav_prac) {
            Intent intent= new Intent(Home_Menu.this,SelectCourse.class);
            intent.putExtra("nm","Practice Test");
            intent.putExtra("for","Practice Test");
            startActivity(intent);
        }else if (id == R.id.nav_all) {
            Intent intent= new Intent(Home_Menu.this,SelectCourse.class);
            intent.putExtra("nm","All India Rank Level");
            intent.putExtra("for","All India Rank Level");
            startActivity(intent);
        }
        else if (id == R.id.nav_history) {
            startActivity(new Intent(Home_Menu.this,History.class));
        }


        else if (id == R.id.nav_abtsus) {
            fragmentClass=Aboutus.class;
            setTitle("About us");
        }else if (id == R.id.nav_lout) {
logout();
        }

        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
    public void logout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure  want to Sign Out?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
try{
    LoginManager.getInstance().logOut();
}catch (Exception e){}
                                try{
                                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                            new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(Status status) {

                                                }
                                            });
                                }catch (Exception e){}
                                SaveSharedPreference.setUserId(Home_Menu.this,"");
                                SaveSharedPreference.setUserName(Home_Menu.this,"");
                                SaveSharedPreference.setRefCode(Home_Menu.this,"");

                                Intent intent = new Intent(Home_Menu.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
