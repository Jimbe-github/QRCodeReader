package com.teratail.q_5hlzx1yc8gce17;

import androidx.annotation.*;
import androidx.lifecycle.*;

import java.util.*;

public class MainViewModel extends ViewModel {
  private final MutableLiveData<byte[]> contentLiveData = new MutableLiveData<>(new byte[0]);
  @NonNull LiveData<byte[]> getContent() { return contentLiveData; }

  void addContent(@Nullable byte[] appends) {
    if(appends == null) return;
    byte[] old = contentLiveData.getValue();
    assert old != null;
    byte[] content = Arrays.copyOf(old, old.length+appends.length);
    System.arraycopy(appends, 0, content, old.length, appends.length);
    contentLiveData.setValue(content);
  }
  void clearContent() {
    contentLiveData.setValue(new byte[0]);
  }

  private final MutableLiveData<ContentDecoder> contentDecoderLiveData = new MutableLiveData<>(ContentDecoder.HEX);
  LiveData<ContentDecoder> getContentDecoder() { return contentDecoderLiveData; }

  void setContentDecoder(@NonNull ContentDecoder contentDecoder) {
    contentDecoderLiveData.setValue(contentDecoder);
  }
}
