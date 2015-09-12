package com.lukrodrigotrejo.autocor.catalogo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lukrodrigotrejo.autocor.R;
import com.lukrodrigotrejo.autocor.db.greendao.Marca;
import com.lukrodrigotrejo.autocor.db.greendao.Rubro;
import com.lukrodrigotrejo.autocor.db.greendao.StockDao;
import com.lukrodrigotrejo.autocor.db.greendao.Tipo_Auto;
import com.lukrodrigotrejo.autocor.db.manager.Global;

import java.util.ArrayList;
import java.util.List;

public class Filtros extends ActionBarActivity {
    Spinner spinMarca, spinTipo, spinRubro;
    EditText txtCodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
        spinMarca = (Spinner)findViewById(R.id.sp_fil_marca);
        spinTipo = (Spinner)findViewById(R.id.sp_fil_tipoAuto);
        spinRubro = (Spinner)findViewById(R.id.sp_fil_rubro);
        txtCodigo = (EditText)findViewById(R.id.tv_fil_codigo);
        populateSpinners();
        setListeners();
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
        if(validateFiltros()) {
            Intent catalogo = new Intent(Filtros.this, Catalogo.class);
            Long parRubro = ((Rubro) spinRubro.getSelectedItem()).getCodigo();
            String parMarca = ((Marca) spinMarca.getSelectedItem()).getCodigo();
            Long parTipo = ((Tipo_Auto) spinTipo.getSelectedItem()).getCodigo();
            String parCodigo = txtCodigo.getText().toString();

            catalogo.putExtra("rubro", parRubro);
            catalogo.putExtra("marca", parMarca);
            catalogo.putExtra("tipo", parTipo);
            catalogo.putExtra("codigo", parCodigo);
            startActivity(catalogo);
        }
    }

    public boolean validateFiltros(){
        if(((Rubro)spinRubro.getSelectedItem()).getCodigo() == null && txtCodigo.getText().toString().length() == 0){
            TextView errorText = (TextView)spinRubro.getSelectedView();
            errorText.setError("Seleccione un Rubro");
            errorText.setTextColor(Color.RED);
            return false;
        }else{
            return true;
        }
    }

    public void limpiarCampos(View view){
        spinRubro.setSelection(0);
        spinMarca.setSelection(0);
        spinTipo.setSelection(0);
        txtCodigo.setText("");
    }

    public void setListeners(){
        spinRubro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO AGREGAR LOGICA PARA CAMBIAR LAS MARCAS SEGUN EL RUBRO
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Marca marca = (Marca) spinMarca.getSelectedItem();
                if(marca.getCodigo() != null) {
                    List<Tipo_Auto> tipo_autos = Global.getDaoSession().getTipo_AutoDao().getTiposByMarca(marca);

                    Tipo_Auto tipo_auto = new Tipo_Auto();
                    tipo_auto.setDescripcion("Seleccione un Tipo...");
                    ArrayList<Tipo_Auto> spTipo = new ArrayList<Tipo_Auto>();
                    spTipo.add(0, tipo_auto);
                    spTipo.addAll(tipo_autos);
                    ArrayAdapter<Tipo_Auto> adapterTipo = new ArrayAdapter<Tipo_Auto>(Filtros.this, android.R.layout.simple_spinner_item, spTipo);
                    spinTipo.setAdapter(adapterTipo);

                    Log.e("Selected item : ", marca.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}
