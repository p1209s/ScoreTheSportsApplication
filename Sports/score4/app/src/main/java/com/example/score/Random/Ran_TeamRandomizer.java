package com.example.score.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.score.R;
import com.example.score.dialogs.NewSaveChangesDialog;
import com.example.score.dialogs.NumberOfTeamsDialog;
import com.example.score.dialogs.SavePresetAsDialog;

import java.util.ArrayList;

public class Ran_TeamRandomizer extends Activity implements OnClickListener, OnTouchListener{

	private TextView tvTotal, tvEmpty, tvCurrentPreset;
	private EditText etName;
	private ScrollView sv;
	private LinearLayout llPlayerList;
	private ArrayList<String> playerArrList;
	private ArrayList<Ran_UpdateObj>updateList;
	private boolean presetLoaded, changesMade, redirectFromNew;
	private final int yGravity = 270;
	private final int gravityType = Gravity.TOP;
	private String numberOfteamsPrevious;

	private static boolean keyboardHidden = true;
	private int etNameYCoordinate;

	@SuppressLint("NonConstantResourceId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		setContentView(R.layout.ran_teamrand);
		initializeViews();
		playerArrList  = new ArrayList<>();

		Button presetOptions = (Button)findViewById(R.id.bPresetOptions);
		presetOptions.setOnClickListener(v -> {

			PopupMenu popup = new PopupMenu(Ran_TeamRandomizer.this, presetOptions);
			popup.getMenuInflater().inflate(R.menu.teamrand_menu, popup.getMenu());

			popup.setOnMenuItemClickListener((MenuItem item) -> {
				switch (item.getItemId()){
					case R.id.newPreset:
						if(changesMade){
							openSaveNewChangesDialog();
						} else{
							clearAll();
						}

						break;

					case R.id.loadPreset:
						Intent i = new Intent(this, Ran_LoadPreset.class);
						i.putExtra("changesMade", isChangesMade());
						i.putExtra("presetLoaded", isPresetLoaded());
						i.putExtra("updateList", updateList);
						i.putStringArrayListExtra("playerList", playerArrList);
						i.putExtra("currentPreset", tvCurrentPreset.getText().toString());
						startActivityForResult(i, 2);
						break;

					case R.id.savePresetAs:
						openSavePresetAsDialog();
						break;

					case R.id.savePreset:
						savePreset();
						break;

					case R.id.deletePreset:
						Intent in = new Intent(this, Ran_DeletePreset.class);
						if(isPresetLoaded()){
							in.putExtra("currentPreset", tvCurrentPreset.getText().toString());
						}
						startActivityForResult(in, 3);
						break;
				}
				return false;
			});
			popup.show();
		});
	}

	@SuppressLint("ClickableViewAccessibility")
	public void initializeViews(){
		tvTotal = (TextView)findViewById(R.id.tvTotalPlayers);
		tvEmpty = (TextView)findViewById(R.id.tvPlayerList);
		tvCurrentPreset= (TextView)findViewById(R.id.tvCurrentPreset);
		etName = (EditText)findViewById(R.id.etPlayerName);
		sv = (ScrollView)findViewById(R.id.svScroll);
		llPlayerList = (LinearLayout)findViewById(R.id.llPlayerList);

		final Button delName = (Button)findViewById(R.id.bDelName);

		final ViewGroup rlLabels = (ViewGroup)findViewById(R.id.rlLabels);
        Button addActionButton = findViewById(R.id.add_action_button);

		//Makes presetOptions button same size as delName button
		delName.post(() -> {
			Rect rectf = new Rect();
			delName.getLocalVisibleRect(rectf);

		});

		//Listener for when the user hits the enter key on softkeyboard
		etName.setOnEditorActionListener((exampleView, actionId, event) -> {
			if((actionId == EditorInfo.IME_ACTION_DONE
				|| actionId == EditorInfo.IME_NULL
				|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
				&& event.getAction() == KeyEvent.ACTION_UP){

				addName();
			}

			return true;
		});

        //get the starting coordinates of etName before it shifts
		etName.post(() -> {
			int[] coordinates = {0, 0};
			etName.getLocationInWindow(coordinates);
			etNameYCoordinate = coordinates[1];
		});

		//Listener to detect if keyboard is shown or not
		final View decorView = this.getWindow().getDecorView();
		decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
			//Calculations to detect if keyboard is present or not
			Rect rect = new Rect();
			decorView.getWindowVisibleDisplayFrame(rect);
			int displayHeight = rect.bottom - rect.top;
			int height = decorView.getHeight();

			boolean keyboardHiddenTemp = (double)displayHeight / height > 0.8 ;

			if (keyboardHiddenTemp != keyboardHidden) {
				keyboardHidden = keyboardHiddenTemp;

				if (!keyboardHidden) {
					//keyboard shown

					sv.postDelayed(() -> {
						int[] coordinates = {0, 0};
						etName.getLocationInWindow(coordinates);
						//beginning coordinates subtract the new shifted coordinates is the amount shifted
						int shiftInLayoutByKeyboard = etNameYCoordinate - coordinates[1];

						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rlLabels.getLayoutParams();
						params.setMargins(0, shiftInLayoutByKeyboard, 0, 0);
						rlLabels.setLayoutParams(params);


						sv.post(() -> {
							//scrolls the scrollview to the bottom
							sv.fullScroll(View.FOCUS_DOWN);
						});
					}, 225);

				} else {
					//keyboard hidden
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rlLabels.getLayoutParams();
					params.setMargins(0, 0, 0, 0);
					rlLabels.setLayoutParams(params);

				}
			}
		});

