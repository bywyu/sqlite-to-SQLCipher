package com.drspaceboo.sqlite2sqlcipher;

import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * The Activity does everything, with a little help from
 * some functions.
 * 
 * @author Dr Boo <contact [at] drspaceboo [dot] com>
 */
public class SQLite2SQLCipher extends Activity
{
    /*
     * Please replace the following variables with the ones
     * relevant to your application
     */
	private static final String SQLITE_FILE = "text.sqlite";
	private static final String DB_NAME = "test.db";
	private static final String DB_PASSWORD = "testPassword";
	//Stop replacing here
	
	private static final String DEBUG_TAG = "SQLite2SQLCipher";
    
	private SQLiteDatabase database;
	private ProgressDialog progressDialog;
	
	/*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Loading the SQLCipher libraries
        SQLiteDatabase.loadLibs(this);
        
        //Preparing the database directories and file to be opened
        File databaseFile = getDatabasePath(DB_NAME);
        databaseFile.mkdirs();
        databaseFile.delete();
        
        //Opening or Creating the database with our specified password
        database = SQLiteDatabase.openOrCreateDatabase(databaseFile, DB_PASSWORD, null);
        
        //Making a progress dialog so we can see that the database is still being loaded
        progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Creating database");
		progressDialog.show();
		
		/*
		 * Creating the database from the .sqlite file. We do this in
		 * an AsyncTask so that we aren't locking the UI thread.
		 */
		new CreateDatabaseFromFileTask().execute();
    }
    
    /**
     * This function updates the String displayed in the application
     * to the success string once the AsyncTask completes
     */
    private void updateUIWithSuccess()
    {
    	runOnUiThread(new Runnable()
    	{
    	    public void run()
    	    {
    	    	((TextView)findViewById(R.id.hello_text)).setText(getResources().getString(R.string.success));
    	    }
    	});
    }
    
    /**
     * This AsyncTask loads the .sqlite file 
     * 
     * @author Dr Boo <contact [at] drspaceboo [dot] com>
     */
    private class CreateDatabaseFromFileTask extends AsyncTask<Void, Void, Void>
    {
		@Override
		protected Void doInBackground(Void... params)
		{
			StringBuilder statement = new StringBuilder();
			String line;
			int lineCount = 1;
			int statementCount = 1;
			BufferedReader reader = null;
			
			try
	    	{
				//Opening the .sqlite file from the Assets folder
		    	reader = new BufferedReader(new InputStreamReader(getAssets().open(SQLITE_FILE)));
		    	
		    	while((line = reader.readLine()) != null)
		    	{
		    		//A very handy line count log
		    		Log.d(DEBUG_TAG,"Reading line " + lineCount);
		    		if(line.length() > 1)
	    			{
			    		statement.append(line);
		    			
			    		//If this line is the end of the statement we run that statement
		    			if(line.matches(".*;$"))
		    			{
		    				//Getting the string from the String Builder
		    				String statementString = statement.toString();
		    				statement = new StringBuilder();
		    				
		    				//Logging the statement, this might help with debugging any problems you encounter
			    			Log.d(DEBUG_TAG,"Statement #" + statementCount + "\"" + statementString + "\"");
			    			statementCount++;
			    			
			    			//Loading the statement into the database
			    			database.execSQL(statementString);
		    			}
	    			}
	    			lineCount++;
		    	}
		    	
		    	//Closing the progress dialog
		    	progressDialog.dismiss();
		    	//Updating the UI with a success message
		    	updateUIWithSuccess();
	    	}
	    	catch (IOException e)
	    	{
	    		e.printStackTrace();
	    	}
			finally
			{
				//Closing the buffered reader
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			//Closing the database
			database.close();
			return null;
		}
    }
}