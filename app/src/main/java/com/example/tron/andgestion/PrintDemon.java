package com.example.tron.andgestion;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zj.usbsdk.UsbController;
import com.zj.usbsdk.PrintPic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zj.btsdk.BluetoothService;

public class PrintDemon extends Activity {
    Button btnSearch;
    Button btnSendDraw;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    EditText edtPrint;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;  //»ñÈ¡Éè±¸ÏûÏ¢

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_demon);
        mService = new BluetoothService(this, mHandler);
        //À¶ÑÀ²»¿ÉÓÃÍË³ö³ÌÐò
        if( mService.isAvailable() == false ){
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        //À¶ÑÀÎ´´ò¿ª£¬´ò¿ªÀ¶ÑÀ
        if( mService.isBTopen() == false)
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        try {
            btnSendDraw = (Button) this.findViewById(R.id.btn_test);
            btnSendDraw.setOnClickListener(new ClickEvent());
            btnSearch = (Button) this.findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new ClickEvent());
            btnSend = (Button) this.findViewById(R.id.btnSend);
            btnSend.setOnClickListener(new ClickEvent());
            btnClose = (Button) this.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new ClickEvent());
            edtContext = (EditText) findViewById(R.id.txt_content);
            btnClose.setEnabled(false);
            btnSend.setEnabled(false);
            btnSendDraw.setEnabled(false);
        } catch (Exception ex) {
            Log.e("³ö´íÐÅÏ¢",ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (v == btnSearch) {
                Intent serverIntent = new Intent(PrintDemon.this,DeviceListActivity.class);      //ÔËÐÐÁíÍâÒ»¸öÀàµÄ»î¶¯
                startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
            } else if (v == btnSend) {
                String msg = edtContext.getText().toString();
                if( msg.length() > 0 ){
                    mService.sendMessage(msg+"\n", "GBK");
                }
            } else if (v == btnClose) {
                mService.stop();
            } else if (v == btnSendDraw) {
                String msg = "";
                String lang = getString(R.string.strLang);
                //printImage();

                byte[] cmd = new byte[3];
                cmd[0] = 0x1b;
                cmd[1] = 0x21;
                if((lang.compareTo("en")) == 0){
                    cmd[2] |= 0x10;
                    mService.write(cmd);           //±¶¿í¡¢±¶¸ßÄ£Ê½
                    mService.sendMessage("Congratulations!\n", "GBK");
                    cmd[2] &= 0xEF;
                    mService.write(cmd);           //È¡Ïû±¶¸ß¡¢±¶¿íÄ£Ê½
                    msg = "  You have sucessfully created communications between your device and our bluetooth printer.\n\n"
                            +"  the company is a high-tech enterprise which specializes" +
                            " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";


                    mService.sendMessage(msg,"GBK");
                }else if((lang.compareTo("ch")) == 0){
                    cmd[2] |= 0x10;
                    mService.write(cmd);           //±¶¿í¡¢±¶¸ßÄ£Ê½
                    mService.sendMessage("¹§Ï²Äú£¡\n", "GBK");
                    cmd[2] &= 0xEF;
                    mService.write(cmd);           //È¡Ïû±¶¸ß¡¢±¶¿íÄ£Ê½
                    msg = "  ÄúÒÑ¾­³É¹¦µÄÁ¬½ÓÉÏÁËÎÒÃÇµÄÀ¶ÑÀ´òÓ¡»ú£¡\n\n"
                            + "  ±¾¹«Ë¾ÊÇÒ»¼Ò×¨Òµ´ÓÊÂÑÐ·¢£¬Éú²ú£¬ÏúÊÛÉÌÓÃÆ±¾Ý´òÓ¡»úºÍÌõÂëÉ¨ÃèÉè±¸ÓÚÒ»ÌåµÄ¸ß¿Æ¼¼ÆóÒµ.\n\n";

                    mService.sendMessage(msg,"GBK");
                }
            }
        }
    }

    /**
     * ´´½¨Ò»¸öHandlerÊµÀý£¬ÓÃÓÚ½ÓÊÕBluetoothServiceÀà·µ»Ø»ØÀ´µÄÏûÏ¢
     */
    private final  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //ÒÑÁ¬½Ó
                            Toast.makeText(getApplicationContext(), "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            btnClose.setEnabled(true);
                            btnSend.setEnabled(true);
                            btnSendDraw.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:  //ÕýÔÚÁ¬½Ó
                            Log.d("À¶ÑÀµ÷ÊÔ","ÕýÔÚÁ¬½Ó.....");
                            break;
                        case BluetoothService.STATE_LISTEN:     //¼àÌýÁ¬½ÓµÄµ½À´
                        case BluetoothService.STATE_NONE:
                            Log.d("À¶ÑÀµ÷ÊÔ","µÈ´ýÁ¬½Ó.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //À¶ÑÀÒÑ¶Ï¿ªÁ¬½Ó
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    btnClose.setEnabled(false);
                    btnSend.setEnabled(false);
                    btnSendDraw.setEnabled(false);
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //ÎÞ·¨Á¬½ÓÉè±¸
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:      //ÇëÇó´ò¿ªÀ¶ÑÀ
                if (resultCode == Activity.RESULT_OK) {   //À¶ÑÀÒÑ¾­´ò¿ª
                    Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {                 //ÓÃ»§²»ÔÊÐí´ò¿ªÀ¶ÑÀ
                    finish();
                }
                break;
            case  REQUEST_CONNECT_DEVICE:     //ÇëÇóÁ¬½ÓÄ³Ò»À¶ÑÀÉè±¸
                if (resultCode == Activity.RESULT_OK) {   //ÒÑµã»÷ËÑË÷ÁÐ±íÖÐµÄÄ³¸öÉè±¸Ïî
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //»ñÈ¡ÁÐ±íÏîÖÐÉè±¸µÄmacµØÖ·
                    con_dev = mService.getDevByMac(address);

                    mService.connect(con_dev);
                }
                break;
        }
    }

    //´òÓ¡Í¼ÐÎ
    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        sendData = pg.printDraw();
        mService.write(sendData);   //´òÓ¡byteÁ÷Êý¾Ý
    }
}
