package co.edu.udea.compumovil.gr05.lab2apprun;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationDrawerActivity extends AppCompatActivity {
    /**
     * Instancia del drawer
     */
    private DrawerLayout drawerLayout;

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            // Añadir carácteristicas
            setupDrawerContent(navigationView);
        }
        drawerTitle = getResources().getString(R.string.eventos_item);
        if (savedInstanceState == null) {
            // Seleccionar item
            selectItem(drawerTitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Colocar ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setIcon(R.mipmap.logo_grupo5);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }

    private void selectItem(String title) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;


        switch (title){
            case "Eventos":
                fragment = new EventosFragment();
                break;

            case "Perfil":
                fragment = new PerfilFragment();
                break;

            case "Acerca de":
                fragment = new AboutFragment();
                break;

            case "Cerrar sesión":
                SharedPreferences preferencias = getSharedPreferences(InicioActivity.TAG_PREFERENCIAS, Context.MODE_PRIVATE);
                preferencias.edit().clear().commit();
                finish();
                Intent intent = new Intent(this,InicioActivity.class);
                startActivity(intent);
                break;
        }

        if (fragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenido_principal,fragment);
            fragmentTransaction.commit();
            setTitle(title); // Setear título actual
        }
        drawerLayout.closeDrawers(); // Cerrar drawer
    }
}
