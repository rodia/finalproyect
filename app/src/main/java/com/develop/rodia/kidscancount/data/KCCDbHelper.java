package com.develop.rodia.kidscancount.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class crate and update the elements to process to create activities and saved the
 * scores and grades
 */
public class KCCDbHelper extends SQLiteOpenHelper {
    /**
     * @var actual version to kcc app.
     */
    private static final int DATABASE_VERSION = 4;

    /**
     * @var database name
     */
    static final String DATABASE_NAME = "kcc.db";

    /**
     *
     * @param context
     */
    public KCCDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method create all tables to save the activities process.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createBeneficiary(db);
        createProgress(db);
        createAward(db);
        createAttempt(db);
        createStage(db);
        createResource(db);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createBeneficiary(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.BeneficiaryEntry.TABLE_NAME + " (" +
                ResultContract.BeneficiaryEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.BeneficiaryEntry.COLUMN_NAME + " VARCHAR(100) NOT NULL," +
                ResultContract.BeneficiaryEntry.COLUMN_GENDER + " TINYINT UNSIGNED NOT NULL DEFAULT 0," +
                ResultContract.BeneficiaryEntry.COLUMN_OLDYEARS + " TINYINT UNSIGNED NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createProgress(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.ProgressEntry.TABLE_NAME + " (" +
                ResultContract.ProgressEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.ProgressEntry.COLUMN_BENEFICIARY_ID + " INT UNSIGNED NOT NULL," +
                ResultContract.ProgressEntry.COLUMN_GRADE + " FLOAT NULL DEFAULT 0," +
                ResultContract.ProgressEntry.COLUMN_HISTORIC + " TEXT NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createAward(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.AwardEntry.TABLE_NAME + " (" +
                ResultContract.AwardEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.AwardEntry.COLUMN_BENEFICIARY_ID + " INT UNSIGNED NOT NULL," +
                ResultContract.AwardEntry.COLUMN_NAME + " VARCHAR(45) NOT NULL," +
                ResultContract.AwardEntry.COLUMN_AWARD + " VARCHAR(45) NULL," +
                ResultContract.AwardEntry.COLUMN_DESCRIPTION + " TEXT NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createAttempt(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.AttemptEntry.TABLE_NAME + " (" +
                ResultContract.AttemptEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.AttemptEntry.COLUMN_STAGE_ID + " INT UNSIGNED NOT NULL," +
                ResultContract.AttemptEntry.COLUMN_BENEFICIARY_ID + " INT UNSIGNED NOT NULL," +
                ResultContract.AttemptEntry.COLUMN_DATE + " DATETIME NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createStage(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.StageEntry.TABLE_NAME + " (" +
                ResultContract.StageEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.StageEntry.COLUMN_NAME + " VARCHAR(45) NULL," +
                ResultContract.StageEntry.COLUMN_MAX_GRADE + " FLOAT NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     *
     * @param db
     * @version 0.1
     */
    private void createResource(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResultContract.ResourceEntry.TABLE_NAME + " (" +
                ResultContract.ResourceEntry._ID + " INT UNSIGNED PRIMARY KEY," +
                ResultContract.ResourceEntry.COLUMN_STAGE_ID + " INT UNSIGNED NOT NULL," +
                ResultContract.ResourceEntry.COLUMN_URL + " VARCHAR(255) NULL," +
                ResultContract.ResourceEntry.COLUMN_LEVEL + " FLOAT NULL," +
                ResultContract.ResourceEntry.COLUMN_TYPE + " TINYINT UNSIGNED NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     * @todo This method need change to manage updates.
     *
     * @version 0.1
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.BeneficiaryEntry.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.ProgressEntry.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.AwardEntry.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.AttemptEntry.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.StageEntry.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + ResultContract.ResourceEntry.TABLE_NAME);
        onCreate(db);
    }
}
