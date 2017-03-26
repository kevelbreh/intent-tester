package example.com.intenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  public static final ArrayList<String> MIMES = new ArrayList<>();
  public static final ArrayList<String> TEXTS = new ArrayList<>();
  public static final ArrayList<String> IMAGES = new ArrayList<>();

  static {
    MIMES.add("*/*");
    MIMES.add("image/*");
    MIMES.add("text/plain");
    TEXTS.add("This is normal text.");
    TEXTS.add("This is normal text and https://www.google.com/");
    TEXTS.add("https://www.google.com/");
    IMAGES.add("NO IMAGE");
    IMAGES.add("SOME IMAGE");
  }

  @BindView(R.id.spinner_mime) Spinner spinnerMime;
  @BindView(R.id.spinner_text) Spinner spinnerText;
  @BindView(R.id.spinner_image) Spinner spinnerImage;

  @BindView(R.id.checkbox_text) CheckBox checkBoxText;
  @BindView(R.id.checkbox_image) CheckBox checkBoxImage;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    ArrayAdapter<String> spinnerMimeAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MIMES);
    spinnerMimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerMime.setAdapter(spinnerMimeAdapter);

    ArrayAdapter<String> spinnerTextAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TEXTS);
    spinnerTextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerText.setAdapter(spinnerTextAdapter);

    ArrayAdapter<String> spinnerImageAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TEXTS);
    spinnerImageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerText.setAdapter(spinnerImageAdapter);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() != R.id.action_launch) {
      return super.onOptionsItemSelected(item);
    }
    makeIntentAndLaunch();
    return true;
  }

  @OnCheckedChanged(R.id.checkbox_text) void onTextChecked(CheckBox view, boolean checked) {
    spinnerText.setEnabled(checked);
  }

  @OnCheckedChanged(R.id.checkbox_image) void onImageChecked(CheckBox view, boolean checked) {
    spinnerImage.setEnabled(checked);
  }

  private void makeIntentAndLaunch() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType((String) spinnerMime.getSelectedItem());

    if (checkBoxText.isEnabled()) {
      String text = (String) spinnerText.getSelectedItem();
      intent.putExtra(Intent.EXTRA_TEXT, text);
    }
    if (checkBoxImage.isEnabled()) {
      String uri = (String) spinnerText.getSelectedItem();
      intent.setType((String) spinnerMime.getSelectedItem());
      intent.setData(Uri.parse(uri));
    }

    startActivity(Intent.createChooser(intent, "Share with"));
  }
}
