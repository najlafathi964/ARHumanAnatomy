package najla.anatomy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Syst extends AppCompatActivity {

    ImageView organ, proc, dise, del, info;
    ImageView DialogImg;
    TextView DialogText;
    ListAdapter listAdapter;
    //    String[] names;
//    int[] imgs;
//    int[] sound;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> imgs = new ArrayList<>();
    ArrayList<Integer> sound = new ArrayList<>();

    List<String> namelist;
    List<Integer> imglist;
    List<Integer> soundlist;
    DB_Sqlite db = new DB_Sqlite(Syst.this);
    File database;
    private ArFragment arFragment;
    private VideoView video;
    private String uri , webUri;
    private WebView web;
    private ListView listView;
    private MediaController mc;
    private Bundle bundle;
    private int Selected, sel;
    private ImageView sys_img;
    private TextView sys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syst);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment3);

        bundle = getIntent().getExtras();
        Selected = bundle.getInt("selected1");
        sel = bundle.getInt("selected");


        organ = findViewById(R.id.organ);
        proc = findViewById(R.id.proc);
        dise = findViewById(R.id.dise);
        del = findViewById(R.id.del);
        info = findViewById(R.id.inf);

        if (Selected == 1) {

            organ.setImageResource(R.drawable.dorgan);
            proc.setImageResource(R.drawable.dproc);
            dise.setImageResource(R.drawable.ddis);
            del.setImageResource(R.drawable.ddelete);
            info.setImageResource(R.drawable.dinfo);

        } else if (Selected == 2) {

            organ.setImageResource(R.drawable.sorgan);
            proc.setImageResource(R.drawable.sproc);
            dise.setImageResource(R.drawable.sdis);
            del.setImageResource(R.drawable.sdelete);
            info.setImageResource(R.drawable.sinfo);

        } else if (Selected == 3) {

            organ.setImageResource(R.drawable.rorgan);
            proc.setImageResource(R.drawable.rproc);
            dise.setImageResource(R.drawable.rdis);
            del.setImageResource(R.drawable.rdel);
            info.setImageResource(R.drawable.rinfo);

        }


        File database = getApplicationContext().getDatabasePath(db.DBNAME);
        if (false == database.exists()) {
            db.getReadableDatabase();
            if (copyDatabase(Syst.this)) {
                //Toast.makeText(this,"نجاح",Toast.LENGTH_LONG).show();

            } else {
                //  Toast.makeText(this,"فشل",Toast.LENGTH_LONG).show();

                return;
            }
        }


        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();

                if (Selected == 1) {
                    ModelRenderable.builder()
                            .setSource(getApplicationContext(), R.raw.stmoch)
                            .build().thenAccept(renderable -> creatModel(anchor, renderable, Selected))
                            .exceptionally(
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "unable to load model", Toast.LENGTH_SHORT).show();
                                        return null;
                                    }
                            );

                }
                if (Selected == 2) {
                    ModelRenderable.builder()
                            .setSource(getApplicationContext(), R.raw.skeleton)
                            .build().thenAccept(renderable -> creatModel(anchor, renderable, Selected))
                            .exceptionally(
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "unable to load model", Toast.LENGTH_SHORT).show();
                                        return null;
                                    }
                            );
                }

                if (Selected == 3) {
                    ModelRenderable.builder()
                            .setSource(getApplicationContext(), R.raw.respiratory)
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

//        Thread thread = new Thread() {
//            @Override
//            public void run() {

                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String info ="" ;
                        if (Selected == 1) {

                            info =getResources().getString(R.string.sysname1);

                        }else if (Selected == 2) {

                            info =getResources().getString(R.string.sysname2);

                        }else if (Selected ==3) {

                            info =getResources().getString(R.string.sysname3);

                        }


                            makeDialoge(2, 0, Syst.this, info);


                        }
                });


                organ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        organ.setBackgroundColor(Color.parseColor("#00333639"));

                        listView = new ListView(v.getContext());
                     //   names = db.get_All_organName(Selected);
                        if (Selected == 1) {
                            soundlist = Arrays.asList(R.raw.mouth, R.raw.esophagus, R.raw.liver, R.raw.stomach, R.raw.pancreas, R.raw.colon, R.raw.small, R.raw.rectum, R.raw.anus);
                           namelist = Arrays.asList("Mouth", "Esophagus", "Liver", "Stomach", "Pancreas", "Large intestine (Colon)", "Small intestine", "Rectum", "Anus");
                            imglist = Arrays.asList(R.drawable.mouth, R.drawable.esoph, R.drawable.liver, R.drawable.stomach, R.drawable.pancreas, R.drawable.colon, R.drawable.smallint, R.drawable.rectum, R.drawable.anus);

                        } else if (Selected == 2) {
                            soundlist = Arrays.asList(R.raw.skull, R.raw.column, R.raw.rib, R.raw.shoulder, R.raw.upperlimb, R.raw.pelvic, R.raw.lower);


                            namelist = Arrays.asList("Skull", "Vertebral Column", "Rib Cage ", "Shoulder Girdle ", "Upper limb", "Pelvic Girdle ", "Lower limb ");
                            imglist = Arrays.asList(R.drawable.skull, R.drawable.column, R.drawable.rib, R.drawable.shoulder, R.drawable.upper, R.drawable.pelvic, R.drawable.lower);

                        } else if (Selected == 3) {
                            soundlist = Arrays.asList(R.raw.nose, R.raw.pharynx, R.raw.larynx, R.raw.trachea, R.raw.bronchi, R.raw.lungs, R.raw.alveoli);
                            namelist = Arrays.asList("Nose", "Pharynx", "Larynx", "Trachea", "Bronchi", "Lungs", "Alveoli");
                            imglist = Arrays.asList(R.drawable.nose, R.drawable.pharynx, R.drawable.larynx, R.drawable.trachea, R.drawable.bronchi, R.drawable.lungs, R.drawable.alveoli);

                        }

                        names.addAll(namelist);
                        imgs.addAll(imglist);
                        sound.addAll(soundlist);

                        listAdapter = new ListAdapter(names, imgs, sound, v.getContext());
                        listView.setAdapter(listAdapter);

                        try {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                            dialog.setTitle("Organs list");
                            listView.findViewById(R.id.list);
                            listAdapter = new ListAdapter(names, imgs, sound, v.getContext());
                            listView.setAdapter(listAdapter);
                            dialog.setView(listView);
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
                });
