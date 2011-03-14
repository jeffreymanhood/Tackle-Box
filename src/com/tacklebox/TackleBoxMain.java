package com.tacklebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TackleBoxMain extends Activity
{
    private TextView rodSetup;
    private TextView lureSetup;
    public static final int UPDATE_ROD_SETUP_SELECTION_ID = 0;
    public static final int UPDATE_LURE_SELECTION_ID = 1;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //createTabs(); you know what - fucktabs

        setLastRodSelected();
        setLastLureSelected();
    }

    private void setLastLureSelected()
    {
        lureSetup = (TextView) findViewById(R.id.main_lure_label);
        //todo: retrieve the lure from the database
        lureSetup.setText(R.string.main_no_lure_saved_text);
    }

    private void setLastRodSelected()
    {
        rodSetup = (TextView) findViewById(R.id.main_rod_setup_label);
        //todo: retrieve the setup from the database
        rodSetup.setText(R.string.main_no_setup_saved_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean retVal = super.onCreateOptionsMenu(menu);
        menu.add(0, UPDATE_ROD_SETUP_SELECTION_ID, 0, R.string.main_add_rod_setup_menu);
        menu.add(0, UPDATE_LURE_SELECTION_ID, 1, R.string.main_add_lure_menu);
        return retVal;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        switch (itemId)
        {
            case UPDATE_ROD_SETUP_SELECTION_ID:
                Intent intent = new Intent(this, RodSetupActivity.class);
                startActivity(intent);
                break;
            case UPDATE_LURE_SELECTION_ID:
                //todo: see println
                System.out.println("TackleBoxMain.onOptionsItemSelected IMPLEMENT LURE SELECTION");
                break;
            default:
                System.err.println("TackleBoxMain.onOptionsItemSelected no action for menu item ID="+itemId);
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        private void createTabs()
        {
            Resources resource = getResources(); 	// resource object to get drawables
            TabHost tabHost = getTabHost();			// the activity tab host
            TabHost.TabSpec spec;					// reusable tab spec
            Intent intent;							// reusable intent for each tab

            // add GearSetup tab
            intent = new Intent().setClass(this, RodSetupActivity.class);
            spec = tabHost.newTabSpec("gearSetup")
                .setIndicator("GearSetup", resource.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
            tabHost.addTab(spec);

            // add TackleBox tab
            intent = new Intent().setClass(this, TackleBoxActivity.class);
            spec = tabHost.newTabSpec("tackleBox")
                .setIndicator("TackleBox", resource.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
            tabHost.addTab(spec);

            // set the current tab
            tabHost.setCurrentTab(0);
        }
    */
}
