package fr.insa.whatodo.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;

import org.apache.http.HttpResponse;

import java.io.File;

import fr.insa.whatodo.R;
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
                AsyncTask<String, Void, Integer> task = new AsyncTask<String, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(String... params) {

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        try {
 /*                           HttpClient http = new DefaultHttpClient();
                            HttpPost method = new HttpPost("http://dfournier.ovh/api/event/");
                            method.addHeader("Authorization", "token "+prefs.getString("token",""));
                            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
                            entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                            entity.addPart("name", new StringBody(title.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("description", new StringBody(description.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("url", new StringBody(url.getText().toString(), ContentType.TEXT_PLAIN));
                           // entity.addPart("imageEvent", new FileBody((new File(filePath)) , ContentType.APPLICATION_OCTET_STREAM));
                            entity.addPart("imageEvent", new ByteArrayBody(new byte[1024*1024], filePath));
                            entity.addPart("startTime", new StringBody(startTime.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("endTime", new StringBody(endTime.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("startDate", new StringBody(startDate.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("endDate", new StringBody(endDate.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("price", new StringBody(price.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("min_age", new StringBody(minAge.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("address", new StringBody(address.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("city", new StringBody(city.getText().toString(), ContentType.TEXT_PLAIN));
                            entity.addPart("categories", new StringBody(("2"), ContentType.TEXT_PLAIN));
                            entity.addPart("tags", new StringBody(("2"), ContentType.TEXT_PLAIN));
                            entity.addPart("latitude", new StringBody(("0"), ContentType.TEXT_PLAIN));
                            entity.addPart("longitude", new StringBody(("0"), ContentType.TEXT_PLAIN));

                            method.setEntity(entity.build());

                            response = http.execute(method);*/

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
                            multipart.addFormField("city", city.getText().toString());
                            multipart.addFormField("categories", "2");
                            multipart.addFormField("tags", "2");
                            multipart.addFormField("latitude", "0");
                            multipart.addFormField("longitude", "0");

                            multipart.addFilePart("imageEvent", new File(filePath));
                            multipart.finish();

                            return 1;
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                            return 0;
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