//            }
//        };
//        thread.run();

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proc.setBackgroundColor(Color.parseColor("#00333639"));


                try {
                    final Dialog dialog = new Dialog(Syst.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.video_row);
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                    dialog.getWindow().setAttributes(layoutParams);
                    getWindow().setFormat(PixelFormat.TRANSLUCENT);

                    video = dialog.findViewById(R.id.vid);

                    if (Selected == 1) {
                        uri = "android.resource://" + getPackageName() + "/" + R.raw.digproc;
                    } else if (Selected == 2) {
                        uri = "android.resource://" + getPackageName() + "/" + R.raw.skeke;
                    } else if (Selected == 3) {
                        uri = "android.resource://" + getPackageName() + "/" + R.raw.resp;
                    }
                    video.setVideoURI(Uri.parse(uri));
                    mc = new MediaController(Syst.this);
                    mc.setAnchorView(video);
                    mc.setVisibility(View.VISIBLE);
                    video.setMediaController(mc);


                    video.requestFocus();
                    video.start();

                } catch (Exception x) {
                }


            }
        });

        dise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dise.setBackgroundColor(Color.parseColor("#00333639"));


                try {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(Syst.this);
                    dialog.setTitle("System's diseases");

                    web = new WebView(Syst.this);

                    if (Selected == 1) {
                        webUri = "http://pennstatehershey.adam.com/content.aspx?productId=107&pid=33&gid=002350#Digestive%20System";
                    } else if (Selected == 2) {
                        webUri = "http://pennstatehershey.adam.com/content.aspx?productId=107&pid=33&gid=002350#Musculoskeletal";
                    } else if (Selected == 3) {
                        webUri = "http://pennstatehershey.adam.com/content.aspx?productId=107&pid=33&gid=002350#Respiratory" ;
                    }
                    web.loadUrl("http://pennstatehershey.adam.com/content.aspx?productId=107&pid=33&gid=002350");
                    web.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;

                        }
                    });



