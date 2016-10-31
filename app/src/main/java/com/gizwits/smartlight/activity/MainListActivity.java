/**
lampW_frameG.png
lampW_frameW.png
lampY_frameG.png
lampY_frameY.png * Project Name:XPGSdkV4AppBase
 * File Name:MainControlActivity.java
 * Package Name:com.gizwits.centercontrolled.activity.control
 * Date:2015-1-27 14:44:17
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.smartlight.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceManageListActivity;
import com.gizwits.framework.activity.help.AboutActivity;
import com.gizwits.framework.activity.help.HelpActivity;
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.Configs;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.DeviceAlarm;
import com.gizwits.framework.entity.GroupDevice;
import com.gizwits.framework.sdk.CmdCenter;
import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.RefreshableListView;
import com.gizwits.framework.widget.RefreshableListView.OnRefreshListener;
import com.gizwits.framework.widget.SlidingMenu;
import com.gizwits.light.R;
import com.gizwits.smartlight.adapter.GroupAdapter;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiCentralControlDevice;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiGroup;
import com.xtremeprog.xpgconnect.XPGWifiSubDevice;

// TODO: Auto-generated Javadoc
/**
 * Created by Lien on 14/12/21.
 * 
 * 设备主控界面
 * 
 * @author Lien
 */
public class MainListActivity extends BaseActivity implements OnClickListener {

	/** The tag. */
	private final String TAG = "MainControlActivity";
	// private XPGWifiDevice device;

	/** The scl content. */
	private RefreshableListView sclContent;

	/** The m view. */
	private SlidingMenu mView;

	/** the btn alpha bg */
	private Button alpha_bg;
	
	/** the ll foot. */
	private LinearLayout llFooter;

	/** The ll bottom. */
	private LinearLayout llBottom;
	
	/** The iv Edit. */
	public ImageView ivEdit;

	/** The iv menu. */
	private ImageView ivMenu;

	/** The tv title. */
	private TextView tvTitle;
	
	/** the TextVIew light_name */
	public TextView tvLName;
	
	/** the iv bottom edit_group Btn*/
	public ImageView etGroup;
	
	/** the TextView turn on off */
	private TextView btnSwitch;
	
	/** the sb light adjust */
	public SeekBar sbLightness;

	/** The m adapter. */
	private MenuDeviceAdapter mAdapter;

	/** The lv device. */
	private ListView lvDevice;
	
	/** the pdialog getStatusProgress*/
	private ProgressDialog getStatusProgress;

	/** The device data map. */
	private ConcurrentHashMap<String, Object> deviceDataMap;

	/** The statu map. */
	private Map<String, Object> statuMap;

	/** The progress dialog. */
	private ProgressDialog progressDialog;
	
	/** The disconnect dialog. */
	private Dialog mDisconnectDialog;

	/** The XPGWifiCentralControlDevice centralControlDevice */
	public XPGWifiCentralControlDevice centralControlDevice;
	
	/** the list device 
	 *  the key listitem name
	 *  the value listitem contains ledlist sdid */
	public Map<String, List<GroupDevice>> mapList=new HashMap<String, List<GroupDevice>>();
	/** the list groupList 
	 * 	the key group name
	 *  the value group contains ledlist sdid */
	public Map<String, List<String>> groupMapList = new HashMap<String, List<String>>();
	/** the list ledList */
	public List<GroupDevice> ledList = new ArrayList<GroupDevice>();
	/** the listview item namelist */
	public List<String> list = new ArrayList<String>();
	/** the list Delete Btn list */
	public List<ImageView> ivDels = new ArrayList<ImageView>();
	
	/** the wifisubdevice status subDevice */
	XPGWifiSubDevice subDevice;
	
	/** the Drawable pic white light */
	public Drawable wLight;
	/** the Drawable pic yellow light */
	public Drawable yLight;
	/** the Drawable pic white light select */
	public Drawable wLightSelect;
	/** the Drawable pic yellow light select */
	public Drawable yLightSelect;
	/** the Drawable pic add */
	public Drawable add;
	/** the Drawable pic power_on */
	public Drawable power_on;
	/** the Drawable pic power_off */
	public Drawable power_off;
	
