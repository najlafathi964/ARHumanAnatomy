package najla.anatomy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment ;
    ImageView male , female , info;
    View arrayView [] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);


        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);

        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        info = findViewById(R.id.info1);

        setArrayView() ;
        setClickListener();





    }


    private void setClickListener() {
        for(int i=0 ; i < arrayView.length ; i++){
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {

        arrayView = new View[]{male,female, info};
    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.male) {

            setBackground (v.getId());

            Intent intent = new Intent(MainActivity.this,SystemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("selected",1);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);

            finish();
        }else if (v.getId()== R.id.female) {
            setBackground (v.getId());

            Intent intent = new Intent(MainActivity.this,SystemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("selected",2);
            intent.putExtras(bundle);
            startActivityForResult(intent,1);
            finish();
        }else if(v.getId()==R.id.info1){

                    try {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("APP GUIDANCE");
                        dialog.setMessage("Welcome to my app..\n\n" +
                                "This is a guide to help you to use this app.\n\n" +
                                "1.Select the gender you want using buttons in the first screen. \n\n" +
                                "2.After gender selection wait until the app detect plane.\n\n" +
                                "3.After plane is detected, add the model by press on add button. \n\n" +
                                "4.Scale, rotate, move the model by your fingres.\n\n" +
                                "5.Select wanted human system by buttons at the bottom of the screen and wait until the plane is detected then add the model. \n\n" +
                                "6.At the bottom of the screen there is 5 buttons, the first one use to display general information about selected human system, the second button display the organs of selected system'You can show details explanation of any organ by selected it from the list', the third button display the working mechanism of selected system, the fourth button display the diseases of selected system and its treatments in a web view, and the last one use for delete the copy of organ 'You can drag any organ of any system by long press on it.' and you can move, rotate and scalling it by your fingers");


                        dialog.setNegativeButton("close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    } catch (Exception x) {
                    }

        }

    }

    private void setBackground(int id) {
        for (int i=0 ; i <arrayView.length ; i++){
            if(arrayView[i].getId()==id)
                arrayView[i].setBackgroundColor(Color.parseColor("#00333639"));
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }

}


