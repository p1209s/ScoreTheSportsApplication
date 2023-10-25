package com.example.score.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;

import java.util.ArrayList;

public class Ran_DeleteName extends Activity implements OnCheckedChangeListener, OnClickListener{
	private ArrayList<String> playerList;
	private ArrayList<Ran_UpdateObj> updateList;
	private CheckBox[] ch;
	private LinearLayout llayout;
	private Button selectAll;
	private TextView tvTotalPlayers;

    @SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle("Delete Name");
		setContentView(R.layout.ran_deletename);
		llayout = (LinearLayout)findViewById(R.id.layout_checkBoxes);
		
		playerList = getIntent().getStringArrayListExtra("playerList");
		
		if(getIntent().getSerializableExtra("updateList") != null){ //may return null
			updateList = (ArrayList<Ran_UpdateObj>)getIntent().getSerializableExtra("updateList");
		}
		
		ch = new CheckBox[playerList.size()];

		initializeViews();	
	}

    @SuppressLint("SetTextI18n")
	public void initializeViews(){
		for (int i = 0; i < playerList.size(); i++) 
		{
			    ch[i] = new CheckBox(this);
			    ch[i].setOnCheckedChangeListener(this);
			    ch[i].setId(i);
			    ch[i].setText(playerList.get(i));
			    ch[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.rtg_text_size));
			    llayout.addView(ch[i]);
		}
		tvTotalPlayers = (TextView)findViewById(R.id.tvDelNameTotalPlayers);
		TextView tvCurrentPreset = (TextView)findViewById(R.id.tvDelNameCurrentPreset);
		selectAll = (Button)findViewById(R.id.bSelectAll);
		Button delete = (Button) findViewById(R.id.bDelete);
		
		tvTotalPlayers.setText(Integer.toString(playerList.size()));
		tvCurrentPreset.setText(getIntent().getStringExtra("currentPreset"));
		selectAll.setOnClickListener(this);
		delete.setOnClickListener(this);
	}
	
	@SuppressLint("SetTextI18n")
	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        for(CheckBox cb: ch){
            //if even one checkbox is unchecked set it back to "Select All"
            if(!cb.isChecked()) {
                selectAll.setText("Select All");
                return;
            }
        }
        selectAll.setText("Deselect All");
	}

	@SuppressLint({"NonConstantResourceId", "SetTextI18n"})
	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.bSelectAll:
			if(selectAll.getText().toString().equals("Select All")){
				for(CheckBox c : ch){
					c.setChecked(true);
				}
				selectAll.setText("Deselect All"); //if select all change to deselect all
				
			} else if(selectAll.getText().toString().equals("Deselect All")){
				for(CheckBox c : ch){
					c.setChecked(false);
				}
				selectAll.setText("Select All"); //if deselect all change to select all
			}
			break;
		case R.id.bDelete:
			int yGravity = 270;
			int gravityType = Gravity.TOP;
			boolean fatalityBool = false;
			for(CheckBox c : ch){
				if(c.isChecked() && c.getVisibility() == CheckBox.VISIBLE){
					playerList.remove(c.getText().toString());
					tvTotalPlayers.setText(Integer.toString(playerList.size())); //update total
					
					if(updateList!=null){
						updateList.add(new Ran_UpdateObj("delete", c.getText().toString()));//add to updatelist if updatelist was passed in to delete activity
					}		
					
					llayout.removeView(c); //remove checkbox from view
					c.setVisibility(View.GONE);
					
					if(!fatalityBool){
						fatalityBool = true;
					}
				}
			}
			
			if(fatalityBool){
				Toast t = Toast.makeText(getApplicationContext(), "Name deleted.", Toast.LENGTH_SHORT);
				t.setGravity(gravityType, 0, yGravity);
				t.show();
				
				/*Thread thread = new Thread(new FatalitySound());
				thread.start(); //plays fatality sound	*/		
			}
			
			if(llayout.getChildCount() == 0){
				returnDataToMain();
				finish();
			}
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		returnDataToMain();
		super.onBackPressed(); //super does what the default back button does
	}
	
	public void returnDataToMain(){
		Intent returnToMain = new Intent();
		returnToMain.putStringArrayListExtra("playerList", playerList);
		if(updateList != null){
			returnToMain.putExtra("updateList", updateList);
		}
		//returns information back to previous class to. From startActivityForResult to onActivityResult
		setResult(RESULT_OK, returnToMain); //result code and intent
	}
	
	/*public class FatalitySound implements Runnable{
		SoundPool sp;
		int fatality = 0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
				fatality = sp.load(getApplicationContext(), R.raw.fatality, 1); // load this sound file
				
				if (fatality != 0) { //FATALITY!
					sp.play(fatality, 5, 1, 0, 0, 1); // hover over play for documentation
				}
				
				Thread.sleep(2500);
				sp.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}
