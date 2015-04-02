package fr.insa.whatodo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.insa.whatodo.R;

/**
 * Created by Benjamin on 24/03/2015.
 */
public class DownloadFragment extends Fragment {

    public DownloadFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_download, container, false);
        return rootView;
    }


}
