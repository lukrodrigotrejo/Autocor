package com.lukrodrigotrejo.autocor.catalogo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lukrodrigotrejo.autocor.R;
import com.lukrodrigotrejo.autocor.db.greendao.Stock;
import com.lukrodrigotrejo.autocor.db.manager.Global;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetalleProducto extends ActionBarActivity {
    ImageView imageView;
    TextView tv_codigo, tv_marca, tv_tipoAuto, tv_rubro, tv_nroOriginal, tv_descripcion, tv_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        tv_codigo = (TextView) findViewById(R.id.tv_depro_codigo);
        tv_marca = (TextView) findViewById(R.id.tv_depro_marca);
        tv_tipoAuto = (TextView) findViewById(R.id.tv_depro_tipoauto);
        tv_rubro = (TextView) findViewById(R.id.tv_depro_rubro);
        tv_nroOriginal = (TextView) findViewById(R.id.tv_depro_nrooriginal);
        tv_descripcion = (TextView) findViewById(R.id.tv_depro_descripcion);
        tv_precio = (TextView) findViewById(R.id.tv_depro_precio);

        Bundle b = getIntent().getExtras();
        String value = b.getString("cod_stock");

        Stock stock = Global.getDaoSession().getStockDao().getByCodigo(value);
        tv_codigo.setText(stock.getCodigo());
        tv_descripcion.setText(stock.getDescripcion());
        tv_precio.setText(stock.getPrecio().toString());
        tv_nroOriginal.setText(stock.getNroOriginal());
        tv_marca.setText(Global.getDaoSession().getMarcaDao().getByCodigo(stock.getMarca()).getDescripcion());
        tv_tipoAuto.setText(Global.getDaoSession().getTipo_AutoDao().getByCodigo(stock.getTipo_Auto().toString()).getDescripcion());
        try {
            tv_rubro.setText(Global.getDaoSession().getRubroDao().getByCodigo(stock.getRubro().toString()).getDescripcion());
        }catch(Exception e){
            tv_rubro.setText("");
        }
        imageView = (ImageView) findViewById(R.id.iv_producto);
        String rubro = Global.getDaoSession().getStockDao().getByCodigo(value).getRubro().toString();
        rubro = String.format("%03d", Integer.parseInt(rubro));
        String strUrl = "http://www.autocor.com.ar/usuarios/img_productos/" + rubro + "/" + value + ".jpg";
        URL imgUrl = null;
        try {
            imgUrl = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new DownloadImageTask().execute(imgUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_producto, menu);
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

    private class DownloadImageTask extends AsyncTask<URL, Void, Drawable>{

        @Override
        protected Drawable doInBackground(URL... imgURL) {
            Bitmap bitmap;
            Drawable d;
            try {
                bitmap = BitmapFactory.decodeStream(imgURL[0].openConnection().getInputStream());
                d =(Drawable)new BitmapDrawable(bitmap);
                return d;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


    @Override
        protected void onPostExecute(Drawable imagen) {
            Log.e("Detalle: ", "Imagen Cargada");
            imageView.setImageDrawable(imagen);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
