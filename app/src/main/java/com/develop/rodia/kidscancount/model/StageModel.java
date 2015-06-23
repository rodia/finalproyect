package com.develop.rodia.kidscancount.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.develop.rodia.kidscancount.data.KCCDbHelper;
import com.develop.rodia.kidscancount.data.ResultContract;

/**
 * Model for Stage
 */
public class StageModel {

    /**
     * Save record to table stage.
     * @param resultValues
     * @return
     */
    public long setDBStage(Context context, ContentValues resultValues) {
        SQLiteDatabase db = new KCCDbHelper(context).getWritableDatabase();

        long resultRowId = db.insert(ResultContract.StageEntry.TABLE_NAME, null, resultValues);
        return resultRowId;
    }

    /**
     * Save record to table resource.
     * @param context
     * @param resultValues
     * @return
     */
    public long setDBResource(Context context, ContentValues resultValues) {
        SQLiteDatabase db = new KCCDbHelper(context).getWritableDatabase();
        long resultRowId = db.insert(ResultContract.ResourceEntry.TABLE_NAME, null, resultValues);
        return resultRowId;
    }

    /**
     * Save element to stage.
     * @param name
     * @param grade
     * @param context
     * @return
     */
    public long saveStage(String name, String grade, Context context) {
        ContentValues resultValues = new ContentValues();
        resultValues.put(ResultContract.StageEntry.COLUMN_NAME, name);
        resultValues.put(ResultContract.StageEntry.COLUMN_MAX_GRADE, grade);

        return setDBStage(context, resultValues);
    }

    /**
     * Save Element to get resource.
     * @param values
     * @param stage_id
     * @param context
     * @return
     */
    public long saveResource(String[] values, long stage_id, Context context) {
        ContentValues resultValues = new ContentValues();
        resultValues.put(ResultContract.ResourceEntry.COLUMN_STAGE_ID, stage_id);

        resultValues.put(ResultContract.ResourceEntry.COLUMN_URL, values[0]);
        resultValues.put(ResultContract.ResourceEntry.COLUMN_LEVEL, values[1]);
        resultValues.put(ResultContract.ResourceEntry.COLUMN_TYPE, values[2]);

        return setDBResource(context, resultValues);
    }

    /**
     * Set matrix element to resources.
     * @param total_for_count
     * @param matrix
     * @param stage_id
     * @param context
     */
    public void bashSaveResource(int total_for_count, String[][] matrix, long stage_id, Context context) {
        for (int i = 0; i < total_for_count; i++) {
            String[] values = matrix[i];
            saveResource(values, stage_id, context);
        }
    }

    /**
     *
     * @param total_for_count
     * @return
     */
    public String [][] valuesForDefault(int total_for_count) {
        String[][] values = new String[total_for_count][3];
        for (int i = 0; i < total_for_count; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 2) {
                    values[i][j] = "0";
                } else {
                    values[i][j] = "";
                }
            }
        }
        return values;
    }
}
