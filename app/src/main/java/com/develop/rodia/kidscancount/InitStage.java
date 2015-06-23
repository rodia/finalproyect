package com.develop.rodia.kidscancount;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import com.develop.rodia.kidscancount.data.ResultContract;
import com.develop.rodia.kidscancount.model.StageModel;


public class InitStage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_stage);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_init_stage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_init_stage, container, false);
            Button button = (Button) rootView.findViewById(R.id.send_init_stage);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText name_stage = (EditText)rootView.findViewById(R.id.name_stage);
                    EditText total_for_count = (EditText)rootView.findViewById(R.id.total_for_count);

                    if (!name_stage.getText().equals("") && !total_for_count.getText().equals("")) {
                        StageModel stage = new StageModel();

                        long stage_id = stage.saveStage(name_stage.getText().toString(),
                                total_for_count.getText().toString(), rootView.getContext());

                        String[][] values = stage.valuesForDefault(
                                Integer.parseInt(total_for_count.getText().toString()));
                        stage.bashSaveResource(Integer.parseInt(total_for_count.getText().toString()),
                                values, stage_id, rootView.getContext());
                        Intent intent;
                        intent = new Intent(getActivity(), StageActivity.class);
                        startActivity(intent);
                    }

                }
            });

            return rootView;
        }
    }
}
