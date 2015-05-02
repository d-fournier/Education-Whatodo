package fr.insa.whatodo.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.insa.whatodo.R;
import fr.insa.whatodo.services.DatabaseServices;
import fr.insa.whatodo.ui.activities.HomeActivity;
import fr.insa.whatodo.utils.EventDatabaseHelper;
import fr.insa.whatodo.utils.MultipartUtility;


/**
 * Created by Benjamin on 01/05/2015.
 */
public class CreationFragment extends Fragment implements View.OnClickListener {


    Button imageButton;
    Button submitButton;
    EditText title;
    EditText description;
    EditText url;
    EditText startTime;
    EditText endTime;
    EditText startDate;
    EditText endDate;
    EditText price;
    EditText minAge;
    EditText address;
    EditText city;
    EditText tags;
    List<CheckBox> categories;

    protected EventDatabaseHelper mDbHelper;
    SQLiteDatabase read_db = null;

    String filePath;

    public CreationFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_creation, container, false);

        mDbHelper = new EventDatabaseHelper(getActivity());
        read_db = mDbHelper.getReadableDatabase();
        imageButton = (Button) rootView.findViewById(R.id.button_image);
        submitButton = (Button) rootView.findViewById(R.id.button_submit);
        title = (EditText) rootView.findViewById(R.id.edit_title);
        description = (EditText) rootView.findViewById(R.id.edit_description);
        url = (EditText) rootView.findViewById(R.id.edit_url);
        startTime = (EditText) rootView.findViewById(R.id.timepicker_begin);
        endTime = (EditText) rootView.findViewById(R.id.timepicker_end);
        startDate = (EditText) rootView.findViewById(R.id.datepicker_begin);
        endDate = (EditText) rootView.findViewById(R.id.datepicker_end);
        price = (EditText) rootView.findViewById(R.id.edit_price);
        minAge = (EditText) rootView.findViewById(R.id.edit_age);
        address = (EditText) rootView.findViewById(R.id.edit_address);
        city = (EditText) rootView.findViewById(R.id.edit_city);
        tags = (EditText) rootView.findViewById(R.id.edit_tags);

        categories = new ArrayList<>();
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxSpectacle));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxConcert));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxTheatre));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxConference));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxDebat));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxExposition));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxSoiree));
        categories.add((CheckBox) rootView.findViewById(R.id.checkBoxSport));

        imageButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            filePath = uri.getPath();
            imageButton.setText("Fichier importé");
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case(R.id.button_submit) :
                //On lance le service d'upload du fichier
                AsyncTask<String, Void, List<String>> task = new AsyncTask<String, Void, List<String>>() {
                    @Override
                    protected void onPostExecute(List<String> response) {
                        super.onPostExecute(response);
                        if(response == null)
                        {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_creation), Toast.LENGTH_SHORT).show();
                        }else
                        {
                            ((HomeActivity)getActivity()).updateCreationFragment();
                        }

                    }

                    @Override
                    protected List<String> doInBackground(String... params) {

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        try {

                            MultipartUtility multipart = new MultipartUtility("http://dfournier.ovh/api/event/", "UTF-8", "token "+prefs.getString("token",""));

                            multipart.addFormField("name", title.getText().toString());
                            multipart.addFormField("description", description.getText().toString());
                            multipart.addFormField("url", url.getText().toString());
                            multipart.addFormField("startTime", startTime.getText().toString());
                            multipart.addFormField("endTime", endTime.getText().toString());
                            multipart.addFormField("startDate", startDate.getText().toString());
                            multipart.addFormField("endDate", endDate.getText().toString());
                            multipart.addFormField("price", price.getText().toString());
                            multipart.addFormField("min_age", minAge.getText().toString());
                            multipart.addFormField("address", address.getText().toString());
                            String s_city = (city.getText().toString());
                            int space = s_city.indexOf(" ");
                            String cityName = city.getText().toString().substring(space+1, s_city.length());
                            String cityCode = city.getText().toString().substring(0, space);
                            multipart.addFormField("city", DatabaseServices.getCityId(cityName, cityCode, read_db));
                            int i = 1;
                            for(CheckBox cb : categories)
                            {
                                if(cb.isChecked())
                                {
                                    multipart.addFormField("categories", ""+i);
                                }
                                i++;
                            }
                            String[] t_tags = tags.getText().toString().split(",");
                            for(String s : t_tags)
                            {
                                multipart.addFormField("tags", s);
                            }
                            multipart.addFormField("latitude", "0");
                            multipart.addFormField("longitude", "0");

                            multipart.addFilePart("imageEvent", new File(filePath));

                           List<String> response = multipart.finish();
                           return response;
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
                task.execute(filePath);
                break;
            case (R.id.button_image) :
                //On selectionne le fichier
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("file/*");
                Intent c = Intent.createChooser(chooseFile, "Choose file");
                startActivityForResult(c, 1);
                break;
        }



    }
}
