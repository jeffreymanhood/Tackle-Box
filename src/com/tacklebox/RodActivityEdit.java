package com.tacklebox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tacklebox.db.TackleBoxDbAdapter;

/**
 * User: Call me Ismail
 * Date: 3/6/11
 * Time: 6:04 PM
 */
public class RodActivityEdit extends Activity
{
    private TackleBoxDbAdapter dbAdapter;
    private TextView brandText;
    private TextView modelText;
    private TextView categoryText;
    private TextView sizeText;
    private TextView lineWeightText;
    private TextView powerRatingText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dbAdapter = new TackleBoxDbAdapter(this);
        dbAdapter.open();

        setContentView(R.layout.rod_activity_edit);

        brandText = (TextView) findViewById(R.id.rod_activity_edit_brand);
        modelText = (TextView) findViewById(R.id.rod_activity_edit_model);
        categoryText = (TextView) findViewById(R.id.rod_activity_edit_category);
        sizeText = (TextView) findViewById(R.id.rod_activity_edit_size);
        lineWeightText = (TextView) findViewById(R.id.rod_activity_edit_line_weight);
        powerRatingText = (TextView) findViewById(R.id.rod_activity_edit_power_rating);

        saveButton = (Button) findViewById(R.id.rod_activity_edit_save_button);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        saveTextFields();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        saveTextFields();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateTextFields();
    }

    private void populateTextFields()
    {
        //todo: implement when we allow editing by contextual menu selection
    }

    private void saveTextFields()
    {
        String brand = brandText.getText().toString();
        String model = modelText.getText().toString();
        String category = categoryText.getText().toString();
        String size = sizeText.getText().toString();
        String lineWeight = lineWeightText.getText().toString();
        String powerRating = powerRatingText.getText().toString();

        //todo: create an update method when we allow editing by contextual menu selection
        dbAdapter.createRod(brand, model, category, size, lineWeight, powerRating);
    }
}
