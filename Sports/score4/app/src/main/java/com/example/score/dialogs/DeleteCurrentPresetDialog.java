package com.example.score.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.score.R;
import com.example.score.Random.Ran_DeletePreset;

public class DeleteCurrentPresetDialog extends DialogFragment implements OnClickListener{
	
	Ran_DeletePreset dp;
	@Override
	public void onAttach(Activity activity) { //the activity holding the fragment will be passed in here
		// TODO Auto-generated method stub
		super.onAttach(activity);
		dp = (Ran_DeletePreset) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("Delete Current Preset?");
		
		View view = inflater.inflate(R.layout.ran_deletecurrentpresetdialog, null);
		Button bCancelCurrPresetDel = (Button) view.findViewById(R.id.bCancelCurrentPresetDelete);
		Button bConfirmCurrPresetDel= (Button) view.findViewById(R.id.bConfirmCurrentPresetDelete);
		
		bCancelCurrPresetDel.setOnClickListener(this);
		bConfirmCurrPresetDel.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.bCancelCurrentPresetDelete){
			dismiss();
			
		} else if(v.getId() == R.id.bConfirmCurrentPresetDelete){
			dp.deleteCurrentPreset();
			dp.setDeleteCurrentPresetExtraTrue();
			dismiss();
		}
	}

}
