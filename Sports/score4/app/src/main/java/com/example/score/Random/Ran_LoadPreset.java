package com.example.score.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.dialogs.LoadSaveChangesDialog;
import com.example.score.dialogs.LoadSavePresetAsDialog;

import java.util.ArrayList;

public class Ran_LoadPreset extends Activity implements OnClickListener{
	private TextView tvEmptyPreset, tvCurrentPreset;
	private RadioButton[] radio;
	private RadioGroup rgPresetGroup;
	private final int yGravity = 270;
	private final int gravityType = Gravity.TOP;
	private int checkedRadio;
	private ArrayList<String> playerList;
	private boolean changesMade;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //get rid of action bar at top
		setContentView(R.layout.ran_loadpreset);
		initializeViews();
		loadPresets();
		
		tvCurrentPreset.setText(getIntent().getStringExtra("currentPreset"));
		playerList = getIntent().getStringArrayListExtra("playerList");
		changesMade = getIntent().getBooleanExtra("changesMade", false);
	}


    public void initializeViews(){
		Button bLoadPreset = (Button)findViewById(R.id.bLoadPreset);
		Button bViewPreset = (Button)findViewById(R.id.bViewPreset);
		tvEmptyPreset = (TextView)findViewById(R.id.tvEmptyPresets);
		tvCurrentPreset = (TextView)findViewById(R.id.tvLoadCurrrentPreset);
		rgPresetGroup = (RadioGroup)findViewById(R.id.rgLoadPreset);
		
		bLoadPreset.setOnClickListener(this);
		bViewPreset.setOnClickListener(this);
	}
	
	public void loadPresets(){
		String[] presetNames;
		Ran_DbHelper db = new Ran_DbHelper(this);
		db.open(); //open db
		presetNames = db.getPresetNames(); //execute cmd
		if(presetNames.length == 0){
			tvEmptyPreset.setVisibility(View.VISIBLE);
			return;
		}
		radio = new RadioButton[presetNames.length];
		for(int i=0; i<radio.length; i++){
			radio[i] = new RadioButton(this);
			radio[i].setText(presetNames[i]);
			radio[i].setId(i);
			radio[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.rtg_text_size));
			rgPresetGroup.addView(radio[i]);
		}
		db.close(); //close db
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
		checkedRadio = rgPresetGroup.getCheckedRadioButtonId();
		
		//return if nothing is checked
		if(checkedRadio < 0){
			showPickAPresetToast();
			return;
		}
					
		switch(v.getId()){
		case R.id.bLoadPreset:
			/*if(tvCurrentPreset.getText().toString().equals(radio[checkedRadio].getText().toString())){
				finish();//finish if preset is already loaded
			}*/
			if(changesMade){
				FragmentManager manager = getFragmentManager();
				LoadSaveChangesDialog loadscd = new LoadSaveChangesDialog();
				loadscd.show(manager, "newscd"); //adds fragment to the manager
			} else{
				loadPresetInMain();
			}
			break;
			
		case R.id.bViewPreset:
			String presetName = radio[checkedRadio].getText().toString();
			
			Intent data = new Intent(this, Ran_ViewPreset.class);
			data.putExtra("presetName", presetName); //add preset name to intent
			startActivityForResult(data, 2);

			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			if (requestCode == 2) {
				if (changesMade) {
					FragmentManager manager = getFragmentManager();
					LoadSaveChangesDialog loadscd = new LoadSaveChangesDialog();
					loadscd.show(manager, "newscd"); //adds fragment to the manager
				} else {
					loadPresetInMain();
				}
			}
		}
	}
	
	public void loadPresetInMain(){
		String presetName = radio[checkedRadio].getText().toString();
		
		Intent data = new Intent();		
		data.putExtra("presetName", presetName); //add preset name to intent
		
		Ran_DbHelper db = new Ran_DbHelper(this);
		db.open();
		data.putStringArrayListExtra("playerNames", db.getPlayersByPreset(presetName)); //add ArrList of players names to intent
		db.close();
		
		setResult(RESULT_OK, data); //set the result which is preset name and players			
		
		Toast t = Toast.makeText(getApplicationContext(), "Preset loaded.", Toast.LENGTH_SHORT);
		t.setGravity(gravityType, 0, yGravity);
		t.show();
		finish();
		
	}
	
	public void savePreset(){
		if(getIntent().getBooleanExtra("presetLoaded", false)){
			Ran_DbHelper db = new Ran_DbHelper(this);
			db.open();
			db.updatePreset(tvCurrentPreset.getText().toString(), (ArrayList<Ran_UpdateObj>)getIntent().getSerializableExtra("updateList"));
			db.close();
			Toast t = Toast.makeText(getApplicationContext(), "Preset saved.", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();
			
			loadPresetInMain(); //load preset after saving current preset
		} else {
			FragmentManager manager = getFragmentManager();
			LoadSavePresetAsDialog loadSavePreset = new LoadSavePresetAsDialog();
			loadSavePreset.show(manager, "loadSavePreset"); //adds fragment to the manager
		}
	}
	
	public ArrayList<String> getPlayersArrList(){
		return playerList;
	}
	
	public String getCurrentPreset(){
		return tvCurrentPreset.getText().toString();
	}
	
	private void showPickAPresetToast(){
		Toast t = Toast.makeText(getApplicationContext(), "Please pick a preset.", Toast.LENGTH_SHORT);
		t.setGravity(gravityType, 0, yGravity);
		t.show();
	}
}
