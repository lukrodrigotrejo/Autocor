package com.lukrodrigotrejo.autocor.catalogo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lukrodrigotrejo.autocor.R;
import com.lukrodrigotrejo.autocor.db.greendao.Marca;
import com.lukrodrigotrejo.autocor.db.greendao.Rubro;
import com.lukrodrigotrejo.autocor.db.greendao.Tipo_Auto;
import com.lukrodrigotrejo.autocor.db.manager.Global;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Filtros extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
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
        ArrayList<String> spMarcas = new ArrayList<String>();
        List<Marca> marcas = Global.getDaoSession().getMarcaDao().loadAll();
        Iterator<Marca> itMarca = marcas.iterator();
        while (itMarca.hasNext()) {
            spMarcas.add(itMarca.next().getDescripcion());
        }
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spMarcas);
        Spinner spinMarca = (Spinner)findViewById(R.id.sp_depro_marca);
        spinMarca.setAdapter(adapterMarca);

        //Tipo
        ArrayList<String> spTipo = new ArrayList<String>();
        List<Tipo_Auto> tipos = Global.getDaoSession().getTipo_AutoDao().loadAll();
        Iterator<Tipo_Auto> itTipo = tipos.iterator();
        while (itTipo.hasNext()) {
            spTipo.add(itTipo.next().getDescripcion());
        }
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spTipo);
        Spinner spinTipo = (Spinner)findViewById(R.id.sp_depro_tipoAuto);
        spinTipo.setAdapter(adapterTipo);

        //Rubro
        ArrayList<String> spRubro = new ArrayList<String>();
        List<Rubro> rubros = Global.getDaoSession().getRubroDao().loadAll();
        Iterator<Rubro> itRubro = rubros.iterator();
        while (itRubro.hasNext()) {
            spRubro.add(itRubro.next().getDescripcion());
        }
        ArrayAdapter<String> adapterRubro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spRubro);
        Spinner spinRubro = (Spinner)findViewById(R.id.sp_depro_rubro);
        spinRubro.setAdapter(adapterRubro);
    }
}
