package com.example.menaadel.zadtask.caching;

import android.provider.BaseColumns;

/**
 * Created by MenaAdel on 1/17/2018.
 */

public class TableData {
    public TableData(){}

    public static abstract class TableInfo implements BaseColumns {
        public static final String DATABASENAME = "repo";
        public static final String TABLENAME = "repository";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String OWNERhtml_url = "Ohtml_url";
        public static final String html_url = "html_url";
        public static final String full_name = "full_name";
        public static final String fork = "fork";
    }
}
