package com.example.score.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.score.R;

import java.util.ArrayList;

public class Ran_ViewPreset extends Activity implements OnClickListener{



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //get rid of action bar at top
		setContentView(R.layout.ran_viewpreset);
		initializeViews();
		
	}

    @SuppressLint("SetTextI18n")
	private void initializeViews(){
		ArrayList<String> playerNames;
		
		TextView tvViewedPreset = (TextView)findViewById(R.id.tvViewedPreset);
		TextView tvtotalPlayers = (TextView)findViewById(R.id.tvViewTotalPlayers);
		LinearLayout llViewPreset = (LinearLayout)findViewById(R.id.llViewPreset);
		Button bLoadPreset = (Button)findViewById(R.id.bViewPresetLoad);
		
		String presetName = getIntent().getStringExtra("presetName");
		tvViewedPreset.setText(presetName);
		
		Ran_DbHelper db = new Ran_DbHelper(this);
		db.open();
		playerNames = db.getPlayersByPreset(presetName);
		db.close();
		
		tvtotalPlayers.setText(Integer.toString(playerNames.size()));
		
		if(playerNames.size()==0){
			TextView tvViewName = new TextView(this);
			tvViewName.setText("Empty");
			tvViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.rtg_text_size));
			llViewPreset.addView(tvViewName);
		} else{
			for(int i=0; i<playerNames.size(); i++){
				TextView tvViewName = new TextView(this);
				tvViewName.setText((i + 1) + ". " + playerNames.get(i));
				tvViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.rtg_text_size));
				llViewPreset.addView(tvViewName);
			}
		}
		
		bLoadPreset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.bViewPresetLoad){
			setResult(RESULT_OK);
			finish();
		}
	}
}
