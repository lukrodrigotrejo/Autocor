package com.lukrodrigotrejo.autocor.daogenerador;

import java.lang.Exception;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.lukrodrigotrejo.autocor.db.greendao");

        Entity marca = schema.addEntity("Marca");
        marca.addStringProperty("Codigo").primaryKey();
        marca.addStringProperty("Descripcion");

        Entity tipo_auto = schema.addEntity("Tipo_Auto");
        tipo_auto.addLongProperty("Codigo").primaryKey();
        tipo_auto.addStringProperty("Marca");
        tipo_auto.addStringProperty("Descripcion");

        Entity rubro = schema.addEntity("Rubro");
        rubro.addLongProperty("Codigo").primaryKey();
        rubro.addStringProperty("Descripcion");

        Entity stock = schema.addEntity("Stock");
        stock.addStringProperty("Codigo").primaryKey();
        stock.addStringProperty("Marca");
        stock.addLongProperty("Tipo_Auto");
        stock.addLongProperty("Rubro");
        stock.addStringProperty("NroOriginal");
        stock.addStringProperty("Descripcion");
        stock.addDoubleProperty("Precio");

        Entity stock_grande = schema.addEntity("Stock_Grande");
        stock_grande.addStringProperty("Codigo").primaryKey();
        stock_grande.addStringProperty("Descripcion");

        new DaoGenerator().generateAll(schema, ".");
    }

}