	/** the groupadapter devicelist */
	public GroupAdapter mGroupAdapter;
	
	/** the tv selecting tv */
	public TextView selecttv;
	
	/** the XPGWifiSubDevice selcetSubDevice */
	public XPGWifiSubDevice selectSubDevice;
	
	/** the String select group name */
	public String selectGroup = "";
	
	/** the device select show Item */
	public List<String> showItemDevices = new ArrayList<String>();

	/** 是否超时标志位 */
	private boolean isTimeOut = false;
	
	/** 底部控制延时 */
	private long switchTime = 0;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** 更新UI界面 */
		UPDATE_UI,

		/** 显示警告 */
		ALARM,

		/** 设备断开连接 */
		DISCONNECTED,

		/** 接收到设备的数据 */
		RECEIVED,

		/** The login start. */
		LOGIN_START,

		/**
		 * The login success.
		 */
		LOGIN_SUCCESS,

		/**
		 * The login fail.
		 */
		LOGIN_FAIL,

		/**
		 * The login timeout.
		 */
		LOGIN_TIMEOUT,
		
		/**
		 * The device get status
		 */
		DEVICE_GETSTATUS,

		/**
		 * The device get status
		 */
		CMD_SWITCH,

		/**
		 * The device get status
		 */
		CMD_LIGHT,
	}

	/**
	 * The handler.
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case DEVICE_GETSTATUS:
				XPGWifiSubDevice subObj = (XPGWifiSubDevice) msg.obj;
				mCenter.cGetSubStatus(subObj);
				break;
			case RECEIVED:
				try {
					if (deviceDataMap.get("data") != null) {
						inputDataToMaps(statuMap,
								(String) deviceDataMap.get("data"));

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case UPDATE_UI:
				//状态置入本地LedList状态列表
				for (int i = 0; i < ledList.size(); i++) {
					try {
						if (ledList.get(i).getSubDevice().getSubDid().equals(subDevice.getSubDid())) {
							ledList.get(i).setOnOff((Boolean) statuMap.get(JsonKeys.ON_OFF));
							ledList.get(i).setLightness(Integer.parseInt(statuMap.get(JsonKeys.LIGHTNESS).toString()));
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				//*****************************改变组状态控制栏**************************************
				boolean isOk = false;
				if (selectGroup != "") {
					for (int j = 0; j < groupMapList.get(selectGroup).size(); j++) {
						for (int i = 0; i < ledList.size(); i++) {
							if (ledList.get(i).getSubDevice().getSubDid().equals(groupMapList.get(selectGroup).get(j))) {
								if(ledList.get(i).isOnOff()){
									switchOn();
								}else {
									switchOff();
								}
								isOk = true;
								break;
							}
						}
						if (isOk) {
							break;
						}
					}
				}
				//********************************************************************************
				myGroupAdapterNotify();
				break;
			case DISCONNECTED:
				Log.e(TAG, "disconnnect");
				DialogManager.showDialog(MainListActivity.this, mDisconnectDialog);
				break;
			case LOGIN_SUCCESS:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				if (mView.isOpen()) {
					mView.toggle();
				}
				onResume();
				break;
			case LOGIN_FAIL:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
				break;
			case LOGIN_TIMEOUT:
				Log.e(TAG, "timeout");
				isTimeOut = true;
				progressDialog.cancel();
				handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
				if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
					mCenter.cDisconnect(mXpgWifiDevice);
					DisconnectOtherDevice();
				}
				break;
			case CMD_SWITCH:
				mCenter.cSwitchOn((XPGWifiSubDevice) msg.obj, msg.arg1 == 0);
				break;
			case CMD_LIGHT:
				mCenter.cLightness((XPGWifiSubDevice) msg.obj, msg.arg1);
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#onCreate(android.os
	 * .Bundle )
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_control);
		initViews();
		initEvents();
		initParams();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.centercontrolled.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		refreshMenu();
		
		//中控监听
		Log.e("centralControlsetListener", "centralControlsetListener");
		centralControlDevice = (XPGWifiCentralControlDevice) mXpgWifiDevice;
		centralControlDevice.setListener(xpgWifiCentralControlDeviceListener);

		Log.e("GetSubDevices", "GetSubDevices");
		mCenter.cGetSubDevicesList(centralControlDevice);//获取子设备
		mCenter.cGetGroups(setmanager.getUid(), setmanager.getToken(), Configs.PRODUCT_KEY_Sub);//获取组
		
		bottomClose();
		ledList.clear();
		
		showItemDevices.clear();
		
		//展开3秒状态获取Loadding框
		getStatusProgress.show();
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getStatusProgress.cancel();
				timer.cancel();
			}
		}, 3000);
	}
	
	/**
	 * 更新菜单界面.
	 * 
	 * @return void
	 */
	private void refreshMenu() {
		initBindList();
		mAdapter.setChoosedPos(-1);
		for (int i = 0; i < bindlist.size(); i++) {
			if (bindlist.get(i).getDid()
					.equalsIgnoreCase(mXpgWifiDevice.getDid()))
				mAdapter.setChoosedPos(i);
		}
		
		//当前绑定列表没有当前操作设备
		if(mAdapter.getChoosedPos()==-1){
			mAdapter.setChoosedPos(0);
			mXpgWifiDevice= mAdapter.getItem(0);
		}
			
		mAdapter.notifyDataSetChanged();
		
		int px = DensityUtil.dip2px(this, mAdapter.getCount() * 50);
		lvDevice.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, px));
	}

	/**
	 * Inits the params.
	 */
	private void initParams() {
		//初始化获取Drawable图片
		wLight = this.getResources().getDrawable(R.drawable.lampw_framew); 
		wLight.setBounds(0, 0, wLight.getMinimumWidth(), wLight.getMinimumHeight());
		yLight = this.getResources().getDrawable(R.drawable.lampy_framey); 
		yLight.setBounds(0, 0, yLight.getMinimumWidth(), yLight.getMinimumHeight());
		wLightSelect = this.getResources().getDrawable(R.drawable.lampw_frameg); 
		wLightSelect.setBounds(0, 0, wLightSelect.getMinimumWidth(), wLightSelect.getMinimumHeight());
		yLightSelect = this.getResources().getDrawable(R.drawable.lampy_frameg); 
		yLightSelect.setBounds(0, 0, yLightSelect.getMinimumWidth(), yLightSelect.getMinimumHeight());
		power_on = this.getResources().getDrawable(R.drawable.icon_power); 
		power_on.setBounds(0, 0, power_on.getMinimumWidth(), power_on.getMinimumHeight());
		power_off = this.getResources().getDrawable(R.drawable.icon_power_off); 
		power_off.setBounds(0, 0, power_on.getMinimumWidth(), power_on.getMinimumHeight());
		add = this.getResources().getDrawable(R.drawable.icon_add); 
		add.setBounds(0, 0, add.getMinimumWidth(), add.getMinimumHeight());
		
		statuMap = new ConcurrentHashMap<String, Object>();
		
		listViewSetNormal();
		
		mGroupAdapter = new GroupAdapter(this, mapList, list);
		sclContent.setAdapter(mGroupAdapter);

		getStatusProgress = new ProgressDialog(this);
		getStatusProgress.setMessage("获取灯状态...");
		getStatusProgress.setCancelable(false);
		
		mDisconnectDialog = DialogManager.getDisconnectDialog(this,
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogManager.dismissDialog(MainListActivity.this,
								mDisconnectDialog);
						IntentUtils.getInstance().startActivity(
								MainListActivity.this,
								DeviceListActivity.class);
						finish();
					}
				});
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		mView = (SlidingMenu) findViewById(R.id.main_layout);
		
		llFooter = (LinearLayout) findViewById(R.id.llFooter);
		alpha_bg = (Button) findViewById(R.id.black_alpha_bg);
		tvLName = (TextView) findViewById(R.id.show_led_name);
		etGroup = (ImageView) findViewById(R.id.edit_group);
		llBottom = (LinearLayout) findViewById(R.id.llBottom);
		ivMenu = (ImageView) findViewById(R.id.ivMenu);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivEdit = (ImageView) findViewById(R.id.ivEdit);
		ivEdit.setTag("1");

		sclContent = (RefreshableListView) findViewById(R.id.sclContent);

		btnSwitch = (TextView) findViewById(R.id.btnSwitch);
		sbLightness = (SeekBar) findViewById(R.id.sbLightness);

		mAdapter = new MenuDeviceAdapter(this, bindlist);
		lvDevice = (ListView) findViewById(R.id.lvDevice);
		lvDevice.setAdapter(mAdapter);

		progressDialog = new ProgressDialog(MainListActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("设备连接中，请稍候。");
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ivMenu.setOnClickListener(this);
		tvTitle.setOnClickListener(this);
		llFooter.setOnClickListener(this);
		etGroup.setOnClickListener(this);

		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mAdapter.getItem(position).isOnline())
					return;

				if (mAdapter.getChoosedPos() == position) {
					mView.toggle();
					return;
				}

				mAdapter.setChoosedPos(position);
				mXpgWifiDevice = bindlist.get(position);
				loginDevice(mXpgWifiDevice);
			}
		});
		sbLightness.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					sbLightness.getParent().requestDisallowInterceptTouchEvent(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					sbLightness.getParent().requestDisallowInterceptTouchEvent(false);
					break;
				}
				return false;
			}
		});
		sbLightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				if (!selectGroup.equals("") && selectGroup != null) {
					for (int i = 0; i < groupMapList.get(selectGroup).size(); i++) {
						for (int j = 0; j < ledList.size(); j++) {
							if (ledList.get(j).getSubDevice().getSubDid().equals(groupMapList.get(selectGroup).get(i))) {
								Message msg = new Message();
								msg.obj = ledList.get(j).getSubDevice();
								msg.arg1 = seekBar.getProgress();
								msg.what = handler_key.CMD_LIGHT.ordinal();
								handler.sendMessageDelayed(msg, 250*i);
								break;
							}
						}
					}
				}else {
					mCenter.cLightness(selectSubDevice, seekBar.getProgress());
				}
				getLedStatus();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});
		alpha_bg.setOnClickListener(this);
		ivEdit.setOnClickListener(this);
		sclContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == list.size() - 1) {
					Intent intent = new Intent(MainListActivity.this, EditGroupActivity.class);
					intent.putStringArrayListExtra("ledList", GroupDevice.getAllName(ledList));
					intent.putExtra("did", ""+centralControlDevice.getDid());
					startActivity(intent);
				}
			}
		});
		
		sclContent.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(RefreshableListView listView) {
				// TODO Auto-generated method stub
				mCenter.cGetSubDevicesList(centralControlDevice);
				final Timer timer=new Timer();
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						runOnUiThread(new Runnable() {
							public void run() {
								sclContent.completeRefreshing();
							}
						});
						timer.cancel();
					}
				}, 2000);
			}
		});
		btnSwitch.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
		case R.id.ivMenu:
			mView.toggle();
			break;
		case R.id.btnSwitch:
			//每0.6秒智能操控一次，避免频繁操控
			if (switchTime + 600 > System.currentTimeMillis()) {
				return;
			}
			switchTime = System.currentTimeMillis();
			if (!selectGroup.equals("") && selectGroup != null) {
				//获取ledList中存在组的设备进行控制
				for (int i = 0; i < groupMapList.get(selectGroup).size(); i++) {
					for (int j = 0; j < ledList.size(); j++) {
						if (ledList.get(j).getSubDevice().getSubDid().equals(groupMapList.get(selectGroup).get(i))) {
							Message msg = new Message();
							msg.obj = ledList.get(j).getSubDevice();
							msg.what = handler_key.CMD_SWITCH.ordinal();
							if (btnSwitch.getText().toString().equals("关灯")){
								msg.arg1 = 1;
							}else{
								msg.arg1 = 0;
							}
							
							handler.sendMessageDelayed(msg, 250*i);
						}
					}
				}
			}else {
				//单设备控制
				if (btnSwitch.getText().toString().equals("关灯")) {
					mCenter.cSwitchOn(selectSubDevice, false);
				}else{
					mCenter.cSwitchOn(selectSubDevice, true);
				}
			}
			break;
		case R.id.edit_group:
			//底部菜单组编辑按钮
			Intent intent = new Intent(MainListActivity.this, EditGroupActivity.class);
			intent.putStringArrayListExtra("ledList", GroupDevice.getAllName(ledList));
			intent.putStringArrayListExtra("groupList", (ArrayList<String>) groupMapList.get(v.getTag().toString()));
			intent.putExtra("groupName", v.getTag().toString());
			intent.putExtra("did", ""+mXpgWifiDevice.getDid());
			startActivity(intent);
			break;
		case R.id.black_alpha_bg:
			bottomClose();
			break;
		case R.id.ivEdit:
			//右上角编辑按钮，点开展示所有删除按钮
			Log.e("showDel", ""+ivDels.size());
			bottomClose();
			
			if (ivEdit.getTag().toString().equals("1")) {
				ivEdit.setImageResource(R.drawable.icon_confirm);
				ivEdit.setTag("0");
			}else{
				ivEdit.setImageResource(R.drawable.icon_edit_w);
				ivEdit.setTag("1");
			}
			
			if (ivDels.size() < 1) {
				return;
			}
			
			if (ivEdit.getTag().toString().equals("0")) {
				for (ImageView ivDel : ivDels) {
						ivDel.setVisibility(View.VISIBLE);
				}
			}else{
				for (ImageView ivDel : ivDels) {
					ivDel.setVisibility(View.INVISIBLE);
				}
			}
			break;
		}
	}
	
	/**
	 * 展开底部控制栏
	 */
	public void bottomShow(){
        llBottom.setVisibility(View.VISIBLE);
        alpha_bg.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 关闭底部控制栏
	 */
	public void bottomClose(){
        llBottom.setVisibility(View.GONE);
        alpha_bg.setVisibility(View.GONE);
        if (selecttv != null) {
			if (((GroupDevice)selecttv.getTag()).isOnOff()) {
				selecttv.setCompoundDrawables(null,yLight,null,null);
			}else{
				selecttv.setCompoundDrawables(null,wLight,null,null);
			}
		}
        selectSubDevice = null;
        selectGroup = "";
        selecttv = null;
	}
	
	/**
	 * 开灯状态下，底部按钮switch
	 */
	public void switchOn(){
		btnSwitch.setText("关灯");
    	btnSwitch.setTextColor(getResources().getColor(R.color.text_blue));
    	btnSwitch.setCompoundDrawables(null,power_on,null,null);
	}
	
	/**
	 * 关灯状态下，底部按钮switch
	 */
	public void switchOff(){
		btnSwitch.setText("开灯");
    	btnSwitch.setTextColor(getResources().getColor(R.color.text_gray));
    	btnSwitch.setCompoundDrawables(null,power_off,null,null);
	}

	/**
	 * 左侧菜单栏点击时间
	 * @param view 点击item项
	 */
	public void onClickSlipBar(View view) {
		switch (view.getId()) {
		case R.id.rlDevice:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					DeviceManageListActivity.class);
			break;
		case R.id.rlAbout:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					AboutActivity.class);
			break;
		case R.id.rlAccount:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					UserManageActivity.class);
			break;
		case R.id.rlHelp:
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					HelpActivity.class);
			break;
		case R.id.btnDeviceList:
			mCenter.cDisconnect(mXpgWifiDevice);
			DisconnectOtherDevice();
			IntentUtils.getInstance().startActivity(MainListActivity.this,
					DeviceListActivity.class);
			finish();
			break;
		}
	}

	/**
	 * Login device.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		DisconnectOtherDevice();
		mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
		progressDialog.show();
		isTimeOut = false;
		handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(),
				5000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		if (isTimeOut)
			return;

		if (result == 0) {
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}
	
	/**
	 * 获取子设备灯状态
	 */
	public void getLedStatus(){
		for (int i = 0; i < ledList.size(); i++) {
			Message msg=new Message();
			msg.what = handler_key.DEVICE_GETSTATUS.ordinal();
			msg.obj = ledList.get(i).getSubDevice();
			handler.sendMessage(msg);
		}
	}

	/**
	 * 设置灯数据,每一次获取子设备，都重新设置maplist,list
	 * @param subDeviceList 如为空则不重置ledList
	 */
	public void putStatusToViewList(List<XPGWifiSubDevice> subDeviceList){
		list.clear();
		mapList.clear();
		
		mapList.put("light", ledList);
		list.add("light");
		
		if (subDeviceList != null) {
			ledList = GroupDevice.getGroupDeviceByList(subDeviceList);
		}
		mapList.put("我的LED", ledList);
		list.add("我的LED");
		
		mapList.put("group", ledList);
		list.add("group");
		
		for (int i = 0; i < grouplist.size(); i++) {
			list.add(grouplist.get(i).groupName);
			List<GroupDevice> gDevices = new ArrayList<GroupDevice>();
			for (int j = 0; j < groupMapList.get(grouplist.get(i).groupName).size(); j++) {
				GroupDevice gDevice = new GroupDevice();
				gDevice.setSdid(Integer.parseInt(groupMapList.get(grouplist.get(i).groupName).get(j)));
				gDevices.add(gDevice);
			}
			mapList.put(grouplist.get(i).groupName, gDevices);
		}
		
		mapList.put("addGroup", ledList);
		list.add("addGroup");
		
	}
	
	/**
	 * 重置ListView数据
	 */
	public void listViewSetNormal(){
		list.clear();
		mapList.clear();
		mapList.put("light", ledList);
		list.add("light");
		mapList.put("我的LED", ledList);
		list.add("我的LED");
		mapList.put("group", ledList);
		list.add("group");
		mapList.put("addGroup", ledList);
		list.add("addGroup");
	}
	
	/**
	 * 更新Adapter状态
	 */
	public void myGroupAdapterNotify(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ivDels.size() > 0) {
					ivDels.clear();
				}
				mGroupAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 检查出了选中device，其他device有没有连接上
	 * 
	 * @param mac
	 *            the mac
	 * @param did
	 *            the did
	 * @return the XPG wifi device
	 */
	private void DisconnectOtherDevice() {
		for (XPGWifiDevice theDevice : bindlist) {
			if (theDevice.isConnected()
					&& !theDevice.getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
				mCenter.cDisconnect(theDevice);
		}
		for (GroupDevice theDevice : ledList) {
			if (theDevice.getSubDevice().isConnected()
					&& !theDevice.getSubDevice().getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
				mCenter.cDisconnect(theDevice.getSubDevice());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#didReceiveData(com
	 * .xtremeprog .xpgconnect.XPGWifiDevice,
	 * java.util.concurrent.ConcurrentHashMap, int)
	 */
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		subDevice = (XPGWifiSubDevice) device;
		deviceDataMap = dataMap;
		Log.e(TAG, "data " + dataMap.toString());
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
		
	}
	
	@Override
	protected void didSubDiscovered(int error,
			List<XPGWifiSubDevice> subDeviceList) {
		// TODO Auto-generated method stub
		super.didSubDiscovered(error, subDeviceList);
		Log.e(TAG, "getSubDevices size : "+subDeviceList.size());
		
		putStatusToViewList(subDeviceList);
		
		myGroupAdapterNotify();
		
		for (int i = 0; i < subDeviceList.size(); i++) {
			subDeviceList.get(i).setListener(deviceListener);
		}
		getLedStatus();
	}
	
	@Override
	protected void didGetGroups(int error, List<XPGWifiGroup> groupList2) {
		// TODO Auto-generated method stub
		super.didGetGroups(error, groupList2);
		String gid = "";
		if (groupList2 == null) {
			return;
		}
		if (groupList2.size() == 0) {
			listViewSetNormal();

			grouplist.clear();
			
			myGroupAdapterNotify();
			return;
		}
		if (!selectGroup.equals("") && selectGroup != null) {
			for (int i = 0; i < grouplist.size(); i++) {
				if (grouplist.get(i).groupName.equals(selectGroup)) {
					gid = grouplist.get(i).gid;
				}
			}
		}
		grouplist.clear();
		for (int i = 0; i < groupList2.size(); i++) {
			if (!gid.equals("") && gid.equals(groupList2.get(i).gid)) {
				tvLName.setText(groupList2.get(i).groupName);
			}
			grouplist.add(groupList2.get(i));
			groupList2.get(i).setListener(xpgWifiGroupListener);
			mCenter.cGetGroupDevices(groupList2.get(i));
		}
	}

	//每一次获取组内子设备，都重新设置maplist,list
	@Override
	protected void didGetDevices(int error,
			List<ConcurrentHashMap<String, String>> devicesList) {
		// TODO Auto-generated method stub
		super.didGetDevices(error, devicesList);
		if (devicesList == null) {
			return;
		}
		Log.e("didGetDevices", ""+devicesList.toString());
		List<String> strings=new ArrayList<String>();
		for (int i = 0; i < devicesList.size(); i++) {
			strings.add(devicesList.get(i).get("sdid"));
		}
		groupMapList.put(grouplist.get(grouplist.size()-1).groupName, strings);
		
		putStatusToViewList(null);
		
		myGroupAdapterNotify();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		bottomClose();
		if (mView.isOpen()) {
			mView.toggle();
		} else {
			if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
				mCenter.cDisconnect(mXpgWifiDevice);
				DisconnectOtherDevice();
			}
			finish();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.centercontrolled.activity.BaseActivity#didDisconnected(com
	 * .xtremeprog .xpgconnect.XPGWifiDevice)
	 */
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		Log.e(TAG, "disconnect");
		if (!device.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid()))
			return;
			
		handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
	}
	
	/**
	 * 把状态信息存入表
	 * 
	 * @param map
	 *            the map
	 * @param json
	 *            the json
	 * @throws JSONException
	 *             the JSON exception
	 */
	private void inputDataToMaps(Map<String, Object> map,String json) throws JSONException {
		Log.i("revjson", json);
		JSONObject receive = new JSONObject(json);
		Iterator actions = receive.keys();
		while (actions.hasNext()) {

			String action = actions.next().toString();
			Log.i("revjson", "action=" + action);
			// 忽略特殊部分
			if (action.equals("cmd") || action.equals("qos")
					|| action.equals("seq") || action.equals("version")) {
				continue;
			}
			JSONObject params = receive.getJSONObject(action);
			Log.i("revjson", "params=" + params);
			Iterator it_params = params.keys();
			while (it_params.hasNext()) {
				String param = it_params.next().toString();
				Object value = params.get(param);
				map.put(param, value);
				Log.i(TAG, "Key:" + param + ";value" + value);
			}
		}
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}
	
	//出现discovered后，重置存储中匹配的mXpgWifiDevice，重新设置Listener
	@Override
	protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
		// TODO Auto-generated method stub
		super.didDiscovered(error, devicesList);
		for (int i = 0; i < devicesList.size(); i++) {
			if (devicesList.get(i).getDid().equals(mXpgWifiDevice.getDid())) {
				mXpgWifiDevice = devicesList.get(i);
				centralControlDevice = (XPGWifiCentralControlDevice) mXpgWifiDevice;
				centralControlDevice.setListener(xpgWifiCentralControlDeviceListener);
				return;
			}
		}
	}
}
