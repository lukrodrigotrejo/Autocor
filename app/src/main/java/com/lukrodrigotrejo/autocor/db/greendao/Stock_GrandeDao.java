package com.lukrodrigotrejo.autocor.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lukrodrigotrejo.autocor.db.greendao.Stock_Grande;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "STOCK__GRANDE".
*/
public class Stock_GrandeDao extends AbstractDao<Stock_Grande, Long> {

    public static final String TABLENAME = "STOCK__GRANDE";

    /**
     * Properties of entity Stock_Grande.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Codigo = new Property(1, String.class, "Codigo", false, "CODIGO");
        public final static Property Descripcion = new Property(2, String.class, "Descripcion", false, "DESCRIPCION");
    };


    public Stock_GrandeDao(DaoConfig config) {
        super(config);
    }
    
    public Stock_GrandeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"STOCK__GRANDE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CODIGO\" TEXT NOT NULL UNIQUE ," + // 1: Codigo
                "\"DESCRIPCION\" TEXT);"); // 2: Descripcion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"STOCK__GRANDE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Stock_Grande entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCodigo());
 
        String Descripcion = entity.getDescripcion();
        if (Descripcion != null) {
            stmt.bindString(3, Descripcion);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Stock_Grande readEntity(Cursor cursor, int offset) {
        Stock_Grande entity = new Stock_Grande( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // Codigo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // Descripcion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Stock_Grande entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCodigo(cursor.getString(offset + 1));
        entity.setDescripcion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Stock_Grande entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Stock_Grande entity) {
        if(entity != null) {
            return entity.getId();
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
