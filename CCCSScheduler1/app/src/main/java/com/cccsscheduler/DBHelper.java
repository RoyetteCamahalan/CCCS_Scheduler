package com.cccsscheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.Getters_Setters.Group_Events_Adapter;
import com.Getters_Setters.Notif_getset;
import com.Getters_Setters.events_adapter;
import com.Getters_Setters.events_adapter2;
import com.Getters_Setters.groups_adapter;
import com.Getters_Setters.member_getset;
import com.Getters_Setters.users_getset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "SCHEDULER";
    private static final int VERSION = 1;

    public static final String USERS_TABLE_NAME = "USERS";
    public static final String USERS_ID = "user_id";
    public static final String USERS_FNAME = "fname";
    public static final String USERS_MNAME = "mname";
    public static final String USERS_LNAME = "lname";
    public static final String USERS_GENDER = "gender";
    public static final String USERS_BDATE = "bdate";
    public static final String USERS_ADDRESS = "address";
    public static final String USERS_CONTACT = "contactno";
    public static final String USERS_UNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_GMAIL = "gmail";

    public static final String GROUPS_TABLE_NAME = "GROUPS";
    public static final String GROUPS_ID = "group_id";
    public static final String GROUPS_NAME = "group_name";
    public static final String GROUPS_DESCRIPTION = "group_description";
    public static final String GROUPS_LEADERID = "leader_id";
    public static final String GROUPS_CURLEADERID = "current_leader_id";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public static final String GROUP_MEMBER_TABLE_NAME = "GROUP_MEMBER";

    public static final String P_EVENTS_TABLE_NAME = "PERSONAL_EVENTS";
    public static final String EVENTS_ID = "event_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String EVENTS_DATE = "date";
    public static final String EVENTS_TIME = "time";

    public static final String NOTES_TABLE_NAME = "NOTES";
    public static final String NOTES_ID = "note_id";

    public static final String G_EVENTS_TABLE_NAME = "GROUP_EVENTS";

    public static final String MEMBERSHIP_NOTIFICATION = "NOTIFICATION";
    public static final String NOTIF_ID = "notification_id";
    public static final String NOTIF_DATE = "notification_date";
    public static final String STATUS = "status";

    public static final String POST_NOTIFICATION = "POST_NOTIFICATION";

    public static final String ACCEPTANCE_POST_NOTIFICATION = "ACCEPTANCE_POST_NOTIFICATION";

    public static final String MEMBERSHIP_REQUEST = "MEMBERSHIP_REQUEST";
    public static final String LEADER_NAME = "LEADER_NAME";

    public static final String REQUEST_MEMBERSHIP_NOTIFICATION = "REQUEST_MEMBERSHIP_NOTIFICATION";

    public static final String SERVER_ID = "server_id";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                USERS_TABLE_NAME,
                USERS_ID,

                USERS_FNAME,
                USERS_MNAME,
                USERS_LNAME,
                USERS_BDATE,
                USERS_GENDER,

                USERS_ADDRESS,
                USERS_CONTACT,

                USERS_UNAME,
                USERS_PASSWORD,
                USERS_EMAIL,

                USERS_GMAIL,
                CREATED_AT,
                UPDATED_AT);
        db.execSQL(sql);
        String sql2 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY , %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT)",
                GROUPS_TABLE_NAME,
                GROUPS_ID,
                GROUPS_NAME,
                GROUPS_DESCRIPTION,
                GROUPS_LEADERID,
                GROUPS_CURLEADERID,
                CREATED_AT,
                UPDATED_AT);
        db.execSQL(sql2);
        String sql3 = String.format("CREATE TABLE %s (%s INTEGER NOT NULL,  %s INTEGER  NOT NULL, %s TEXT, %s TEXT,PRIMARY KEY ( %s, %s))",
                GROUP_MEMBER_TABLE_NAME,
                GROUPS_ID,
                USERS_ID,
                CREATED_AT,
                UPDATED_AT,
                GROUPS_ID,
                USERS_ID);
        db.execSQL(sql3);
        String sql4 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,  %s TEXT, %s TEXT,%s INTEGER, %s TEXT, %s TEXT,%s INTEGER)",
                P_EVENTS_TABLE_NAME,
                EVENTS_ID,
                TITLE,
                DESCRIPTION,
                EVENTS_DATE,
                EVENTS_TIME,
                PRIORITY,
                CREATED_AT,
                UPDATED_AT,
                SERVER_ID);
        db.execSQL(sql4);
        String sql5 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT,  %s INTEGER)",
                NOTES_TABLE_NAME,
                NOTES_ID,
                TITLE,
                DESCRIPTION,
                CREATED_AT,
                UPDATED_AT,
                SERVER_ID);
        db.execSQL(sql5);
        String sql6 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY,  %s INTEGER,  %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT,%s INTEGER,%s INTEGER, %s TEXT, %s TEXT)",
                G_EVENTS_TABLE_NAME,
                EVENTS_ID,
                GROUPS_ID,
                USERS_ID,
                TITLE,
                DESCRIPTION,
                EVENTS_DATE,
                EVENTS_TIME,
                PRIORITY,
                STATUS,
                CREATED_AT,
                UPDATED_AT);
        db.execSQL(sql6);
        String sql7 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,  %s INTEGER, %s TEXT, %s TEXT)",
                MEMBERSHIP_NOTIFICATION,
                NOTIF_ID,
                GROUPS_ID,
                NOTIF_DATE,
                STATUS);
        db.execSQL(sql7);
        String sql8 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,  %s INTEGER,  %s INTEGER, %s TEXT, %s TEXT)",
                POST_NOTIFICATION,
                NOTIF_ID,
                GROUPS_ID,
                EVENTS_ID,
                NOTIF_DATE,
                STATUS);
        db.execSQL(sql8);
        String sql9 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,  %s INTEGER,  %s INTEGER,  %s TEXT)",
                ACCEPTANCE_POST_NOTIFICATION,
                NOTIF_ID,
                GROUPS_ID,
                EVENTS_ID,
                NOTIF_DATE);
        db.execSQL(sql9);
        String sql10 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY,  %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                MEMBERSHIP_REQUEST,
                NOTIF_ID,
                GROUPS_ID,
                GROUPS_NAME,
                GROUPS_DESCRIPTION,
                LEADER_NAME,
                NOTIF_DATE,
                STATUS);
        db.execSQL(sql10);
        String sql11 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,  %s INTEGER,  %s INTEGER, %s TEXT, %s TEXT)",
                REQUEST_MEMBERSHIP_NOTIFICATION,
                NOTIF_ID,
                GROUPS_ID,
                USERS_ID,
                NOTIF_DATE,
                STATUS);
        db.execSQL(sql11);
    }


    public void clearDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + GROUP_MEMBER_TABLE_NAME);
        db.execSQL("delete from " + P_EVENTS_TABLE_NAME);
        db.execSQL("delete from " + GROUP_MEMBER_TABLE_NAME);
        db.execSQL("delete from " + G_EVENTS_TABLE_NAME);
        db.execSQL("delete from " + MEMBERSHIP_REQUEST);
        db.execSQL("delete from " + GROUPS_TABLE_NAME);
        db.close();

    }

    public ArrayList<groups_adapter> getgroups() {

        ArrayList<groups_adapter> groups = new ArrayList<groups_adapter>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + GROUPS_TABLE_NAME;
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            groups_adapter group = new groups_adapter();

            int id = cur.getInt(0); //this is for id
            String name = cur.getString(1); //task name
            String description = cur.getString(2); //description
            int leaderid = cur.getInt(3); //date deadline
            String created_at = cur.getString(4);
            String updated_at = cur.getString(5);

            group.setId(id);
            group.setName(name);
            group.setDescription(description);
            group.setleaderid(leaderid);
            group.setcreated_at(created_at);
            group.setupdated_at(updated_at);

            groups.add(group);
        }

        cur.close();
        db.close();

        return groups;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (newVersion > oldVersion) {
            // db.execSQL("ALTER TABLE "+USERS_TABLE_NAME+" ADD COLUMN "+UPDATED_AT+" TEXT");
        }
    }

    public boolean addNewGroup(String groupname, String Description, int userid, int server_id) {

        SQLiteDatabase db = getWritableDatabase();
        boolean ret = false;
        ContentValues values = new ContentValues();
        values.put(GROUPS_ID, server_id);
        values.put(GROUPS_NAME, groupname);
        values.put(GROUPS_DESCRIPTION, Description);
        values.put(GROUPS_LEADERID, userid);
        values.put(GROUPS_CURLEADERID, userid);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        values.put(CREATED_AT, formattedDate);
        long rowID = db.insert(GROUPS_TABLE_NAME, null, values);
        ret = rowID > 0;
        return ret;

    }

    public void update_group(String groupname, String Description, int userid, int server_id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUPS_NAME, groupname);
        values.put(GROUPS_DESCRIPTION, Description);
        String id = server_id + "";

        String whereArgs[] = new String[]{id};

        db.update(GROUPS_TABLE_NAME, values, GROUPS_ID + "=?", whereArgs);
    }

    public int getGroupLeader(int group_id) {
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT " + GROUPS_LEADERID + " FROM " + GROUPS_TABLE_NAME + " WHERE " + GROUPS_ID + "=" + String.valueOf(group_id);
        Cursor cur = db.rawQuery(sql, null);
        int id = 0;
        while (cur.moveToNext()) {
            id = cur.getInt(0);

        }
        cur.close();
        db.close();
        return id;


    }

    public long addNewEvent(String event_name, String event_Description, String date_deadline, String time_deadline,String created_At,String updated_at, int priority, int server_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, event_name);
        values.put(DESCRIPTION, event_Description);
        values.put(EVENTS_DATE, date_deadline);
        values.put(EVENTS_TIME, time_deadline);
        values.put(CREATED_AT, created_At);
        values.put(CREATED_AT, updated_at);
        values.put(PRIORITY, priority);
        values.put(SERVER_ID, server_id);
        long rowID = db.insert(P_EVENTS_TABLE_NAME, null, values);
        return rowID;
    }

    public ArrayList<events_adapter> getevents() {

        ArrayList<events_adapter> events = new ArrayList<events_adapter>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + P_EVENTS_TABLE_NAME;
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            events_adapter event = new events_adapter();

            int id = cur.getInt(0); //this is for id
            String name = cur.getString(1); //task name
            String description = cur.getString(2); //description
            String date_deadline = cur.getString(3); //date deadline
            String time_deadline = cur.getString(4);
            int event_priority = cur.getInt(5);
            String created_at = cur.getString(6);
            String updated_at = cur.getString(7);

            event.setId(id);
            event.setName(name);
            event.setDescription(description);
            event.setdate_deadline(date_deadline);
            event.settime_deadline(time_deadline);
            event.setpriority(event_priority);
            event.setcreated_at(created_at);
            event.setupdated_at(updated_at);

            events.add(event);
        }

        cur.close();
        db.close();

        return events;
    }

    public boolean deleteEvent(int id) {

        boolean ret = false;

        SQLiteDatabase db = getWritableDatabase();

        String whereArgs[] = new String[]{id + ""};

        int rowsDeleted = db.delete(P_EVENTS_TABLE_NAME, EVENTS_ID + "=?", whereArgs);
        ret = rowsDeleted > 0;

        db.close();

        return ret;

    }

    public boolean updateTask(events_adapter event) {

        boolean ret = false;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, event.getName());
        values.put(DESCRIPTION, event.getDescription());
        values.put(EVENTS_DATE, event.getdate_deadline());
        values.put(EVENTS_TIME, event.gettime_deadline());
        values.put(PRIORITY, event.getpriority());
        values.put(UPDATED_AT, event.getupdated_at());
        String id = event.getId() + "";

        String whereArgs[] = new String[]{id};

        int rowsAffected = db.update(P_EVENTS_TABLE_NAME, values, EVENTS_ID + "=?", whereArgs);

        ret = rowsAffected > 0;

        db.close();

        return ret;
    }

    public boolean sign_up(String fname, String mname, String lname, String gender, String bdate,
                           String address, String uname, String password, String email, String contactno, int server_id) {
        SQLiteDatabase db = getWritableDatabase();
        boolean ret = false;


        ContentValues values = new ContentValues();
        values.put(USERS_FNAME, fname);
        values.put(USERS_MNAME, mname);
        values.put(USERS_LNAME, lname);
        values.put(USERS_BDATE, bdate);
        values.put(USERS_GENDER, gender);

        values.put(USERS_ADDRESS, address);
        values.put(USERS_CONTACT, contactno);

        values.put(USERS_UNAME, uname);
        values.put(USERS_PASSWORD, password);
        values.put(USERS_EMAIL, email);
        values.put(USERS_GMAIL, "");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        values.put(CREATED_AT, formattedDate);
        values.put(UPDATED_AT, formattedDate);
        values.put(USERS_ID, server_id);
        long rowID = db.insert(USERS_TABLE_NAME, null, values);
        ret = rowID > 0;

        return ret;
    }

    public boolean updateUserinfo(String fname, String mname, String lname, String gender, String bdate,
                                  String address, String uname, String password, String email, String contactno, int server_id) {

        boolean ret = false;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERS_FNAME, fname);
        values.put(USERS_MNAME, mname);
        values.put(USERS_LNAME, lname);
        values.put(USERS_BDATE, bdate);
        values.put(USERS_GENDER, gender);

        values.put(USERS_ADDRESS, address);
        values.put(USERS_CONTACT, contactno);

        values.put(USERS_UNAME, uname);
        values.put(USERS_PASSWORD, password);
        values.put(USERS_EMAIL, email);
        values.put(USERS_GMAIL, "");
        String id = server_id + "";

        String whereArgs[] = new String[]{id};

        int rowsAffected = db.update(USERS_TABLE_NAME, values, USERS_ID + "=?", whereArgs);

        ret = rowsAffected > 0;

        db.close();

        return ret;
    }

    public int searchUser(String username, String password) {
        String sql = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE  username='" + username + "'  AND password = '" + password + "'  ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        int check = 0;

        while (cur.moveToNext()) {
            check = cur.getInt(0);
        }
        db.close();
        cur.close();

        return check;
    }

    public boolean searchUserID(int user_id) {
        String sql = "SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_ID + "=" + user_id;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        int checker = cur.getCount();
        db.close();
        cur.close();

        return checker > 0;
    }

    public long insertgroup_event(int groupid, int userid, String event_title, String event_Description, String date_deadline, String time_deadline, int priority, int status, int server_id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENTS_ID, server_id);
        values.put(GROUPS_ID, groupid);
        values.put(USERS_ID, userid);
        values.put(TITLE, event_title);
        values.put(DESCRIPTION, event_Description);
        values.put(EVENTS_DATE, date_deadline);
        values.put(EVENTS_TIME, time_deadline);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        values.put(PRIORITY, priority);
        values.put(STATUS, status);
        values.put(CREATED_AT, formattedDate);
        long rowID = db.insert(G_EVENTS_TABLE_NAME, null, values);
        //ADD NOTIFICATION HERE
        return rowID;

    }

    public boolean check_group_events(int event_id) {

        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + G_EVENTS_TABLE_NAME + " where " + EVENTS_ID + "=" + String.valueOf(event_id);
        Cursor cur = db.rawQuery(sql, null);
        int numrows = cur.getCount();
        cur.close();
        db.close();

        return numrows > 0;
    }

    public ArrayList<Group_Events_Adapter> get_group_events(int group_id) {

        ArrayList<Group_Events_Adapter> events = new ArrayList<Group_Events_Adapter>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = String.format("SELECT GE.event_id,U.user_id,GE.title,ge.description,ge.date,ge.time,ge.created_at,ge.updated_at,U.fname ||' '||U.mname||' '||U.lname user FROM GROUPS G INNER JOIN GROUP_EVENTS GE ON GE.group_id=G.group_id INNER JOIN USERS U ON U.user_id=GE.user_id where G.group_id=%s and GE.status!=2",
                group_id);
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            Group_Events_Adapter event = new Group_Events_Adapter();

            int id = cur.getInt(0); //this is for id
            int user_id=cur.getInt(1);
            String name = cur.getString(2); //task name
            String description = cur.getString(3); //description
            String date_deadline = cur.getString(4); //date deadline
            String time_deadline = cur.getString(5);
            String created_at = cur.getString(6);
            String updated_at = cur.getString(7);
            String postedby=cur.getString(8);
            event.setId(id);
            event.setuserId(user_id);
            event.setName(name);
            event.setDescription(description);
            event.setdate_deadline(date_deadline);
            event.settime_deadline(time_deadline);
            event.setcreated_at(created_at);
            event.setupdated_at(updated_at);
            event.setpostedby(postedby);
            events.add(event);
        }
        return events;
    }

    public boolean deleteGroup_Event(int id) {

        boolean ret = false;

        SQLiteDatabase db = getWritableDatabase();

        String whereArgs[] = new String[]{id + ""};

        int rowsDeleted = db.delete(G_EVENTS_TABLE_NAME, EVENTS_ID + "=?", whereArgs);
        ret = rowsDeleted > 0;

        db.close();

        return ret;

    }

    public boolean updateGroup_event(Group_Events_Adapter event, int sender) {

        boolean ret = false;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, event.getName());
        values.put(DESCRIPTION, event.getDescription());
        values.put(EVENTS_DATE, event.getdate_deadline());
        values.put(EVENTS_TIME, event.gettime_deadline());
        values.put(PRIORITY, event.getpriority());
        if (sender == 1) {
            values.put(STATUS, event.getstatus());
        }
        String id = event.getId() + "";

        String whereArgs[] = new String[]{id};

        int rowsAffected = db.update(G_EVENTS_TABLE_NAME, values, EVENTS_ID + "=?", whereArgs);

        ret = rowsAffected > 0;

        db.close();

        return ret;
    }

    public ArrayList<String> getgroupinfo(int group_id) {
        ArrayList<String> group = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = String.format("SELECT g.%s,g.%s,u.%s ||' '||u.%s||' '||u.%s,us.%s||' '||us.%s||' '||us.%s,g.%s,g.%s FROM %s g inner join %s u on u.%s=g.%s inner join %s us on us.%s=g.%s where g.%s=%s",
                GROUPS_NAME,
                GROUPS_DESCRIPTION,
                USERS_FNAME,
                USERS_MNAME,
                USERS_LNAME,
                USERS_FNAME,
                USERS_MNAME,
                USERS_LNAME,
                CREATED_AT,
                UPDATED_AT,

                GROUPS_TABLE_NAME,
                USERS_TABLE_NAME,
                USERS_ID,
                GROUPS_LEADERID,
                USERS_TABLE_NAME,
                USERS_ID,
                GROUPS_CURLEADERID,
                GROUPS_ID,
                group_id);
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {
            group.add(cur.getString(0));
            group.add(cur.getString(1));
            group.add(cur.getString(2));
            group.add(cur.getString(3));
            group.add(cur.getString(4));
            group.add(cur.getString(5));
        }
        return group;

    }

    public ArrayList<users_getset> getusers() {

        ArrayList<users_getset> users = new ArrayList<users_getset>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT " + USERS_ID + ", " + USERS_FNAME + ", " + USERS_MNAME + "," + USERS_LNAME + "," + USERS_ADDRESS + " FROM " + USERS_TABLE_NAME;
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            users_getset user = new users_getset();

            int id = cur.getInt(0); //this is for id
            String fname = cur.getString(1); //task name
            String mname = cur.getString(2); //description
            String lname = cur.getString(3); //date deadline
            String address = cur.getString(4);

            user.setId(id);
            user.setName(fname, mname, lname);
            user.setAddress(address);

            users.add(user);
        }

        cur.close();
        db.close();

        return users;
    }


    public ArrayList<events_adapter2> geteventsforupload(int userid, Date lastupdate) {

        ArrayList<events_adapter2> events = new ArrayList<events_adapter2>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + P_EVENTS_TABLE_NAME;
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            events_adapter2 event = new events_adapter2();

            int id = cur.getInt(0); //this is for id
            String name = cur.getString(1); //task name
            String description = cur.getString(2); //description
            String date_deadline = cur.getString(3); //date deadline
            String time_deadline = cur.getString(4);
            int event_priority = cur.getInt(5);
            String created_at = cur.getString(6);
            String updated_at = cur.getString(7);
            int ser_id = cur.getInt(8);
            try {
                boolean update = false;
                if (updated_at != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date lupdate = dateFormat.parse(updated_at);
                    if (lupdate.after(lastupdate)) {
                        update = true;
                    }
                }

                event.setId(id);
                event.setName(name);
                event.setDescription(description);
                event.setdate_deadline(date_deadline);
                event.settime_deadline(time_deadline);
                event.setpriority(event_priority);
                event.setcreated_at(created_at);
                event.setupdated_at(updated_at);
                event.setserverid(ser_id);
                if (update || ser_id == 0) {
                    events.add(event);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }

        cur.close();
        db.close();

        return events;
    }

    public void updateserver_id(int table, int local_id, int serverid) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SERVER_ID, serverid);
        String id = local_id + "";

        String whereArgs[] = new String[]{id};
        switch (table) {
            case 1://event personal
                db.update(P_EVENTS_TABLE_NAME, values, EVENTS_ID + "=?", whereArgs);
                break;

            default:
                break;
        }

    }

    public boolean check_mrequest(int group_id) {

        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + MEMBERSHIP_REQUEST + " where " + GROUPS_ID + "=" + String.valueOf(group_id);
        Cursor cur = db.rawQuery(sql, null);
        int numrows = cur.getCount();
        cur.close();
        db.close();

        return numrows > 0;
    }

    public void manage_mrequest(int notification_id, int group_id, String gname, String gdesc, String leadername, String notifdate, int status, int action) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIF_ID, notification_id);
        values.put(GROUPS_ID, group_id);
        values.put(GROUPS_NAME, gname);
        values.put(GROUPS_DESCRIPTION, gdesc);
        values.put(LEADER_NAME, leadername);
        values.put(NOTIF_DATE, notifdate);
        values.put(STATUS, status);
        if (action == 1) {
            db.insert(MEMBERSHIP_REQUEST, null, values);
        } else {
            String id = group_id + "";

            String whereArgs[] = new String[]{id};

            db.update(MEMBERSHIP_REQUEST, values, GROUPS_ID + "=?", whereArgs);
        }

        db.close();


    }

    public int countnotif() {

        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + MEMBERSHIP_REQUEST + " where " + STATUS + "=0";
        Cursor cur = db.rawQuery(sql, null);
        int numrows = cur.getCount();
        cur.close();
        db.close();

        return numrows;
    }

    public ArrayList<Notif_getset> getnotifications() {

        ArrayList<Notif_getset> notifications = new ArrayList<Notif_getset>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT notification_id, group_id, group_name, group_description, LEADER_NAME, notification_date,status, 1 notiftype"+
                " FROM memBERSHIP_REQUEST where status=0 UNION ALL"+
                " select ge.event_id, u.user_id, g.group_name, g.group_description, (u.fname || u.mname || u.lname) as user, "+
                "ge.created_at notification_date, ge.status, 2 notiftype from GROUP_EVENTS ge inner join"+
                " USERS u on u.user_id=ge.user_id inner join GROUPS g on g.group_id=ge.group_id WHERE ge.status!=2 UNION ALL"+
                " select gm.user_id, g.group_id, g.group_name, g.group_description, (u.fname || u.mname || u.lname) as user,"+
                " gm.created_at notification_date, 0 status, 3 notiftype from GROUPS g inner join GrouP_MEMBER gm"+
                " on g.group_id=gm.group_id inner join USERS u on u.user_id=gm.user_id order by notification_date desc";
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            Notif_getset notifs = new Notif_getset();

            int id = cur.getInt(0); //this is for id
            int groupid = cur.getInt(1);
            String gname = cur.getString(2); //task name
            String gdescription = cur.getString(3); //description
            String leadername = cur.getString(4); //date deadline
            String notification_date = cur.getString(5);
            int Status = cur.getInt(6);
            int notiftype = cur.getInt(7);

            notifs.setId(id);
            notifs.setgroupid(groupid);
            notifs.setName(gname);
            notifs.setDescription(gdescription);
            notifs.setleadername(leadername);
            notifs.setnotifdate(notification_date);
            notifs.setstatus(Status);
            notifs.setnotiftype(notiftype);
            notifications.add(notifs);
        }

        cur.close();
        db.close();

        return notifications;
    }

    public void insert_member(int groupid, int userid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUPS_ID, groupid);
        values.put(USERS_ID, userid);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        values.put(CREATED_AT, formattedDate);
        db.insert(GROUP_MEMBER_TABLE_NAME, null, values);

    }

    public void updaterequeststatus(int notifid, int notiftype, int status) {
        SQLiteDatabase db = getWritableDatabase();
        if (notiftype == 1) {
            ContentValues values = new ContentValues();
            values.put(STATUS, status);
            String id = notifid + "";

            String whereArgs[] = new String[]{id};

            db.update(MEMBERSHIP_REQUEST, values, NOTIF_ID + "=?", whereArgs);
        }


    }
    public void updatepoststatus(int event_id, int status) {
        SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STATUS, status);
            String id = event_id + "";

            String whereArgs[] = new String[]{id};

            db.update(G_EVENTS_TABLE_NAME, values, EVENTS_ID + "=?", whereArgs);


    }
    public ArrayList<member_getset> get_group_member(int group_id) {

        ArrayList<member_getset> members = new ArrayList<member_getset>();
        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "select USERS.* FROM GROUP_MEMBER INNER JOIN USERS ON USERS.user_id=GROUP_MEMBER.user_id WHERE GROUP_MEMBER.group_id=" + String.valueOf(group_id) +
                " order by userS.fname,users.mname,users.lname";
        //String sql = "SELECT u.* FROM "+GROUP_MEMBER_TABLE_NAME +" gm INNER JOIN "+USERS_TABLE_NAME +" u on u."+USERS_ID+
        //	"=gm."+USERS_ID+" gm."+GROUPS_ID+"="+String.valueOf(group_id);
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {

            member_getset member = new member_getset();

            int id = cur.getInt(0); //this is for id
            String fname = cur.getString(1);
            String mname = cur.getString(2);
            String lname = cur.getString(3);
            String address = cur.getString(6);

            member.setId(id);
            member.setName(fname, mname, lname);
            member.setAddress(address);
            members.add(member);
        }

        cur.close();
        db.close();

        return members;
    }

    public boolean insertNote(String title, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(TITLE, title);
        val.put(DESCRIPTION, note);
        val.put(SERVER_ID, 0);

        long rowID = db.insert(NOTES_TABLE_NAME, null, val);

        db.close();
        return rowID > 0;
    }

    public ArrayList<HashMap<String, String>> listOfNotes() {
        ArrayList<HashMap<String, String>> array = new ArrayList();
        String sql = "SELECT * FROM " + NOTES_TABLE_NAME;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {
            HashMap<String, String> map = new HashMap();
            map.put(TITLE, cur.getString(cur.getColumnIndex(TITLE)));
            map.put(DESCRIPTION, cur.getString(cur.getColumnIndex(DESCRIPTION)));
            array.add(map);
        }

        db.close();
        cur.close();
        return array;
    }
    public boolean check_event(int event_id) {

        SQLiteDatabase db = getWritableDatabase();

        //fetch all tasks in the database
        String sql = "SELECT * FROM " + P_EVENTS_TABLE_NAME + " where " + SERVER_ID + "=" + String.valueOf(event_id);
        Cursor cur = db.rawQuery(sql, null);
        int numrows = cur.getCount();
        cur.close();
        db.close();

        return numrows > 0;
    }
    public ArrayList<HashMap<String, String>> get_event_info(int event_id) {
        ArrayList<HashMap<String, String>> array = new ArrayList();
        String sql = "SELECT * FROM " + G_EVENTS_TABLE_NAME+" where "+EVENTS_ID+"="+String.valueOf(event_id);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);

        while (cur.moveToNext()) {
            HashMap<String, String> map = new HashMap();
            map.put(TITLE, cur.getString(cur.getColumnIndex(TITLE)));
            map.put(DESCRIPTION, cur.getString(cur.getColumnIndex(DESCRIPTION)));
            map.put(EVENTS_DATE, cur.getString(cur.getColumnIndex(EVENTS_DATE)));
            map.put(EVENTS_TIME, cur.getString(cur.getColumnIndex(EVENTS_TIME)));
            map.put(PRIORITY, cur.getString(cur.getColumnIndex(PRIORITY)));
            array.add(map);
        }

        db.close();
        cur.close();
        return array;
    }

    public ArrayList<HashMap<String, String>> getInformation(int user_id) {
        ArrayList<HashMap<String, String>> array = new ArrayList();
        String sql = "SELECT " + USERS_FNAME + ","+ USERS_MNAME + ","+ USERS_LNAME + "," + USERS_BDATE + "," + USERS_GENDER + "," + USERS_ADDRESS + "," + USERS_CONTACT + "," + USERS_UNAME + "," + USERS_PASSWORD + "," + USERS_EMAIL + " FROM " + USERS_TABLE_NAME + " where " + USERS_ID + "=" + String.valueOf(user_id);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery(sql, null);
        while (cur.moveToNext()) {
            HashMap<String, String> map = new HashMap();
            map.put("FULLNAME", cur.getString(cur.getColumnIndex(USERS_FNAME)));
            map.put("USERS_FNAME", cur.getString(cur.getColumnIndex(USERS_FNAME)));
            map.put("USERS_MNAME", cur.getString(cur.getColumnIndex(USERS_MNAME)));
            map.put("USERS_LNAME", cur.getString(cur.getColumnIndex(USERS_LNAME)));
            map.put("USERS_BDATE", cur.getString(cur.getColumnIndex(USERS_BDATE)));
            map.put("USERS_GENDER", cur.getString(cur.getColumnIndex(USERS_GENDER)));
            map.put("USERS_ADDRESS", cur.getString(cur.getColumnIndex(USERS_ADDRESS)));
            map.put("USERS_CONTACT", cur.getString(cur.getColumnIndex(USERS_CONTACT)));
            map.put("USERS_UNAME", cur.getString(cur.getColumnIndex(USERS_UNAME)));
            map.put("USERS_PASSWORD", cur.getString(cur.getColumnIndex(USERS_PASSWORD)));
            map.put("USERS_EMAIL", cur.getString(cur.getColumnIndex(USERS_EMAIL)));
            array.add(map);
        }
        db.close();
        cur.close();
        return array;

    }

    public int getInformation2(int user_id) {
        String sql = "SELECT " + USERS_FNAME + ","+ USERS_MNAME + ","+ USERS_LNAME + "," + USERS_BDATE + "," + USERS_GENDER + "," + USERS_ADDRESS + "," + USERS_CONTACT + "," + USERS_UNAME + "," + USERS_PASSWORD + "," + USERS_EMAIL + " FROM " + USERS_TABLE_NAME + " WHERE " + USERS_ID + "=" + String.valueOf(user_id);
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;
        Cursor cur = db.rawQuery(sql, null);
        count = cur.getCount();
        db.close();
        cur.close();
        return count;
    }
}


