package com.teratail.q_5hlzx1yc8gce17;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.*;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

    Button scanButton = findViewById(R.id.scan_button);
    CheckBox appendCheck = findViewById(R.id.append_check);
    TextView contentText = findViewById(R.id.content_text);
    Spinner contentSpinner = findViewById(R.id.content_spinner);
    Button clearButton = findViewById(R.id.clear_button);

    model.getContent().observe(this, content -> {
      ContentDecoder contentDecoder = model.getContentDecoder().getValue();
      contentText.setText(contentDecoder.getText(content));
    });
    model.getContentDecoder().observe(this, contentDecoder -> {
      byte[] content = model.getContent().getValue();
      contentText.setText(contentDecoder.getText(content));
    });

    GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build();
    GmsBarcodeScanner barcodeScanner = GmsBarcodeScanning.getClient(this, options);

    scanButton.setOnClickListener(v -> {
      barcodeScanner.startScan()
          .addOnSuccessListener(barcode -> {
            if(!appendCheck.isChecked()) model.clearContent();
            model.addContent(barcode.getRawBytes());
          })
          .addOnCanceledListener(() -> {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT);
          })
          .addOnFailureListener(e -> {
            Toast.makeText(this, "throws Exception", Toast.LENGTH_LONG);
            e.printStackTrace();
          });
    });

    ContentSpinnerAdapter adapter = new ContentSpinnerAdapter();
    contentSpinner.setAdapter(adapter);
    contentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        model.setContentDecoder((ContentDecoder)adapter.getItem(position));
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) { /*nothing*/ }
    });

    clearButton.setOnClickListener(v -> {
      model.clearContent();
    });
  }

  private static class ContentSpinnerAdapter extends BaseAdapter {
    @Override
    public int getCount() {
      return ContentDecoder.values().length;
    }

    @Override
    public Object getItem(int position) {
      return ContentDecoder.values()[position];
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
      if(view == null) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_dropdown_item, parent, false);
        view.setTag(view.findViewById(R.id.dropdown_text));
      }
      TextView dropdownText = (TextView)view.getTag();
      dropdownText.setText(ContentDecoder.values()[position].toString());
      return view;
    }
  }
}