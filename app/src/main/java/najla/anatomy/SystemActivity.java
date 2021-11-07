package najla.anatomy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class SystemActivity extends AppCompatActivity {
    Bundle bundle ;
    int Selected =1;
    private ArFragment arFragment;

    ImageView dig  , skele , resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment2);

        dig=findViewById(R.id.dig);
        skele=findViewById(R.id.skele);
        resp=findViewById(R.id.resp);

        bundle = getIntent().getExtras();
        Selected = bundle.getInt("selected");


        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();

                 if (Selected == 1) {
                     ModelRenderable.builder()
                             .setSource(getApplicationContext(), R.raw.male)
                             .build().thenAccept(renderable -> creatModel(anchor, renderable, Selected))
                             .exceptionally(
                                     throwable -> {
                                         Toast.makeText(getApplicationContext(), "unable to load model", Toast.LENGTH_SHORT).show();
                                         return null;
                                     }
                             );

                 }
                 if (Selected == 2 ) {
                     ModelRenderable.builder()
                             .setSource(getApplicationContext(), R.raw.female)
                             .build().thenAccept(renderable -> creatModel(anchor, renderable, Selected))
                             .exceptionally(
                                     throwable -> {
                                         Toast.makeText(getApplicationContext(), "unable to load model", Toast.LENGTH_SHORT).show();
                                         return null;
                                     }
                             );
                 }



            }
        });



        skele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skele.setBackgroundColor(Color.parseColor("#00333639"));

                Intent intent = new Intent(SystemActivity.this,Syst.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected1",2);
                bundle.putInt("selected",Selected);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                finish();
            }
        });

        dig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dig.setBackgroundColor(Color.parseColor("#00333639"));

                Intent intent = new Intent(SystemActivity.this,Syst.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected1",1);
                bundle.putInt("selected",Selected);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                finish();
            }
        });



        resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skele.setBackgroundColor(Color.parseColor("#00333639"));

                Intent intent = new Intent(SystemActivity.this,Syst.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected1",3);
                bundle.putInt("selected",Selected);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                finish();
            }
        });

    }


    private void creatModel(Anchor anchor , ModelRenderable renderable,int selected) {

        if (selected == 1) {
            AnchorNode anchorNode = new AnchorNode(anchor);

            TransformableNode male = new TransformableNode(arFragment.getTransformationSystem());
            male.setParent(anchorNode);
            male.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            male.select();
        }
        if (selected == 2) {
            AnchorNode anchorNode = new AnchorNode(anchor);

            TransformableNode female = new TransformableNode(arFragment.getTransformationSystem());
            female.setParent(anchorNode);
            female.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            female.select();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Intent = new Intent(SystemActivity.this,MainActivity.class);
        startActivity(Intent);
        finish();

    }

}
