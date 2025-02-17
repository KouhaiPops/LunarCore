// Code generated by protocol buffer compiler. Do not edit!
package emu.lunarcore.proto;

import java.io.IOException;
import us.hebi.quickbuf.FieldName;
import us.hebi.quickbuf.InvalidProtocolBufferException;
import us.hebi.quickbuf.JsonSink;
import us.hebi.quickbuf.JsonSource;
import us.hebi.quickbuf.MessageFactory;
import us.hebi.quickbuf.ProtoMessage;
import us.hebi.quickbuf.ProtoSink;
import us.hebi.quickbuf.ProtoSource;

public final class RelicArchiveOuterClass {
  /**
   * Protobuf type {@code RelicArchive}
   */
  public static final class RelicArchive extends ProtoMessage<RelicArchive> implements Cloneable {
    private static final long serialVersionUID = 0L;

    /**
     * <code>optional uint32 relic_id = 11;</code>
     */
    private int relicId;

    /**
     * <code>optional uint32 slot = 12;</code>
     */
    private int slot;

    private RelicArchive() {
    }

    /**
     * @return a new empty instance of {@code RelicArchive}
     */
    public static RelicArchive newInstance() {
      return new RelicArchive();
    }

    /**
     * <code>optional uint32 relic_id = 11;</code>
     * @return whether the relicId field is set
     */
    public boolean hasRelicId() {
      return (bitField0_ & 0x00000001) != 0;
    }

    /**
     * <code>optional uint32 relic_id = 11;</code>
     * @return this
     */
    public RelicArchive clearRelicId() {
      bitField0_ &= ~0x00000001;
      relicId = 0;
      return this;
    }

    /**
     * <code>optional uint32 relic_id = 11;</code>
     * @return the relicId
     */
    public int getRelicId() {
      return relicId;
    }

    /**
     * <code>optional uint32 relic_id = 11;</code>
     * @param value the relicId to set
     * @return this
     */
    public RelicArchive setRelicId(final int value) {
      bitField0_ |= 0x00000001;
      relicId = value;
      return this;
    }

    /**
     * <code>optional uint32 slot = 12;</code>
     * @return whether the slot field is set
     */
    public boolean hasSlot() {
      return (bitField0_ & 0x00000002) != 0;
    }

    /**
     * <code>optional uint32 slot = 12;</code>
     * @return this
     */
    public RelicArchive clearSlot() {
      bitField0_ &= ~0x00000002;
      slot = 0;
      return this;
    }

    /**
     * <code>optional uint32 slot = 12;</code>
     * @return the slot
     */
    public int getSlot() {
      return slot;
    }

    /**
     * <code>optional uint32 slot = 12;</code>
     * @param value the slot to set
     * @return this
     */
    public RelicArchive setSlot(final int value) {
      bitField0_ |= 0x00000002;
      slot = value;
      return this;
    }

    @Override
    public RelicArchive copyFrom(final RelicArchive other) {
      cachedSize = other.cachedSize;
      if ((bitField0_ | other.bitField0_) != 0) {
        bitField0_ = other.bitField0_;
        relicId = other.relicId;
        slot = other.slot;
      }
      return this;
    }

    @Override
    public RelicArchive mergeFrom(final RelicArchive other) {
      if (other.isEmpty()) {
        return this;
      }
      cachedSize = -1;
      if (other.hasRelicId()) {
        setRelicId(other.relicId);
      }
      if (other.hasSlot()) {
        setSlot(other.slot);
      }
      return this;
    }

    @Override
    public RelicArchive clear() {
      if (isEmpty()) {
        return this;
      }
      cachedSize = -1;
      bitField0_ = 0;
      relicId = 0;
      slot = 0;
      return this;
    }

