package com.tacklebox;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.tacklebox.db.TackleBoxDbAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: Call me Ismail
 * Date: 2/13/11
 * Time: 8:17 PM
 */
public class RodSetupActivity extends Activity
{
    public static final int ACTIVITY_ADD_LURE = 0;
    private TackleBoxDbAdapter dbAdapter;
    private TextView rodNameTextView;
    private TextView reelNameTextView;
    private TextView lineNameTextView;
    private static final int UPDATE_ROD_ACTIVITY = 0;
    private static final int UPDATE_REEL_ACTIVITY = 1;
    private static final int UPDATE_LINE_ACTIVITY =2;
    private static final int UPDATE_ROD_INTENT = 10;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rod_setup);

        rodNameTextView = (TextView) findViewById(R.id.rod_setup_rod_name);
        reelNameTextView = (TextView) findViewById(R.id.rod_setup_reel_name);
        lineNameTextView = (TextView) findViewById(R.id.rod_setup_line_name);

        dbAdapter = new TackleBoxDbAdapter(this);
        dbAdapter.open();

        fillRodSetupData();
	}

    private void fillRodSetupData()
    {
		Cursor cursor = dbAdapter.fetchRodSelection();

        int count = cursor.getCount();
        if(count < 1)
        {
            rodNameTextView.setText(R.string.rod_setup_no_rod);
            reelNameTextView.setText(R.string.rod_setup_no_reel);
            lineNameTextView.setText(R.string.rod_setup_no_line);
        }
        else if (count == 1)
        {
            rodNameTextView.setText(cursor.getString(cursor.getColumnIndex(TackleBoxDbAdapter.COL_ROD_SETUP_ROD_NAME)));
            reelNameTextView.setText(cursor.getString(cursor.getColumnIndex(TackleBoxDbAdapter.COL_ROD_SETUP_REEL_NAME)));
            lineNameTextView.setText(cursor.getString(cursor.getColumnIndex(TackleBoxDbAdapter.COL_ROD_SETUP_LINE_NAME)));
        }
        else
        {
            System.err.println("RodSetupActivity.fillRodSetupData returned more than one current selection.");
        }

        cursor.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean retVal = super.onCreateOptionsMenu(menu);
        menu.add(0, UPDATE_ROD_ACTIVITY, 0, R.string.rod_setup_update_rods_menu);
        menu.add(0, UPDATE_REEL_ACTIVITY, 1, R.string.rod_setup_update_reels_menu);
        menu.add(0, UPDATE_LINE_ACTIVITY, 2, R.string.rod_setup_update_line_menu);
        return retVal;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case UPDATE_ROD_ACTIVITY:
                Intent intent = new Intent(this, RodActivity.class);
                startActivityForResult(intent, UPDATE_ROD_INTENT);
                break;

            case UPDATE_REEL_ACTIVITY:
                //todo: implement activity
                break;

            case UPDATE_LINE_ACTIVITY:
                //todo: implement activity
                break;

            default:
                System.err.println("RodSetupActivity.onOptionsItemSelected no action defined for " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        switch (requestCode)
        {
            case UPDATE_ROD_INTENT:
                    rodNameTextView.setText((String)extras.get("ROD_NAME"));
                break;
            default:
                System.err.println("RodSetupActivity.onActivityResult unknown action "  + requestCode);
        }
    }
}

