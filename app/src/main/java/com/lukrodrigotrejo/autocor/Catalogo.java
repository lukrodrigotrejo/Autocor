package com.lukrodrigotrejo.autocor;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lukrodrigotrejo.autocor.db.greendao.DaoMaster;
import com.lukrodrigotrejo.autocor.db.greendao.DaoSession;
import com.lukrodrigotrejo.autocor.db.greendao.Marca;
import com.lukrodrigotrejo.autocor.db.greendao.MarcaDao;
import com.lukrodrigotrejo.autocor.db.manager.AndroidDatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


public class Catalogo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalogo, menu);
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
    }

    public void openDB(View view){
        Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }
    public void updateDB(View view){
        new DBConnectTask().execute("");
    }

    private class DBConnectTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = ("");
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://190.228.29.57/autocor_dev", "autocor_user", "v3r6c2t4");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from MARCA LIMIT 5;");
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    Marca marca = new Marca(rs.getString(1), rs.getString(2));

                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "dbautocor_android", null);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(db);
                    DaoSession daoSession = daoMaster.newSession();
                    daoSession.getMarcaDao().insertOrReplace(marca);

                    result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                    result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                }

            } catch (Exception e) {
                e.printStackTrace();
                result = ("Test Error:"+e.toString());
                Log.w("Android-system","system get connection");
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Result", result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
