package com.weather.byhieg.easyweather;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    private static void addProvince(Schema schema) {
        Entity entity = schema.addEntity("Province");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("provinceName").notNull();
    }

    private static void addCity(Schema schema) {
        Entity entity = schema.addEntity("City");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("citynName").notNull();
        entity.addStringProperty("provinceName").notNull();
        entity.addStringProperty("love").notNull();
    }

    private static void addLoveCity(Schema schema) {
        Entity entity = schema.addEntity("LoveCity");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("citynName");
        entity.addIntProperty("order");
    }

    private static void addCityWeather(Schema schema) {
        Entity entity = schema.addEntity("CityWeather");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("cityName");
        entity.addByteArrayProperty("weatherBean");
        entity.addDateProperty("updateTime");
    }

    private static void addLoveViewSpot(Schema schema) {
        Entity entity = schema.addEntity("LoveViewSpot");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("name");
        entity.addIntProperty("order");
    }

    private static void addViewSpotWeather(Schema schema) {
        Entity entity = schema.addEntity("ViewSpotWeather");
        entity.addIdProperty().autoincrement();
        entity.addByteArrayProperty("weatherBean");
        entity.addDateProperty("updateTime");
        entity.addStringProperty("name");
    }

    private static void addDirect(Schema schema) {
        Entity entity = schema.addEntity("Direct");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("provinceName").notNull();
    }

    private static void addViewSpot(Schema schema) {
        Entity entity = schema.addEntity("ViewSpot");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("directName").notNull();
        entity.addStringProperty("viewSpotName").notNull();
        entity.addStringProperty("viewSpotID").notNull();
        entity.addStringProperty("love");
    }

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.weather.byhieg.easyweather.Db");
        schema.setDefaultJavaPackageDao("com.weather.byhieg.easyweather.Db");
        addProvince(schema);
        addCity(schema);
        addCityWeather(schema);
        addDirect(schema);
        addLoveCity(schema);
        addViewSpot(schema);
        addViewSpotWeather(schema);
        addLoveViewSpot(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
    }

}
