package com.teratail.q_5hlzx1yc8gce17;

import androidx.annotation.NonNull;
import androidx.lifecycle.*;

import java.util.*;

public class MainViewModel extends ViewModel {
  private MutableLiveData<byte[]> contentLiveData = new MutableLiveData<>(new byte[0]);
  @NonNull LiveData<byte[]> getContent() { return contentLiveData; }

  void addContent(byte[] appends) {
    byte[] old = contentLiveData.getValue();
    byte[] content = Arrays.copyOf(old, old.length+appends.length);
    System.arraycopy(appends, 0, content, old.length, appends.length);
    contentLiveData.setValue(content);
  }
  void clearContent() {
    contentLiveData.setValue(new byte[0]);
  }

  private MutableLiveData<ContentDecoder> contentDecoderLiveData = new MutableLiveData<>(ContentDecoder.HEX);
  LiveData<ContentDecoder> getContentDecoder() { return contentDecoderLiveData; }

  void setContentDecoder(@NonNull ContentDecoder contentDecoder) {
    contentDecoderLiveData.setValue(contentDecoder);
  }
}
