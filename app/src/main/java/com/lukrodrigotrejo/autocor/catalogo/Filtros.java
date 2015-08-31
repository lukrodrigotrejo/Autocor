package com.lukrodrigotrejo.autocor.catalogo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lukrodrigotrejo.autocor.R;
import com.lukrodrigotrejo.autocor.db.greendao.Marca;
import com.lukrodrigotrejo.autocor.db.greendao.Rubro;
import com.lukrodrigotrejo.autocor.db.greendao.Tipo_Auto;
import com.lukrodrigotrejo.autocor.db.manager.Global;

import java.util.ArrayList;

public class Filtros extends ActionBarActivity {
    Spinner spinMarca, spinTipo, spinRubro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
        spinMarca = (Spinner)findViewById(R.id.sp_depro_marca);
        spinTipo = (Spinner)findViewById(R.id.sp_depro_tipoAuto);
        spinRubro = (Spinner)findViewById(R.id.sp_depro_rubro);
        populateSpinners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtros, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.logo_autocor);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_gradient));
        actionBar.setTitle(" Autocor");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateSpinners(){
        //Marcas
        Marca marca = new Marca();
        marca.setDescripcion("Seleccione una Marca...");
        ArrayList<Marca> spMarcas = new ArrayList<Marca>();
        spMarcas.add(0, marca);
        spMarcas.addAll(Global.getDaoSession().getMarcaDao().loadAll());
        ArrayAdapter<Marca> adapterMarca = new ArrayAdapter<Marca>(this, android.R.layout.simple_spinner_item, spMarcas);
        spinMarca.setAdapter(adapterMarca);


        //Tipo
        Tipo_Auto tipo_auto = new Tipo_Auto();
        tipo_auto.setDescripcion("Seleccione un Tipo...");
        ArrayList<Tipo_Auto> spTipo = new ArrayList<Tipo_Auto>();
        spTipo.add(0, tipo_auto);
        spTipo.addAll(Global.getDaoSession().getTipo_AutoDao().loadAll());
        ArrayAdapter<Tipo_Auto> adapterTipo = new ArrayAdapter<Tipo_Auto>(this, android.R.layout.simple_spinner_item, spTipo);
        spinTipo.setAdapter(adapterTipo);

        //Rubro
        Rubro rubro = new Rubro();
        rubro.setDescripcion("Seleccione un Rubro...");
        ArrayList<Rubro> spRubro = new ArrayList<Rubro>();
        spRubro.add(0, rubro);
        spRubro.addAll(Global.getDaoSession().getRubroDao().loadAll());
        ArrayAdapter<Rubro> adapterRubro = new ArrayAdapter<Rubro>(this, android.R.layout.simple_spinner_item, spRubro);
        spinRubro.setAdapter(adapterRubro);
    }

    public void buscar(View view){

    }
}
