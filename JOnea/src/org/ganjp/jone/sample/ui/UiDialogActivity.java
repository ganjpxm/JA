package org.ganjp.jone.sample.ui;

import org.ganjp.jlib.core.util.DialogUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.JOneActivity;
import org.ganjp.jone.sample.SampleActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class UiDialogActivity extends JOneActivity {
	private Button oneBtnDialogBtn;
	private Button twoBtnDialogBtn;
	private Button threeeBtnDialogBtn;
	private Button inputDialogBtn;
	private Button radiosDialogBtn;
	private Button checkboxsDialogBtn;
	private Button listDialogBtn;
	private Button customeDialogBtn;
	private Button progressDialogBtn;
	private Button timeDialogBtn;
	private Button dateDialogBtn;
	
	private TextView mMessageTv;
	private static String selectItem = "";
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sample_ui_dialog);
		super.onCreate(savedInstanceState);
		mTitleTv.setText("Dialog");
		mMessageTv = (TextView)findViewById(R.id.message_tv);
		
		oneBtnDialogBtn=(Button)findViewById(R.id.one_btn_dialog_btn);
		twoBtnDialogBtn=(Button)findViewById(R.id.two_btn_dialog_btn);
		threeeBtnDialogBtn=(Button)findViewById(R.id.three_btn_dialog_btn);
		inputDialogBtn=(Button)findViewById(R.id.input_dialog_btn);
		radiosDialogBtn=(Button)findViewById(R.id.radios_dialog_btn);
		checkboxsDialogBtn=(Button)findViewById(R.id.checkboxs_dialog_btn);
		listDialogBtn=(Button)findViewById(R.id.list_dialog_btn);
		customeDialogBtn=(Button)findViewById(R.id.custome_dialog_btn);
		progressDialogBtn=(Button)findViewById(R.id.progress_dialog_btn);
		timeDialogBtn=(Button)findViewById(R.id.time_dialog_btn);
		dateDialogBtn=(Button)findViewById(R.id.date_dialog_btn);
		
		oneBtnDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtil.showAlertDialog(UiDialogActivity.this, getString(R.string.message));
			}
		});
		twoBtnDialogBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DialogUtil.showConfirmDialog(UiDialogActivity.this, null, getString(R.string.message), 
					new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			        		if (which==-1) mMessageTv.setText("confirm");
			        		else if (which==-2) mMessageTv.setText("cancel");
			            }
		        });
			}
	    });
		
		threeeBtnDialogBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DialogUtil.showAlertDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_alert, getString(R.string.title), getString(R.string.message), 
						new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				        		if (which==-1) mMessageTv.setText("confirm");
				        		else if (which==-2) mMessageTv.setText("cancel");
				        		else if (which==-3) mMessageTv.setText("more");
				            }
			        }, new String[]{getString(R.string.confirm), getString(R.string.more), getString(R.string.cancel)}
			    );
			}
	 	});
		
		inputDialogBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final EditText et = new EditText(UiDialogActivity.this);
				DialogUtil.showInputDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, getString(R.string.title), 
					getString(R.string.message), et, new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int which) {
				    		if (which==-1) mMessageTv.setText("confirm : " + et.getText()); 
			        		else if (which==-2) mMessageTv.setText("cancel");
				    	}
				});
			}
		});
		
		radiosDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = {"Red", "Green", "Blue"};
				DialogUtil.showRadiosDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, getString(R.string.title), items, 1,
					new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int which) {
				    		if (which>=0) {
				    			selectItem = String.valueOf(items[which]); //selectItem is static
				    			mMessageTv.setText(selectItem);
				    		} else if (which==-1) {
				    			mMessageTv.setText("confirm : " + selectItem);
				    		} else if (which==-2) {
				    			mMessageTv.setText("cancel");
				    		}
				    	}
				});
			}
		});
		
		checkboxsDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = {"Red", "Green", "Blue"};
				final boolean [] itemsSelected = {true, false, true};
				DialogUtil.showCheckboxsDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, getString(R.string.title), items,
					itemsSelected, new DialogInterface.OnMultiChoiceClickListener() {
					    public void onClick(DialogInterface dialogInterface, int item, boolean b) {
					        mMessageTv.setText(String.format("%s: %s", items[item], b));
					    }
					}, new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int which) {
				    		if (which==-1) mMessageTv.setText("confirm : "); 
			        		else if (which==-2) mMessageTv.setText("cancel");
				    	}
					}
				);
			}
		});
		
		listDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = {"Red", "Green", "Blue"};
				DialogUtil.showListDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, getString(R.string.title), items, 
					new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int which) {
				    		if (which>=0) {
				    			selectItem = String.valueOf(items[which]); //selectItem is static
				    			mMessageTv.setText(selectItem);
				    		} else if (which==-1) {
				    			mMessageTv.setText("cancel");
				    		}
				    	}
				});
			}
		});
		
		customeDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final View view = LayoutInflater.from(UiDialogActivity.this).inflate(R.layout.sample_ui_dialog_custom, null);
				DialogUtil.showCustomeDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, getString(R.string.title), view, 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							EditText userNameEt = (EditText)view.findViewById(R.id.user_name_et);
					    	if (which==-1) mMessageTv.setText("confirm user name : " + userNameEt.getText());
				        	else if (which==-2) mMessageTv.setText("cancel");
					    }
				});
			}
	 	});
		
		progressDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = DialogUtil.getProgressDialog(UiDialogActivity.this, android.R.drawable.ic_dialog_info, 
						getString(R.string.title), getString(R.string.message));
				dialog.show();
			}
	 	});
		
		timeDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(UiDialogActivity.this, 0, null, 0, 0, false);
				dialog.setTitle(R.string.title);
				dialog.show();
			}
	 	});
		
		dateDialogBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
						mMessageTv.setText(new StringBuilder().append(selectedMonth + 1)
						   .append("-").append(selectedDay).append("-").append(selectedYear)
						   .append(" "));
					}
				};
	
				DatePickerDialog dialog = new DatePickerDialog(UiDialogActivity.this, 0, datePickerListener, 0, 0, 0);
		        dialog.setTitle(R.string.title);
		        dialog.show();
			}
	 	});
	}
	
	@Override
    public void onClick(View view) {
    	super.onClick(view);
    	if (view == mBackBtn) {
    		startActivity(new Intent(this, SampleActivity.class));
		}
    }
}