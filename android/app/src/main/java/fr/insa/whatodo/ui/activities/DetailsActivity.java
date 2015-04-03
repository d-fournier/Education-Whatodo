package fr.insa.whatodo.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import fr.insa.whatodo.R;

/**
 * Created by Benjamin on 03/04/2015.
 */
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
    }
}
