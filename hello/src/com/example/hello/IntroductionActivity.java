package com.example.hello;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class IntroductionActivity extends Activity implements OnClickListener{
private VideoView video;
private MediaController ctlr;
private Button nextButton;
@Override
public void onCreate(Bundle icicle) {
super.onCreate(icicle);
getWindow().setFormat(PixelFormat.TRANSLUCENT);
setContentView(R.layout.introduction_layout);
nextButton = (Button)findViewById(R.id.nextbutton);
nextButton.setOnClickListener(this);
//File clip=new File(Environment.getExternalStorageDirectory(),
//"test.mp4");
String videoName = "test";
int id = getResources().getIdentifier(videoName, "raw", getPackageName());
final String path = "android.resource://" + getPackageName() + "/" + id;

//vvBgVideo.setVideoURI(Uri.parse(path));
/*video=(VideoView)findViewById(R.id.video);
//video.setVideoPath(clip.getAbsolutePath());
video.setVideoURI(Uri.parse(path));
ctlr=new MediaController(this);
ctlr.setMediaPlayer(video);
video.setMediaController(ctlr);
video.start();
video.requestFocus();*/

}
@Override
public void onClick(View view) {
	// TODO Auto-generated method stub
	switch(view.getId())
	{
	case R.id.nextbutton:

		//Toast.makeText(this,"allfine",Toast.LENGTH_LONG).show();
	    //video.clearFocus();
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
		break;
	}
}
}
