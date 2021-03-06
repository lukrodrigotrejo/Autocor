package com.lukrodrigotrejo.autocor.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lukrodrigotrejo.autocor.db.greendao.Marca;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MARCA".
*/
public class MarcaDao extends AbstractDao<Marca, String> {

    public static final String TABLENAME = "MARCA";

    /**
     * Properties of entity Marca.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Codigo = new Property(0, String.class, "Codigo", true, "CODIGO");
        public final static Property Descripcion = new Property(1, String.class, "Descripcion", false, "DESCRIPCION");
    };


    public MarcaDao(DaoConfig config) {
        super(config);
    }
    
    public MarcaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MARCA\" (" + //
                "\"CODIGO\" TEXT PRIMARY KEY NOT NULL ," + // 0: Codigo
                "\"DESCRIPCION\" TEXT);"); // 1: Descripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MARCA\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Marca entity) {
        stmt.clearBindings();
 
        String Codigo = entity.getCodigo();
        if (Codigo != null) {
            stmt.bindString(1, Codigo);
        }
 
        String Descripcion = entity.getDescripcion();
        if (Descripcion != null) {
            stmt.bindString(2, Descripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Marca readEntity(Cursor cursor, int offset) {
        Marca entity = new Marca( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Codigo
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // Descripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Marca entity, int offset) {
        entity.setCodigo(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDescripcion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Marca entity, long rowId) {
        return entity.getCodigo();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Marca entity) {
        if(entity != null) {
            return entity.getCodigo();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
