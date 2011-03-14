package com.tacklebox;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import com.tacklebox.db.TackleBoxDbAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: Call me Ismail
 * Date: 2/13/11
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TackleBoxActivity extends ListActivity
{
    private static final int LURE_ACTIVITY_CREATE = 0;

     private TackleBoxDbAdapter dbAdapter;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rod_setup);

        initializeDb();
    }

    private void initializeDb()
	{
		dbAdapter = new TackleBoxDbAdapter(this);
		dbAdapter.open();

		fillData();
	}


	public void fillData()
	{
		// Get all of the rows from the database and create the item list
        Cursor notesCursor = dbAdapter.fetchAllLures();
        startManagingCursor(notesCursor);

        // create an array to specify the fields we want to display in the list
        String[] from = new String[]{	TackleBoxDbAdapter.KEY_LURE_BRAND,
        								TackleBoxDbAdapter.KEY_LURE_MODEL,
    									TackleBoxDbAdapter.KEY_LURE_CATEGORY,
    									TackleBoxDbAdapter.KEY_LURE_SIZE,
    									TackleBoxDbAdapter.KEY_LURE_COLOR,
    									TackleBoxDbAdapter.KEY_LURE_SPECIES};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[]{	R.id.lure_brand,
        						R.id.lure_model,
        						R.id.lure_category,
        						R.id.lure_size,
        						R.id.lure_color,
        						R.id.lure_species};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter lures =
            new SimpleCursorAdapter(this, R.layout.lure_table, notesCursor, from, to);
        setListAdapter(lures);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case RodSetupActivity.ACTIVITY_ADD_LURE:
                fillData();
                break;
            default:
                System.err.println("TackleBoxActivity.onActivityResult WRONG! No case defined for request code: " + requestCode);
        }
    }

    protected void createNewLure()
	{
		//Intent intent = new Intent(this, Lure.class);
		//startActivityForResult(intent, LURE_ACTIVITY_CREATE);
	}
}
