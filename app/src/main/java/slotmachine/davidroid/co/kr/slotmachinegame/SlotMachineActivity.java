package slotmachine.davidroid.co.kr.slotmachinegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import slotmachine.davidroid.co.kr.slotmachinegame.util.SlotUtil;

public class SlotMachineActivity extends AppCompatActivity {

    SlotUtil slotUtil;

    private RadioGroup operateTypeRadioGroup;
    private int prevRadioChecked =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_machine);

        slotUtil = new SlotUtil(SlotMachineActivity.this);

        operateTypeRadioGroup = (RadioGroup) findViewById(R.id.operateType);
        operateTypeRadioGroup.check(R.id.radio_01_btn);
        operateTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(), operateTypeRadioGroup.getCheckedRadioButtonId()+"", Toast.LENGTH_SHORT).show();
                switch(checkedId)
                {
                    case R.id.radio_01_btn:{

                        slotUtil.registerButtonOperator(SlotMachineActivity.this);

                        Toast.makeText(getApplicationContext(), "버튼 사용", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.radio_02_proximity:{
                        slotUtil.registerProximitySensor(SlotMachineActivity.this);
                        Toast.makeText(getApplicationContext(), "근접 센서", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.radio_03_accelorometer:{
                        Toast.makeText(getApplicationContext(), "가속도 센서", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SlotMachineGame", "onResume()");
        slotUtil.registerProximitySensor(SlotMachineActivity.this);
        slotUtil.registerButtonOperator(SlotMachineActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SlotMachineGame", "onPause()");
        slotUtil.unregisterProximitySensor(SlotMachineActivity.this);
        slotUtil.unregisterButtonOperator(SlotMachineActivity.this);

    }
}
