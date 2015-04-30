package fr.insa.whatodo.ui.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import fr.insa.whatodo.R;
import fr.insa.whatodo.model.User;
import fr.insa.whatodo.ui.activities.HomeActivity;
import fr.insa.whatodo.utils.JSonParser.JSonParser;
import fr.insa.whatodo.utils.Utils;

/**
 * Created by Benjamin on 30/04/2015.
 */
public class LoginFragment extends DialogFragment implements View.OnClickListener {

    private static final String LOGIN_URL = "http://dfournier.ovh/auth/login";

    RSAPublicKey publicKey;
    Button submitButton;
    EditText email;
    EditText password;

    public LoginFragment() {

/*        KeyFactory keyFactory = null;
        try {
            RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger("9656767816324566455108963108813036530472269724259890899919421571108214755526575616206817371735345374475104423930595151471602785808878634661330733400974883"),
                                                            new BigInteger("65537"));
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encodedPassword = cipher.doFinal("TU".getBytes());
            System.out.println("Longueur : " + encodedPassword.length);
            System.out.println("Contenu : " + Arrays.toString(encodedPassword));
            System.out.println("Mot de passe : "+ Arrays.toString("TU".getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        submitButton = (Button) rootView.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
        email = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (Utils.checkConnectivity(getActivity())) {
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (TextUtils.isEmpty(s)) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_login), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.login_ok), Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("token", s);
                        editor.apply();
                        ((HomeActivity)getActivity()).updateLoginFragment();
                    }
                }

                @Override
                protected String doInBackground(Void... params) {
                    HttpURLConnection urlConnection;
                    //J'envoie la requete au serveur
                    try {
                        URL login_url = new URL(LOGIN_URL);
                        urlConnection = (HttpURLConnection) login_url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);

                /*        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                        encodedPassword = cipher.doFinal(password.getText().toString().getBytes());*/

                        DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                        os.writeBytes(("email=" + URLEncoder.encode(email.getText().toString(), "UTF-8") + "&password=" + URLEncoder.encode(password.getText().toString(), "UTF-8")));
                        os.flush();
                        os.close();

                        int code = urlConnection.getResponseCode();
                        System.out.println("CODE : " + code);
                        if (code != 200) {
                            return null;
                        } else {
                            byte[] buffer = new byte[128];
                            InputStream is = urlConnection.getInputStream();
                            is.read(buffer);
                            is.close();
                            String token = new String(buffer);
                            token = token.substring(token.indexOf(":") + 2, token.lastIndexOf("\""));
                            urlConnection.disconnect();
                            return token;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            task.execute();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }

    }
}
