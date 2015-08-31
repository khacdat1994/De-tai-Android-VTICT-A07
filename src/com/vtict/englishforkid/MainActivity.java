package com.vtict.englishforkid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.vtict.eng4kid.database.DBHelper;
import com.vtict.eng4kid.database.Topic;

public class MainActivity extends Activity {

	private DBHelper db;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initParseSDK();
		// iv = (ImageView) findViewById(R.id.iv);
		// new taskDownloadFileFromURL()
		// .execute("http://englishteststore.net/vocabulary/picvoca/audio/a.mp3");
		db = new DBHelper(this);
		// createDialog();
		getDataFromServer();

	}

	// init Parse SDK
	private void initParseSDK() {
		Parse.initialize(this, "yDQUiEWnX1kk7IqI4VyGcMZIf8b8cIbfv4oz7dP9",
				"31h8yHJBufMc4aTj8uJelRfhsKsuDMxXjDwZAUHB");

	}

	private void createDialog() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Downloading file. Please wait...");
		pDialog.setCancelable(false);
		// pDialog.show();
	}

	private void getDataFromServer() {

		ParseQuery<ParseObject> queryTopic = ParseQuery.getQuery("Topic");
		queryTopic.orderByAscending("name");
		queryTopic.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> _lstTopic, ParseException e) {
				if (e == null) {
					int topicID = 0;
					for (ParseObject obj : _lstTopic) {

						final Topic topic = new Topic(topicID, obj
								.getString("name"), obj.getString("linkIcon"));

						pDialog.show();

						// lấy tất cả các từ dựa vào objectId của mỗi Topic
						// tương ứng
						ParseQuery<ParseObject> innerQuery = ParseQuery
								.getQuery("Topic");
						innerQuery.whereEqualTo("objectId", obj.getObjectId());
						//
						ParseQuery<ParseObject> query = ParseQuery
								.getQuery("Word");
						query.whereMatchesQuery("id_topic", innerQuery);
						query.findInBackground(new FindCallback<ParseObject>() {
							public void done(List<ParseObject> _lstWord,
									ParseException e) {
								if (e == null && _lstWord.size() > 0) {

									// db.insertTopic(topic);

									// list word trả về
									for (ParseObject obj : _lstWord) {

										//
										//
										// threadDownloadFileFromURL(obj
										// .getString("linkPic"));
										// Toast.makeText(getBaseContext(),
										// obj.getString("word"),
										// Toast.LENGTH_LONG).show();
										// Vocabulary vocab = new Vocabulary(obj
										// .getString("word"),
										// threadDownloadFileFromURL(obj
										// .getString("linkPic")),
										// obj.getString("linkSound"), obj
										// .getString("translate"));
										// db.insertVocab(topic, vocab);
										// pDialog.dismiss();
										// pDialog.dismiss();

										new taskDownloadFileFromURL()
												.execute(obj
														.getString("linkPic"));
									}
								}
							}
						});
						topicID++;
						// createDialog();
					}
				}
			}
		});
		// pDialog.dismiss();
		// progessBar.setVisibility(View.INVISIBLE);
	}

	// download lưu về thẻ nhớ từ đường link có sẵn
	class taskDownloadFileFromURL extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... _params) {
			int count;
			try {
				URL url = new URL(_params[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				// get lenght file
				int lenghtOfFile = conection.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);

				File folder = new File(
						Environment.getExternalStorageDirectory()
								+ "/english4kid/picture");

				folder.mkdirs();

				OutputStream output = new FileOutputStream(
						Environment.getExternalStorageDirectory()
								+ "/english4kid/picture/"
								+ URLUtil.guessFileName(_params[0], null, null));

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					// total += count;
					// publishProgress("" + (int) ((total * 100) /
					// lenghtOfFile));
					// writing data to file
					output.write(data, 0, count);
				}

				// flushing output
				output.flush();
				// closing streams
				output.close();
				input.close();
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

		}

		// phương thức load ảnh, âm tthanh từ đường dẫn lưu ở thẻ nhớ
		private void loadImage() {
			String imagePath = Environment.getExternalStorageDirectory()
					.toString() + "/english4kid/A.gif";
			String musicPath = Environment.getExternalStorageDirectory()
					.toString() + "/english4kid/a.mp3";
			// setting downloaded into image view
			// iv.setImageDrawable(Drawable.createFromPath(imagePath));

			// music

			try {
				MediaPlayer mPlayer = new MediaPlayer();
				Uri myUri = Uri.parse(musicPath);
				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mPlayer.setDataSource(getApplicationContext(), myUri);
				mPlayer.prepare();
				mPlayer.start();
			} catch (Exception e) {

			}
		}
	}

}
