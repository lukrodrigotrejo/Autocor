package com.lukrodrigotrejo.autocor.catalogo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lukrodrigotrejo.autocor.R;
import com.lukrodrigotrejo.autocor.util.Constants;
import com.lukrodrigotrejo.autocor.adapter.*;
import com.lukrodrigotrejo.autocor.db.greendao.*;
import com.lukrodrigotrejo.autocor.db.manager.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Catalogo extends ActionBarActivity {
    private ArrayList<HashMap> list;
    int cant = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        Bundle b = getIntent().getExtras();
        final Long parRubro = b.getLong("rubro");
        final String parMarca = b.getString("marca");
        final Long parTipo = b.getLong("tipo");
        final String parCodigo = b.getString("codigo");

        Log.e("LOG CATALOGO", "parRubro - " + parRubro);
        Log.e("LOG CATALOGO", "parMarca - " + parMarca);
        Log.e("LOG CATALOGO", "parTipo - " + parTipo);
        Log.e("LOG CATALOGO", "parCodigo - " + parCodigo);

        ListView lv_productos = (ListView) findViewById(R.id.listView);
        populateTable(parRubro, parMarca, parTipo, parCodigo);
        ListViewAdapter adapter = new ListViewAdapter(this, list);
        lv_productos.setAdapter(adapter);
        lv_productos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                LinearLayout ll = (LinearLayout) view;
                TextView tv_codigo = (TextView) ll.getChildAt(0);

                Intent detailIntent = new Intent(Catalogo.this, DetalleProducto.class);
                detailIntent.putExtra("cod_stock", tv_codigo.getText().toString());
                startActivity(detailIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalogo, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.logo_autocor);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_gradient));
        actionBar.setTitle(" Autocor");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                if(Build.VERSION.SDK_INT >= 11) {
                    new DBConnectTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }else {
                    new DBConnectTask().execute("");
                }
                return true;
            case R.id.action_database:
                Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_filter:
                Intent filtros = new Intent(this, Filtros.class);
                startActivity(filtros);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateTable(Long parRubro, String parMarca, Long parTipo, String parCodigo){
        list = new ArrayList<HashMap>();

        if("".equals(parCodigo)){
            List<Stock> listStock = Global.getDaoSession().getStockDao().getbyFiltros(parRubro, parMarca, parTipo);

            if(listStock.size() != 0){
                for(Stock item : listStock){
                    HashMap temp = new HashMap();
                    temp.put(Constants.FIRST_COLUMN, item.getCodigo());
                    temp.put(Constants.SECOND_COLUMN, item.getDescripcion());
                    list.add(temp);
                }
            }
        }else{
            //TODO VERIFICAR PORQUE NO FUNCIONA EL FILTRO EN BASE AL PRODUCTO
            List<Stock_Grande> stocks = Global.getDaoSession().getStock_GrandeDao().getStocksByCodigo(parCodigo);

            if(stocks.size() != 0){
                for(Stock_Grande item : stocks){
                    HashMap temp = new HashMap();
                    temp.put(Constants.FIRST_COLUMN, item.getCodigo());
                    temp.put(Constants.SECOND_COLUMN, item.getDescripcion());
                    list.add(temp);
                }
            }
        }
    }

    private class DBConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.e("SINCRONIZANDO", "Se comenzo a sincronizar");
            String result = ("");
            Connection con;
            Statement st;
            //Connect to databse
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection("jdbc:mysql://190.228.29.57/autocor_dev", "autocor_user", "v3r6c2t4");
                st = con.createStatement();
            }catch (Exception e){
                st = null;
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","system get connection");
            }
            //Populate "Marca"
            try {
                if (st != null) {
                    ResultSet rs = st.executeQuery("select MAR_CODIGO, MAR_DESCRIPCION from MARCA;");
                    while (rs.next()) {
                        Marca marca = new Marca(rs.getString(1), rs.getString(2));
                        Global.getDaoSession().getMarcaDao().insertOrReplace(marca);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","Error on populate Marca");
            }
            //Populate "Rubro"
            try {
                if (st != null) {
                    ResultSet rs = st.executeQuery("select RUB_CODIGO, RUB_DESCRIPCION from RUBRO;");
                    while (rs.next()) {
                        Rubro rubro = new Rubro(rs.getLong(1), rs.getString(2));
                        Global.getDaoSession().getRubroDao().insertOrReplace(rubro);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","Error on populate Rubro");
            }
            //Populate "Tipo_Auto"
            try {
                if (st != null) {
                    ResultSet rs = st.executeQuery("select TIP_CODIGO, MAR_CODIGO, TIP_DESCRIPCION from TIPO_AUTO;");
                    while (rs.next()) {
                        Tipo_Auto tipo_auto = new Tipo_Auto(rs.getLong(1), rs.getString(2), rs.getString(3));
                        Global.getDaoSession().getTipo_AutoDao().insertOrReplace(tipo_auto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","Error on populate TipoAuto");
            }
            //Populate "Stock"
            try {
                if (st != null) {
                    ResultSet rs = st.executeQuery("select CODPIEZA, MAR_CODIGO, TIP_CODIGO, RUB_CODIGO, NROORIGINAL, DESCRIP, PRECIO from STOCK LIMIT 300;");
                    while (rs.next()) {
                        Stock stock = new Stock(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getLong(4), rs.getString(5), rs.getString(6), rs.getDouble(7));
                        Global.getDaoSession().getStockDao().insertOrReplace(stock);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","Error on populate TipoAuto");
            }
            //Populate "Stock_grande"
            try {
                if (st != null) {
                    ResultSet rs = st.executeQuery("select codpieza, descri from stock_grande LIMIT 300;");
                    while (rs.next()) {
                        Stock_Grande stock_grande = new Stock_Grande(rs.getString(1), rs.getString(2));
                        Global.getDaoSession().getStock_GrandeDao().insertOrReplace(stock_grande);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","Error on populate Stock_grande");
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Result", result);
            Log.e("DDBB", "Base de datos sincronizada");
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
