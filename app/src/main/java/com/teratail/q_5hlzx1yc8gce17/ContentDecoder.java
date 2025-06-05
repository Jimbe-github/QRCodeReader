package com.teratail.q_5hlzx1yc8gce17;

import androidx.annotation.NonNull;

import java.nio.charset.*;

enum ContentDecoder {
  HEX {
    String getText(byte[] content) {
      StringBuilder sb = new StringBuilder();
      int i = 0;
      for(byte b : content) {
        if(++i > 16) {
          sb.append("\n");
          i = 1;
        }
        if(b < 16)
          sb.append('0');
        sb.append(Integer.toHexString(b));
      }
      return sb.toString();
    }

    public @NonNull String toString() {
      return "Hex";
    }
  },
  SHIFT_JIS {
    String getText(byte[] content) {
      return new String(content, Charset.forName("Shift_JIS"));
    }

    public @NonNull String toString() {
      return "ShiftJIS";
    }
  },
  UTF_8 {
    String getText(byte[] content) {
      return new String(content, StandardCharsets.UTF_8);
    }

    public @NonNull String toString() {
      return "UTF-8";
    }
  };

  abstract String getText(byte[] content);
}