    @Override
    public RelicArchive clearQuick() {
      if (isEmpty()) {
        return this;
      }
      cachedSize = -1;
      bitField0_ = 0;
      return this;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof RelicArchive)) {
        return false;
      }
      RelicArchive other = (RelicArchive) o;
      return bitField0_ == other.bitField0_
        && (!hasRelicId() || relicId == other.relicId)
        && (!hasSlot() || slot == other.slot);
    }

    @Override
    public void writeTo(final ProtoSink output) throws IOException {
      if ((bitField0_ & 0x00000001) != 0) {
        output.writeRawByte((byte) 88);
        output.writeUInt32NoTag(relicId);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        output.writeRawByte((byte) 96);
        output.writeUInt32NoTag(slot);
      }
    }

    @Override
    protected int computeSerializedSize() {
      int size = 0;
      if ((bitField0_ & 0x00000001) != 0) {
        size += 1 + ProtoSink.computeUInt32SizeNoTag(relicId);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        size += 1 + ProtoSink.computeUInt32SizeNoTag(slot);
      }
      return size;
    }

    @Override
    @SuppressWarnings("fallthrough")
    public RelicArchive mergeFrom(final ProtoSource input) throws IOException {
      // Enabled Fall-Through Optimization (QuickBuffers)
      int tag = input.readTag();
      while (true) {
        switch (tag) {
          case 88: {
            // relicId
            relicId = input.readUInt32();
            bitField0_ |= 0x00000001;
            tag = input.readTag();
            if (tag != 96) {
              break;
            }
          }
          case 96: {
            // slot
            slot = input.readUInt32();
            bitField0_ |= 0x00000002;
            tag = input.readTag();
            if (tag != 0) {
              break;
            }
          }
          case 0: {
            return this;
          }
          default: {
            if (!input.skipField(tag)) {
              return this;
            }
            tag = input.readTag();
            break;
          }
        }
      }
    }

    @Override
    public void writeTo(final JsonSink output) throws IOException {
      output.beginObject();
      if ((bitField0_ & 0x00000001) != 0) {
        output.writeUInt32(FieldNames.relicId, relicId);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        output.writeUInt32(FieldNames.slot, slot);
      }
      output.endObject();
    }

    @Override
    public RelicArchive mergeFrom(final JsonSource input) throws IOException {
      if (!input.beginObject()) {
        return this;
      }
      while (!input.isAtEnd()) {
        switch (input.readFieldHash()) {
          case 1090714606:
          case -547564185: {
            if (input.isAtField(FieldNames.relicId)) {
              if (!input.trySkipNullValue()) {
                relicId = input.readUInt32();
                bitField0_ |= 0x00000001;
              }
            } else {
              input.skipUnknownField();
            }
            break;
          }
          case 3533310: {
            if (input.isAtField(FieldNames.slot)) {
              if (!input.trySkipNullValue()) {
                slot = input.readUInt32();
                bitField0_ |= 0x00000002;
              }
            } else {
              input.skipUnknownField();
            }
            break;
          }
          default: {
            input.skipUnknownField();
            break;
          }
        }
      }
      input.endObject();
      return this;
    }

    @Override
    public RelicArchive clone() {
      return new RelicArchive().copyFrom(this);
    }

    @Override
    public boolean isEmpty() {
      return ((bitField0_) == 0);
    }

    public static RelicArchive parseFrom(final byte[] data) throws InvalidProtocolBufferException {
      return ProtoMessage.mergeFrom(new RelicArchive(), data).checkInitialized();
    }

    public static RelicArchive parseFrom(final ProtoSource input) throws IOException {
      return ProtoMessage.mergeFrom(new RelicArchive(), input).checkInitialized();
    }

    public static RelicArchive parseFrom(final JsonSource input) throws IOException {
      return ProtoMessage.mergeFrom(new RelicArchive(), input).checkInitialized();
    }

    /**
     * @return factory for creating RelicArchive messages
     */
    public static MessageFactory<RelicArchive> getFactory() {
      return RelicArchiveFactory.INSTANCE;
    }

    private enum RelicArchiveFactory implements MessageFactory<RelicArchive> {
      INSTANCE;

      @Override
      public RelicArchive create() {
        return RelicArchive.newInstance();
      }
    }

    /**
     * Contains name constants used for serializing JSON
     */
    static class FieldNames {
      static final FieldName relicId = FieldName.forField("relicId", "relic_id");

      static final FieldName slot = FieldName.forField("slot");
    }
  }
}
