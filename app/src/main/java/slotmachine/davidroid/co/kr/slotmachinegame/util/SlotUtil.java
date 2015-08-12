package slotmachine.davidroid.co.kr.slotmachinegame.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import slotmachine.davidroid.co.kr.slotmachinegame.R;
import slotmachine.davidroid.co.kr.slotmachinegame.widget.OnWheelChangedListener;
import slotmachine.davidroid.co.kr.slotmachinegame.widget.OnWheelScrollListener;
import slotmachine.davidroid.co.kr.slotmachinegame.widget.WheelView;
import slotmachine.davidroid.co.kr.slotmachinegame.widget.adapters.SlotMachineAdapter;

/**
 * Created by YSY on 2015-08-12.
 */
public class SlotUtil {

    private final int SLOT_TIME_01 = 2000;
    private final int SLOT_TIME_02 = 3200;
    private final int SLOT_TIME_03 = 4500;

    private Context context;
    private View rootView;

    Button mix;

    private boolean wheelScrolled = false; // Wheel scrolled flag

    SensorManager sm;
    SensorEventListener listener;

    /**
     * CONSTRUCTOR and INITIALIZER
     */
    /* CONSTRUCTOR */
    public SlotUtil(Context context){
        this.context = context;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        initWheel(R.id.slot_1);
        initWheel(R.id.slot_2);
        initWheel(R.id.slot_3);
    }
    /* Initailize */
    public void initWheel(int id) {

        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(context));
        wheel.setCurrentItem((int) (Math.random() * 10));

        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
    }
/**===========================================================================================================*/
    /**
     * Returns wheel by Id
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    public WheelView getWheel(int id) {
        return (WheelView) rootView.findViewById(id);
    }

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {

            if(wheel.getId() == R.id.slot_3) {
                wheelScrolled = false;

                //updateStatus();
            }
        }
    };

    // Wheel changed listener
    public OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                //updateStatus();
            }
        }
    };



    /**
     * Mixes wheel
     * @param id the wheel id
     */
    public void mixWheel(int id, int mixTimeMills) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-350 + (int) (Math.random() * 50), mixTimeMills);
    }

    /**===========================================<<근접 센서 >>=============================================*/
    public void registerProximitySensor(Context context){

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.d("SlotUtil", "되는거 맞는거죠?");
                mixWheel(R.id.slot_1, SLOT_TIME_01);
                mixWheel(R.id.slot_2, SLOT_TIME_02);
                mixWheel(R.id.slot_3, SLOT_TIME_03);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //센서를 사용하기위해서 센서 메니져 객체를 등록
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //센서메니저에 우리가 원하는 센서를 등록(근접센서)
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterProximitySensor(Context context){
        sm.unregisterListener(listener);
    }
    /**===========================================<<근접 센서 >>=============================================*/
    /**===========================================<< 버튼 >>=============================================*/
    public void registerButtonOperator(Context context){
        mix = (Button) rootView.findViewById(R.id.btn_mix);
        mix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //if (operateTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_01_btn) {
                    mixWheel(R.id.slot_1, SLOT_TIME_01);
                    mixWheel(R.id.slot_2, SLOT_TIME_02);
                    mixWheel(R.id.slot_3, SLOT_TIME_03);
                //}
            }
        });
    }
    public void unregisterButtonOperator(Context context){
        mix = null;
    }
    /**===========================================<< 버튼 >>=============================================*/
}
