package org.espier.note7.activity;

import java.util.ArrayList;
import java.util.List;

import org.espier.note7.R;
import org.espier.note7.adapter.NoteAdapter;
import org.espier.note7.db.DatabaseHelper;
import org.espier.note7.model.Note;
import org.espier.note7.view.MyListView;
import org.espier.note7.view.MyListView.OnRefreshListener;
import org.espier.note7.view.UINavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NoteListActivity extends BaseAcvitity {
	private UINavigation navigation;
	public TextView tvRight, tvCancel;
	private LinearLayout llLeft,llSearch;
	public MyListView listView;
	private DatabaseHelper databaseHelper;
	public NoteAdapter adapter, dialogAdapter;
	private List<Note> items;
	private boolean isNull = false;
	private EditText etSearch, etDialogSearch;
	public Dialog baseDialog;
	public LayoutInflater mInflater;
	private ListView lvDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		navigation = (UINavigation) findViewById(R.id.navigation);
		tvRight = (TextView) navigation.findViewById(R.id.tv_right);
		llLeft = (LinearLayout) navigation.findViewById(R.id.ll_left);
		llLeft.setVisibility(View.GONE);
		// tvRight.setText("    +    ");

		listView = (MyListView) findViewById(R.id.listView);
		llSearch =(LinearLayout)findViewById(R.id.ll_head);
		
		llSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				llSearch.setVisibility(View.INVISIBLE);
				navigation.setVisibility(View.GONE);
				baseDialog = getDialog(NoteListActivity.this);
				etDialogSearch.setHint(getResources().getString(R.string.search_hint));
				etDialogSearch.findFocus();
				tvCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (navigation.getVisibility() == View.GONE
								|| llSearch.getVisibility() == View.INVISIBLE) {
							navigation.setVisibility(View.VISIBLE);
							llSearch.setVisibility(View.VISIBLE);
							baseDialog.dismiss();
						}
					}
				});
				etDialogSearch.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub


					}

					@Override
					public void beforeTextChanged(CharSequence s,
							int start, int before, int count) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						// lvDialog.setBackgroundResource(R.drawable.bg_body);
						String keyWords = etDialogSearch.getText()
								.toString();
						if (!keyWords.equals("")) {
							List<Note> notes = databaseHelper
									.searchNotes(keyWords);
							for (int i = 0; i < notes.size(); i++) {
								System.out.println(notes.get(i).toString());
								dialogAdapter = new NoteAdapter(
										NoteListActivity.this, notes, false);
								lvDialog.setAdapter(dialogAdapter);
							}
						}


					}
				});
			}
		});
//		etSearch = (EditText) findViewById(R.id.et_search);
//		etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View view, boolean hasfocus) {
//				// TODO Auto-generated method stub
//				if (hasfocus) {
//					
//
//				}
//
//			}
//		});

		tvRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NoteListActivity.this,
						EditNoteActivity.class);
				startActivity(intent);
			}
		});
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

			}

		});

		// detector = new GestureDetector(this, new MyGuestureListener(this));


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		items = getData();
		int count = 0;
		if (null == items || items.size() == 0) {
			isNull = true;
		}
		if (isNull) {
			count = items.size();
		}
		if (!isNull) {
			// listView.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent(NoteListActivity.this,
			// EditNoteActivity.class);
			// intent.putExtra("note", items.get(position - 1));
			// intent.putParcelableArrayListExtra("notes",
			// (ArrayList<? extends Parcelable>) items);
			// intent.putExtra("index", position);
			// startActivity(intent);
			// }
			//
			// });
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, final int position, long id) {
					// TODO Auto-generated method stub
					// CharSequence[] alertItems = { "删除", "取消" };
					// AlertDialog.Builder builder = new AlertDialog.Builder(
					// NoteListActivity.this);
					// builder.setItems(alertItems,
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(DialogInterface dialog,
					// int which) {
					// // TODO Auto-generated method stub
					// if (which == 0) {
					// System.out.println("postion=="
					// + position);
					// System.out.println("id=="
					// + items.get(position - 1)
					// .getId());
					// boolean delete = databaseHelper
					// .deleteNoteById(items.get(
					// position - 1).getId());
					// //
					// System.out.println("position=="+position+","+"delete=="+delete);
					// items = getData();
					// adapter = new NoteAdapter(
					// NoteListActivity.this, items,
					// isNull);
					// listView.setAdapter(adapter);
					// } else if (which == 1) {
					//
					// }
					// }
					// });
					// builder.setTitle("确定要删除吗？");
					// builder.show();
					return false;
				}

			});

		}
		// navigation.setTvTitleText("Notes(" + count + ")");
		System.out.println("mmmmmm");
		adapter = new NoteAdapter(this, items, isNull);
		listView.setAdapter(adapter);

	}

	public List<Note> getData() {
		List<Note> items = new ArrayList<Note>();
		databaseHelper = new DatabaseHelper(this);
		items = databaseHelper.findAllNotes();
		System.out.println("items===size==" + items.size());
		if (items.size() == 0 || items == null) {
			isNull = true;
			for (int i = 0; i < 10; i++) {
				Note note = null;
				if (i == 2) {
					String no = getResources().getString(R.string.no_note);
					note = new Note(3, no, 0, "");
				} else {
					note = new Note(i, "", 0, "");
				}
				items.add(note);
			}
		} else {
			isNull = false;
		}

		return items;
	}
	public Dialog getDialog(Context context) {
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}
		if (baseDialog != null)
			baseDialog = null;
		baseDialog = new Dialog(context, R.style.Transparent);
		baseDialog.setCanceledOnTouchOutside(true);
		// dialog.
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout mLayout = (LinearLayout) mInflater.inflate(
				R.layout.dialog_search, null);
		etDialogSearch = (EditText) mLayout.findViewById(R.id.et_search);
		tvCancel = (TextView) mLayout.findViewById(R.id.tv_cancel);
		lvDialog = (ListView) mLayout.findViewById(R.id.lv_dialog);
		lvDialog.setBackground(null);
		baseDialog.getWindow().setContentView(mLayout, lp);

		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(INPUT_METHOD_SERVICE);
		// imm.showSoftInput(mLayout, 0);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		Activity a = null;
		if (context instanceof Activity) {
			a = (Activity) context;
		}
		baseDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (navigation.getVisibility() == View.GONE
							|| llSearch.getVisibility() == View.INVISIBLE) {
						navigation.setVisibility(View.VISIBLE);
						llSearch.setVisibility(View.VISIBLE);
						baseDialog.dismiss();
					}
					return true;
				} else {
					return false; // 默认返回 false
				}
			}
		});
		if (a != null && !a.isFinishing() && !baseDialog.isShowing())
			baseDialog.show();
		return baseDialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			items = getData();
			System.out.println("nnnnnnnnnn");
			adapter.notifyDataSetChanged();
		}

	}



}
