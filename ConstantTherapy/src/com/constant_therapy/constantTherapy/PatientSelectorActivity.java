package com.constant_therapy.constantTherapy;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.constant_therapy.popup.PopoverView;
import com.constant_therapy.popup.PopoverView.PopoverViewDelegate;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.DialogListAdapter;
import com.constant_therapy.util.PatientModel;
import com.constant_therapy.widget.CTTextView;

public abstract class PatientSelectorActivity extends CTActivity implements PopoverViewDelegate {

	protected static final int RESULT_CODE_ADD_PATIENT = 231;

	private static final String LOG_TAG = "PatientSelectorActivity";

	/** used for e.g. the pulldown patient selector */
	PopoverView popoverView; 
	
	/** dialog for updating account info e.g. email addr */
	protected Dialog updateDialog;
	
	/** dialog for updating the user's password */
	Dialog passwordDialog;

	/** list adapter for lists of patients e.g. for patient selector */
	DialogListAdapter dialogListAdapter;

	private static final int ADD_PATIENT_SELECTION = 0;
	/** temp variable for handling multiple click */
	
	int click = 0;

	/** show drop down list of patients to select new patient, add patient, remove patient */
	protected void showPatientDropDown() {
		
		List<PatientModel> actualPatients = CTUser.getInstance().getPatientList();
		List<PatientModel> patientList = new ArrayList<PatientModel>();
		
		// put in a meta-entry as the first row - the "add patient" selection
		PatientModel model = new PatientModel("Add new patient", null, "", "", "");
		patientList.add(model);

		// add the real patients
		for (int k = 0; k < actualPatients.size(); k++) 
			patientList.add(actualPatients.get(k));
		
		// add some blanks if it's a real short list ... I guess this is how it is
		// in the iPad ... gets attention for the pulldown?
		int addListCount = 10 - actualPatients.size();
		for (int i = 0; i < addListCount; i++) {
			model = new PatientModel("", null, "", "", "");
			patientList.add(model);
		}

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.popup_list, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 2.0f, 2.0f);
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(findViewById(R.id.rlPatient)),
				PopoverView.PopoverArrowDirectionUp, true);

		final ListView list = (ListView) popoverView
				.findViewById(R.id.listView1);
		dialogListAdapter = new DialogListAdapter(this,
				patientList, true);
		list.setAdapter(dialogListAdapter);

		// get the position selected patient in the initial alert.
		dialogListAdapter.setSelectedPosition(CTUser.getInstance().getCurrentPatientUsername());
		int dialogSelectedIndex = dialogListAdapter.getSelectedIndex();
		
		if (DialogListAdapter.NO_PATIENT_SELECTED != dialogSelectedIndex) {

			list.setSelection(dialogSelectedIndex);
			list.setItemChecked(dialogSelectedIndex, true);
		}
		
		// get activity context
		final Context theContext = this;
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long arg3) {

				// Zeroth item is the "add patient" option
				if (ADD_PATIENT_SELECTION == pos) {
					// add patient
					// go to signup activity with the "add patient" URL ... include my ID and access token
				    Intent intent = new Intent(theContext, WebSignupActivity.class);
				    String urlString = WebSignupActivity.CLINICIAN_ADD_PATIENT_URL + CTUser.getInstance().getUserId();
				    urlString += "/" + CTUser.getInstance().getAccessToken();
				    intent.putExtra(WebSignupActivity.EXTRA_URL_KEY, urlString);
				    startActivityForResult(intent, RESULT_CODE_ADD_PATIENT);
				    // since we're going to another activity, summarily dismiss the selector
				    popoverView.dismissPopover(false);
				} else {
					
					// selected something other than add patient ... remember that we put
					// blank entries in after the real ones so check to see if the selection
					// is one of the blank entries, and if it is, ignore the click
					if (((PatientModel)list.getItemAtPosition(pos)).getUsername().length() > 0) {
						boolean checked = list.isItemChecked(pos);
						ImageView imTick = (ImageView) v
								.findViewById(R.id.imageTick);
	
						if (checked) {
							dialogListAdapter.setSelectedIndex(pos);
							if (imTick.getVisibility() == View.INVISIBLE) {
	
								changePatientConfirmAndExecute(dialogListAdapter.getSelectedItem());
							}
							list.setItemChecked(pos, true);
							imTick.setVisibility(View.VISIBLE);
						} else {
							list.setItemChecked(pos, false);
							imTick.setVisibility(View.INVISIBLE);
						}
	
					} 
					// why where we even doing this?
					//else {
					//	list.setItemChecked(selectedPosition, true);
					//}
				}

			}
		});

	}

	/** ask user to confirm change of selected patient - default implementation - 
	 * for your derived class you may want to change how the OnClickListener()
	 * behaves ... which would be a great place for the new Java 8 lambda expressions */
	protected void changePatientConfirmAndExecute(final PatientModel item) {
		final Dialog dialog = new Dialog(this, R.style.DialogAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_logout);
		dialog.setCancelable(false);
		CTTextView tvTitle = (CTTextView) dialog.findViewById(R.id.tvTitle);
		CTTextView tvMessage = (CTTextView) dialog.findViewById(R.id.tvMessage);
		tvMessage.setVisibility(View.VISIBLE);

		tvTitle.setText("Change the current patient?");

		tvMessage
				.setText("Do you want to change the currently selected patient?");

		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		Button okbtn = (Button) dialog.findViewById(R.id.btnok);
		// if button is clicked, close the custom dialog
		dialogButton.setText("Go Back");
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				popoverViewWillDismiss(popoverView);
				popoverView.removeAllViews();
				dialog.dismiss();
				showPatientDropDown();

			}
		});
		okbtn.setText("Change Patient");
		okbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				selectAndRefreshPatient(item);

			}
		});

		dialog.show();
	}
	
	public void updatePopup(String eAddr, String pno, String fName, String lName) {
		click = 0;
		updateDialog = new Dialog(this, R.style.DialogAnim);
		updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		updateDialog.setContentView(R.layout.update_info);
		updateDialog.setCancelable(false);

		final EditText email = (EditText) updateDialog
				.findViewById(R.id.txt_Email);
		final EditText phno = (EditText) updateDialog
				.findViewById(R.id.txt_phn);
		final EditText fname = (EditText) updateDialog
				.findViewById(R.id.txt_fname);
		final EditText lname = (EditText) updateDialog
				.findViewById(R.id.txt_lname);

		Button btnChangePwd = (Button) updateDialog
				.findViewById(R.id.btnChangePwd);

		Button cancel = (Button) updateDialog.findViewById(R.id.cancel);
		Button save = (Button) updateDialog
				.findViewById(R.id.updateSave);

		email.setText(eAddr);
		phno.setText(pno);
		fname.setText(fName);
		lname.setText(lName);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(click == 0){
					click = 1;
					String updateEmail = email.getText().toString();
					String updatePhone = phno.getText().toString();
					String updateFname = fname.getText().toString();
					String updateLname = lname.getText().toString();
	
					if (CTUser.getInstance().getEmail().equalsIgnoreCase(updateEmail)) {
	
						// no change to email address
						CTUser.getInstance().updateAccountInfo(updateEmail, updatePhone, updateLname, updateFname);
						updateAccountInfo();
	
					} else if (isValidEmail(updateEmail)) {
	
						// email changed ... and it's in the form of an email
						// ... make sure server is OK with it e.g. it's not a duplicate
						CTUser.getInstance().updateAccountInfo(updateEmail, updatePhone, updateLname, updateFname);
						callValidateService(updateEmail);
						
					} else {
						
						// changed, not valid email format
						showInternetAlert(getString(R.string.oop),
								getString(R.string.validEmail));
					}

			}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDialog.dismiss();

			}
		});

		btnChangePwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(click == 0)
					changePassword();
			}
		});

		updateDialog.show();

		Window window = updateDialog.getWindow();
		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		setWindowLayoutDimensions(wm, window, 3.5f, 2.25f);
	}

	public void changePassword() {

		passwordDialog = new Dialog(this, R.style.DialogAnim);
		passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		passwordDialog.setContentView(R.layout.change_password);
		passwordDialog.setCancelable(false);

		CTTextView pwdCancel = (CTTextView) passwordDialog
				.findViewById(R.id.pwdCancel);

		final Button btnSubmit = (Button) passwordDialog
				.findViewById(R.id.btnSubmit);

		final EditText txt_repeatPwd = (EditText) passwordDialog
				.findViewById(R.id.txt_repPwd);

		final String title = getString(R.string.error_password_title);
		final String msg = getString(R.string.error_msg_pwd_change);
		final String isEmpty = getString(R.string.is_empty);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String currentPassword = ((EditText)passwordDialog.findViewById(R.id.txt_currPwd)).getText().toString();
				String newPassword = ((EditText)passwordDialog.findViewById(R.id.txt_newPwd)).getText().toString();
				String repeatPassword = ((EditText) passwordDialog.findViewById(R.id.txt_repPwd)).getText().toString();

				btnSubmit.setClickable(false);
				if ((currentPassword.length() == 0 || newPassword.length() == 0 || repeatPassword
						.length() == 0)) {
					showInternetAlert(getString(R.string.oop), isEmpty);
					btnSubmit.setClickable(true);
				}

				else {

					if (!CTUser.getInstance().getPassword().equalsIgnoreCase(currentPassword)) {
						showInternetAlert(title, msg);
						btnSubmit.setClickable(true);
					} else if (!confirmPasswordValidate(newPassword,
							repeatPassword)) {
						btnSubmit.setClickable(true);
						showInternetAlert(getString(R.string.oop),
								getString(R.string.mismatch));
					} else {
						postChangedPassword(CTUser.getInstance().getUsername(), currentPassword,
								newPassword);
					}

				}

			}
		});

		pwdCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				passwordDialog.dismiss();

			}
		});
		passwordDialog.show();
		Window window = passwordDialog.getWindow();
		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		setWindowLayoutDimensions(wm, window, 3.5f, 2.25f);
	}
	
	/** post changed password to web service ... note that we don't take the old password
	 * from CTUser since this is something the user entered, and the server is gonna
	 * verify it */
	public void postChangedPassword(String username, String oldPassword, String newPassword) {
		CTUser.getInstance().cacheProspectiveNewPassword(newPassword);
		String urlChangePwd = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/"
				+ getString(R.string.changepassword);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CHANGE_PASSWORD_TOKEN, urlChangePwd, username,
				oldPassword, newPassword);
	}

	/** compare passwords to tell if they're the same */
	public boolean confirmPasswordValidate(String password,
			String confirmPassword) {
		boolean pstatus = false;

		if (password.equalsIgnoreCase(confirmPassword)) {
			pstatus = true;

		}
		Log.v(LOG_TAG, "Password validate result:" + pstatus);
		return pstatus;
	}

	/** call the web service to validate an email address */
	protected void callValidateService(String email) {

		String urlValidate = getString(R.string.remote_preurl)
				+ getString(R.string.validate_email);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.VALIDATE_EMAIL_TOKEN, urlValidate, email, false,
				"get");

	}
	
	/** set content size for the view in a popover as a proportion of current display */
	@SuppressWarnings("deprecation")
	public void setWindowLayoutDimensions(WindowManager wm, Window theWindow, float widthFactor, float heightFactor) {
		
		Display display = wm.getDefaultDisplay();
		
		Point size = new Point();
		// getWidth and getHeight are deprecated, but before SDK 13 the replacement getSize
		// is not available ... technically we are not compatible with pre-SKD-13 anyhow
		// but this is an easy safety measure
		if (android.os.Build.VERSION.SDK_INT >= 13)
		{
		    display.getSize(size);
		}
		else
		{
			size.x = display.getWidth();
			size.y = display.getHeight();
		}

		// ESR: not sure why we are adding the size of the view to the size of the
		// window ... it seems likely that the view size is already included in the window size ...
		float width = findViewById(R.id.container).getWidth() + size.x;
		float height = findViewById(R.id.container).getHeight() + size.y;
		
		theWindow.setLayout((int) (width / widthFactor), (int) (height / heightFactor));
	}
	
	/** update the user account info e.g. email, phone, first & last name 
	 * ... duplicate of TasksActivity.callUpdateService() except that TasksActivity
	 * uses Constants.UPDATE_ACCOUNT_INFO instead of Constants.UPDATE_ACCOUNT_TOKEN */
	public void updateAccountInfo() {
						
		// NOTE: these service calls do NOT result in updates to providers!  They use the
		// BaselineProcessor!
		
		String urlEmail = this.getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + CTUser.getInstance().getUserId()
				+ getString(R.string.email) + CTUser.getInstance().getEmail();
		ServiceHelper.execute(this, mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlEmail);

		String urlPhno = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + CTUser.getInstance().getUserId()
				+ getString(R.string.phonenumber) + CTUser.getInstance().getPhoneNumber();
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlPhno);

		String urlFname = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + CTUser.getInstance().getUserId()
				+ getString(R.string.firstname) + CTUser.getInstance().getFirstName();
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlFname);

		String urlLname = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + CTUser.getInstance().getUserId()
				+ getString(R.string.lastname) + CTUser.getInstance().getLastName();
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlLname);
		
		// those web service calls don't return anything that can update our provider...
		// though it's not clear that we need to keep updated info in the provider,
		// we actually haven't made that decision yet
		CTUser.getInstance().putAccountInfoIntoProvider();
	}

	public boolean isValidEmail(String inputEmail) {
		if (inputEmail == null) {
			return false;
		} else {
			Log.v(LOG_TAG, inputEmail);
			return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail)
					.matches();
		}
	}

	/** deals with any consequences of selecting a new current patient and updating the display 
	 * ... used by changePatientConfirmAndExecute() */
	abstract protected void selectAndRefreshPatient(PatientModel selectedPatient); 	

	/** generic implementation of showInternetAlert() ... derived classes
	 * will probably have different behavior because they will want to refresh
	 * one or the other dialog when the alert is dismissed, because once the user
	 * has acknowledged that Internet was an issue their "OK" is essentially treated
	 * as a "retry" assent */
	protected void showInternetAlert(String title, String message) {
		this.showGenericOkDialog(title, message);
	}

	/** insert updated account info data into the CTUser and from thenc into the provider ... the web service
	 * endpoints we use to update this data don't return anything that we can
	 * put in our provider so if we want the provider to remain in sync we have
	 * to put the info there ourselves */
	protected void storeUpdatedAccountInfo(String eAddr, String pno, String fName,
			String lName) {
		CTUser.getInstance().updateAccountInfo(eAddr,  pno,  lName,  fName);
		CTUser.getInstance().putAccountInfoIntoProvider();
	}


}
