package com.develop.rodia.kidscancount.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * @version 0.1
 */
public class ResultContract {

    public static long normalizeDate(long startDate) {
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);

        return time.setJulianDay(julianDay);
    }

    /**
     * @version 0.1
     */
    public static final class BeneficiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "beneficiary";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_OLDYEARS = "oldyears";
    }

    /**
     * @version 0.1
     */
    public static final class ProgressEntry implements BaseColumns {
        public static final String TABLE_NAME = "progress";

        public static final String COLUMN_BENEFICIARY_ID = "beneficiary_id";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_HISTORIC = "historic";
    }

    /**
     * @version 0.1
     */
    public static final class AwardEntry implements BaseColumns {
        public static final String TABLE_NAME = "award";

        public static final String COLUMN_BENEFICIARY_ID = "beneficiary_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AWARD = "award";
        public static final String COLUMN_DESCRIPTION = "description";
    }

    /**
     * @version 0.1
     */
    public static final class AttemptEntry implements BaseColumns {
        public static final String TABLE_NAME = "attempt";

        public static final String COLUMN_STAGE_ID = "stage_id";
        public static final String COLUMN_BENEFICIARY_ID = "beneficiary_id";
        public static final String COLUMN_DATE = "date";
    }

    /**
     * @version 0.1
     */
    public static final class StageEntry implements BaseColumns {
        public static final String TABLE_NAME = "stage";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MAX_GRADE = "max_grade";
    }

    /**
     * @version 0.1
     */
    public static final class ResourceEntry implements BaseColumns {
        public static final String TABLE_NAME = "resource";

        public static final String COLUMN_STAGE_ID = "stage_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_TYPE = "type";
    }
}
