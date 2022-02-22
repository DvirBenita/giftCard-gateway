package io.wetake.backend.gateways.giftcardgateway.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileObjectStorage {
  private String userId;
  private String mimeType;
  private long size;
  private String name;
  private long timestamp;
  private byte[] file;
}
