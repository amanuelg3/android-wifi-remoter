package com.remoter.mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class X_DataBase {
	private SqliteHelper sqliteHelper;
	
	public boolean createDataBase(Context context,String name,CursorFactory factory,int version){
		sqliteHelper = new SqliteHelper(context, name, null, version);
		return false;
	}
	
	
	
	
	
	
	
	
	class SqliteHelper extends SQLiteOpenHelper { // 创建SQLITE的数据库助手

		public SqliteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "create table "
//					+ MyContentProviderMetaData.USERS_TABLE_NAME + "("
//					+ MyContentProviderMetaData.UserTableMetaData._ID
//					+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
//					+ MyContentProviderMetaData.UserTableMetaData.USER_NAME
					+ " varchar(20));";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}
