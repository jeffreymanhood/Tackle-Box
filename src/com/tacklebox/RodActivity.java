package com.tacklebox;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.tacklebox.db.TackleBoxDbAdapter;

/**
 * User: Call me Ismail
 * Date: 3/6/11
 * Time: 4:47 PM
 */
public class RodActivity extends ListActivity
{
    private TackleBoxDbAdapter dbAdapter;
    private static final int ADD_ROD = 0;
    private static final int NEW_ROD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rod_activity);

        dbAdapter = new TackleBoxDbAdapter(this);
        dbAdapter.open();

        fillData();
    }

    private void fillData()
    {
		// Get all of the rows from the database and create the item list
        Cursor cursor = dbAdapter.fetchAllRods();
        startManagingCursor(cursor);

        // create an array to specify the fields we want to display in the list
        String[] from = new String[]{	TackleBoxDbAdapter.COL_ROD_BRAND,
        								TackleBoxDbAdapter.COL_ROD_MODEL,
    									TackleBoxDbAdapter.COL_ROD_CATEGORY,
    									TackleBoxDbAdapter.COL_ROD_SIZE,
    									TackleBoxDbAdapter.COL_ROD_LINE_WEIGHT,
    									TackleBoxDbAdapter.COL_ROD_POWER_RATING};

        // and an array of the fields we want to bind those fields to
        int[] to = new int[]{	R.id.rod_brand,
        						R.id.rod_model,
        						R.id.rod_category,
        						R.id.rod_size,
        						R.id.rod_line_weight,
        						R.id.rod_power_rating};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter rods =
            new SimpleCursorAdapter(this, R.layout.rod_row, cursor, from, to);
        setListAdapter(rods);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean retVal = super.onCreateOptionsMenu(menu);
        menu.add(0, ADD_ROD, 0, R.string.rod_activity_add_rod_menu);
        return retVal;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case ADD_ROD:
                Intent intent = new Intent(this, RodActivityEdit.class);
                startActivityForResult(intent, NEW_ROD);
                fillData();
                break;
            default:
                System.err.println("RodActivity.onOptionsItemSelected no action for menu item " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Cursor cursor = dbAdapter.fetchRodNameById(id);
        bundle.putString("ROD_NAME", cursor.getString(cursor.getColumnIndex(TackleBoxDbAdapter.COL_ROD_BRAND))+ " " +
            cursor.getString(cursor.getColumnIndex(TackleBoxDbAdapter.COL_ROD_MODEL)));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