//                   Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("http://pennstatehershey.adam.com/content.aspx?productId=107&pid=33&gid=002350"));
//
//                 startActivity(intent);

                    dialog.setView(web);
                    dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });




                    dialog.show();


                } catch (Exception x) {
                }


            }
        });


    }


    public boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(db.DBNAME);
            String outFileName = db.DBLOCATION + db.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void creatModel(Anchor anchor, ModelRenderable renderable, int selected) {


        AnchorNode anchorNode = new AnchorNode(anchor);

        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
        model.setParent(anchorNode);
        model.setRenderable(renderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

        if (Selected == 3) {
            model.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), 90f));
        }
        model.select();

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del.setBackgroundColor(Color.parseColor("#00333639"));

                arFragment.getArSceneView().getScene().removeChild(anchorNode);


            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent intent = new Intent(Syst.this, SystemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("selected", sel);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            finish();

    }

    public class ListAdapter extends ArrayAdapter<String> {

        final ArrayList<String> name;
        final ArrayList<Integer> imgs;
        final ArrayList<Integer> sound;
        final Context context;

        public ListAdapter(ArrayList<String> name, ArrayList<Integer> imgs, ArrayList<Integer> sound, Context context) {
            super(context, R.layout.row_item, name);
            this.context = context;
            this.name = name;
            this.imgs = imgs;
            this.sound = sound;
        }

        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item, null, true);


            TextView txt_row = view.findViewById(R.id.txt_organ);
            final ImageView img_row = view.findViewById(R.id.img_organ);
            ImageView sounds = view.findViewById(R.id.sound);
            LinearLayout lin_row = view.findViewById(R.id.lin_row);
            img_row.setImageResource(imgs.get(position));
            txt_row.setText(name.get(position));
            sounds.setBackgroundColor(Color.parseColor("#00333639"));

            MediaPlayer player = MediaPlayer.create(context, sound.get(position));


            sounds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.start();
                }
            });

            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    lin_row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lin_row.setBackgroundColor(Color.parseColor("#00333639"));


                            makeDialoge(1, position + 1, context, txt_row.getText().toString());


                        }
                    });

                }
            };
            thread1.run();


            return view;
        }

    }

    public void makeDialoge(int n, int postion, Context context, String title) {

        try {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(title);


            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 600);
            layoutParams.gravity = Gravity.CENTER;

            DialogImg = new ImageView(context);

            DialogImg.setLayoutParams(layoutParams);

            String imgname = "";
            String infoname = "";
            if (n == 1) {
                imgname = "img" + Selected + postion;
                infoname = "organ" + Selected + postion;
            } else if (n == 2) {
                imgname = "sysimg" + Selected;
                infoname = "sys" + Selected;

            }

            int inf1 = context.getResources().getIdentifier(imgname, "string", getPackageName());
            String imge = context.getResources().getString(inf1);


            DialogImg.setImageResource(context.getResources().getIdentifier("najla.anatomy:drawable/" + imge, null, null));

            DialogText = new TextView(context);

            DialogText.setPadding(30, 15, 10, 10);

            // organ.setText(db.get_All_organInfo(name[position]));
            int inf = context.getResources().getIdentifier(infoname, "string", getPackageName());
            DialogText.setText(context.getResources().getString(inf));

            layout.addView(DialogImg);
            layout.addView(DialogText);

            ScrollView scrollView = new ScrollView(context);
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            scrollView.addView(layout);

            dialog.setView(scrollView);


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



