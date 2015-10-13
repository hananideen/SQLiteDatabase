package com.sql.sqliteexample;

/**
 * Created by Hananideen on 12/8/2015.
 */
public class MyDbContractConstants {

    public MyDbContractConstants() {
        //empty constructor to prevent accidental instantiation
    }

    public static abstract class MyConstants{
        public static final String DATABASE_NAME = "myContacts_database";
        public static final String DATABASE_TABLE = "MyContacts_table";
        public static final int DATABASE_VERSION = 1;
        public static final String KEY_ID = "_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
    }

}

