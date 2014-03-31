package com.example.hello;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class PictureConfirmationDialogue extends DialogFragment implements
	  DialogInterface.OnClickListener {
	private View form=null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	  form=
	      getActivity().getLayoutInflater()
	                   .inflate(R.layout.dialog, null);

	  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

	  builder.setMessage("Do you want to proceed to next step or redo this step?");
	  
	  return(builder.setTitle(R.string.nextstep).setView(form)
	                .setPositiveButton(R.string.proceed, this)
	                .setNegativeButton(R.string.redo, null).create());
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
	 
	  MainActivity activity = (MainActivity)this.getActivity();
	  activity.proceedToNextStep();
	  
	}

	@Override
	public void onDismiss(DialogInterface unused) {
	  super.onDismiss(unused);
	  
	  Log.d(getClass().getSimpleName(), "unused");
	}

	@Override
	public void onCancel(DialogInterface unused) {
	  super.onCancel(unused);
	  
	  MainActivity activity = (MainActivity)this.getActivity();
	  activity.repeatStep();
	}
	}