		delName.setOnClickListener(this);
        findViewById(R.id.bRandomize).setOnClickListener(this);
        addActionButton.setOnClickListener(this);

        findViewById(R.id.rlRoot).setOnTouchListener(this);
		sv.setOnTouchListener(this);

	}

	@SuppressLint("SetTextI18n")
	private void addName(){
		//--------------------------------Update List
		String name;
		//get name in EditText
		name = etName.getText().toString().trim();

		//if name is empty display toast and return
		if(name.isEmpty()){
			Toast t = Toast.makeText(getApplicationContext(), "Please enter a name!", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();
			etName.setText(""); //reset edittext
			return;
		}else if(name.contains("~") || name.contains("'")){
			Toast t = Toast.makeText(getApplicationContext(), "Name cannot contain the symbols ~ or ' .", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();
			return;
		}else if(playerArrList.contains(name)){
			Toast t = Toast.makeText(getApplicationContext(), "Please enter a unique name.", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();
			return; //exit if duplicate names
		} else if(name.length() > 26){
			Toast t = Toast.makeText(getApplicationContext(), "Name must be 1-26 characters.", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();
			return; //exit if duplicate names
		}

		//--------------------------------Update Total

		int total = Integer.parseInt(tvTotal.getText().toString());
		total ++;
		tvTotal.setText(Integer.toString(total));

		//---------------------------------------------
		if(!isChangesMade()){
			setChangesMade(true); //set changes made to true on add
		}

		//always add to playerList, only add to updateList if preset is loaded for more efficient saves
		playerArrList.add(name);
		if(isPresetLoaded()){ //adds to update List
			updateList.add(new Ran_UpdateObj("add", name));
		}
		//if name is valid add a number to it
		name = total + ". "+ name;

		//remove the tvEmpty
		llPlayerList.removeView(tvEmpty);

		addNameToScreen(name);

		//reset editText to blank after adding a name
		etName.setText("");

        sv.post(() -> {
			//scrolls the scrollview to the bottom
			sv.fullScroll(View.FOCUS_DOWN);
		});
	}

	@SuppressLint("NonConstantResourceId")
	@Override
	public void onClick(View v) {

		switch(v.getId()){
            case R.id.add_action_button:
                addName();
                break;

            case R.id.bDelName:
                if(playerArrList.size()>0){

                    Intent i = new Intent(this, Ran_DeleteName.class);
                    i.putStringArrayListExtra("playerList", playerArrList);
                    i.putExtra("currentPreset", tvCurrentPreset.getText().toString());
                    if(isPresetLoaded()){ //if preset is loaded pass in updatelist
                        i.putExtra("updateList", updateList);
                    }
                    startActivityForResult(i, 1); //0 is default request code
                }else{
                    Toast t = Toast.makeText(getApplicationContext(), "Nothing to delete.", Toast.LENGTH_SHORT);
                    t.setGravity(gravityType, 0, yGravity);
                    t.show();
                }
                break;

            case R.id.bRandomize:
                if(playerArrList.size() >= 2){
                    FragmentManager manager = getFragmentManager();
                    NumberOfTeamsDialog notd = new NumberOfTeamsDialog();
                    notd.show(manager, "notd"); //adds fragment to the manager
                }else{
                    Toast t = Toast.makeText(getApplicationContext(), "Please enter at least 2 players.", Toast.LENGTH_SHORT);
                    t.setGravity(gravityType, 0, yGravity);
                    t.show();
                }
                break;

            case R.id.bPresetOptions:
                openOptionsMenu();
                break;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int eventID = event.getAction();
		//Hide soft KeyBoard when touching background
		if (eventID == MotionEvent.ACTION_DOWN) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
			return true;
		}

		return false;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == RESULT_OK){

			switch(requestCode){
				//Bundle basket = data.getExtras();
			case 1://1 if deleteName
				if(playerArrList.size() == data.getStringArrayListExtra("playerList").size()){
					return; //exit if playerList size did not change
				}

				if(!isChangesMade()){
					setChangesMade(true); //set changes made to true on delete
				}

				playerArrList = data.getStringArrayListExtra("playerList");
				if(data.getSerializableExtra("updateList") != null){
					updateList = (ArrayList<Ran_UpdateObj>)data.getSerializableExtra("updateList");
				}

				populatePlayerList();
				break;
			case 2://load preset
				tvCurrentPreset.setText(data.getStringExtra("presetName"));
				playerArrList = data.getStringArrayListExtra("playerNames");

				populatePlayerList();

				setPresetLoaded(true); //make sure preset is loaded so we can start up initializeupdatelist
				initializeUpdateList();
				break;
			case 3: //delete Preset
				if(data.getBooleanExtra("currPresetDeleted", false)){
					clearAll();
				}
				break;
			}
		}
	}

	@SuppressLint("SetTextI18n")
	public void populatePlayerList(){
		llPlayerList.removeAllViews();

		if(playerArrList.size() == 0){
			llPlayerList.addView(tvEmpty);
			tvTotal.setText(Integer.toString(playerArrList.size()));
			setChangesMade(false);
			return; //exit if playerArrList is 0
		}
		int i=1;
		for(String nameString : playerArrList){
			//add name to players list
			String name = i + ". " + nameString;
			addNameToScreen(name);
			i++;
		}
		//tvList.setText(revisedList); //set the tv to the new List after revision(delete/update)
		tvTotal.setText(Integer.toString(playerArrList.size()));
	}

	public void initializeUpdateList(){ //must initialize update if you load a preset
		if(isPresetLoaded()){
			updateList = new ArrayList<>();
		}
	}

	@SuppressLint("SetTextI18n")
	public void clearAll(){
		setPresetLoaded(false);
		tvCurrentPreset.setText("None");
		playerArrList.clear();
		updateList = null;
		populatePlayerList();
	}

	private void openSavePresetAsDialog(){
		FragmentManager manager = getFragmentManager();
		SavePresetAsDialog savepreset = new SavePresetAsDialog();
		savepreset.show(manager, "savepreset"); //adds fragment to the manager
	}

	public void openSaveNewChangesDialog(){
		FragmentManager manager = getFragmentManager();
		NewSaveChangesDialog newscd = new NewSaveChangesDialog();
		newscd.show(manager, "newscd"); //adds fragment to the manager
	}

	public void savePreset(){
		if(isPresetLoaded()){
			Ran_DbHelper db = new Ran_DbHelper(this);
			db.open();
			db.updatePreset(tvCurrentPreset.getText().toString(), updateList);
			db.close();
			Toast t = Toast.makeText(getApplicationContext(), "Preset saved.", Toast.LENGTH_SHORT);
			t.setGravity(gravityType, 0, yGravity);
			t.show();

			updateList.clear();
			setChangesMade(false); //when saved = blank slate
		} else {
			openSavePresetAsDialog();
		}
	}

	public void addNameToScreen(String name){
		//add name to players list
		TextView tvNewName = new TextView(this);
		tvNewName.setText(name.replaceFirst("\\s", "\u00A0")); //replace first space with unicode no break space);
		tvNewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.rtg_text_size));
		llPlayerList.addView(tvNewName);
	}

	public void setChangesMade(boolean changesMade){
		this.changesMade = changesMade;
	}

	public boolean isChangesMade(){
		return changesMade;
	}

	public void setRedirectFromNew (boolean redirectFromNew){
		this.redirectFromNew = redirectFromNew;
	}

	public boolean isRedirectedFromNew(){
		return redirectFromNew;
	}

	public ArrayList<String> getPlayersArrList(){
		return playerArrList;
	}

	public void setCurrentPresetText(String currentPreset){
		tvCurrentPreset.setText(currentPreset);
	}

	public boolean isPresetLoaded() {
		return presetLoaded;
	}

	public void setPresetLoaded(boolean presetLoaded) {
		this.presetLoaded = presetLoaded;
		setChangesMade(false); //if presets is loaded there are no new changes
	}

	public String getCurrentPreset(){
		return tvCurrentPreset.getText().toString();
	}

	public String getTotalPlayer(){
		return tvTotal.getText().toString();
	}

	public String getNumberOfteamsPrevious() {
		return numberOfteamsPrevious;
	}

	public void setNumberOfteamsPrevious(String numberOfteamsPrevious) {
		this.numberOfteamsPrevious = numberOfteamsPrevious;
	}

	public void onBackPressed() {
		// Go back to the Home page
		Intent intent = new Intent(getApplicationContext(), com.example.score.HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

}