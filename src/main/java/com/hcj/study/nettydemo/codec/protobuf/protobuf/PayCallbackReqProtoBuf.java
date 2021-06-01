// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PayCallbackReq.proto

package com.hcj.study.nettydemo.codec.protobuf.protobuf;

public final class PayCallbackReqProtoBuf {
  private PayCallbackReqProtoBuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PayCallbackReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:PayCallbackReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 goodId = 1;</code>
     */
    long getGoodId();

    /**
     * <code>int32 count = 2;</code>
     */
    int getCount();
  }
  /**
   * Protobuf type {@code PayCallbackReq}
   */
  public  static final class PayCallbackReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:PayCallbackReq)
      PayCallbackReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PayCallbackReq.newBuilder() to construct.
    private PayCallbackReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PayCallbackReq() {
      goodId_ = 0L;
      count_ = 0;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PayCallbackReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              goodId_ = input.readInt64();
              break;
            }
            case 16: {

              count_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PayCallbackReqProtoBuf.internal_static_PayCallbackReq_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PayCallbackReqProtoBuf.internal_static_PayCallbackReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              PayCallbackReqProtoBuf.PayCallbackReq.class, PayCallbackReqProtoBuf.PayCallbackReq.Builder.class);
    }

    public static final int GOODID_FIELD_NUMBER = 1;
    private long goodId_;
    /**
     * <code>int64 goodId = 1;</code>
     */
    public long getGoodId() {
      return goodId_;
    }

    public static final int COUNT_FIELD_NUMBER = 2;
    private int count_;
    /**
     * <code>int32 count = 2;</code>
     */
    public int getCount() {
      return count_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (goodId_ != 0L) {
        output.writeInt64(1, goodId_);
      }
      if (count_ != 0) {
        output.writeInt32(2, count_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (goodId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, goodId_);
      }
      if (count_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, count_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof PayCallbackReqProtoBuf.PayCallbackReq)) {
        return super.equals(obj);
      }
      PayCallbackReqProtoBuf.PayCallbackReq other = (PayCallbackReqProtoBuf.PayCallbackReq) obj;

      boolean result = true;
      result = result && (getGoodId()
          == other.getGoodId());
      result = result && (getCount()
          == other.getCount());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + GOODID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getGoodId());
      hash = (37 * hash) + COUNT_FIELD_NUMBER;
      hash = (53 * hash) + getCount();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PayCallbackReqProtoBuf.PayCallbackReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(PayCallbackReqProtoBuf.PayCallbackReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code PayCallbackReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:PayCallbackReq)
        PayCallbackReqProtoBuf.PayCallbackReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return PayCallbackReqProtoBuf.internal_static_PayCallbackReq_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return PayCallbackReqProtoBuf.internal_static_PayCallbackReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                PayCallbackReqProtoBuf.PayCallbackReq.class, PayCallbackReqProtoBuf.PayCallbackReq.Builder.class);
      }

      // Construct using com.hcj.study.nettydemo.codec.protobuf.proto.PayCallbackReqProtoBuf.PayCallbackReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        goodId_ = 0L;

        count_ = 0;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return PayCallbackReqProtoBuf.internal_static_PayCallbackReq_descriptor;
      }

      public PayCallbackReqProtoBuf.PayCallbackReq getDefaultInstanceForType() {
        return PayCallbackReqProtoBuf.PayCallbackReq.getDefaultInstance();
      }

      public PayCallbackReqProtoBuf.PayCallbackReq build() {
        PayCallbackReqProtoBuf.PayCallbackReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public PayCallbackReqProtoBuf.PayCallbackReq buildPartial() {
        PayCallbackReqProtoBuf.PayCallbackReq result = new PayCallbackReqProtoBuf.PayCallbackReq(this);
        result.goodId_ = goodId_;
        result.count_ = count_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof PayCallbackReqProtoBuf.PayCallbackReq) {
          return mergeFrom((PayCallbackReqProtoBuf.PayCallbackReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(PayCallbackReqProtoBuf.PayCallbackReq other) {
        if (other == PayCallbackReqProtoBuf.PayCallbackReq.getDefaultInstance()) return this;
        if (other.getGoodId() != 0L) {
          setGoodId(other.getGoodId());
        }
        if (other.getCount() != 0) {
          setCount(other.getCount());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        PayCallbackReqProtoBuf.PayCallbackReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (PayCallbackReqProtoBuf.PayCallbackReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long goodId_ ;
      /**
       * <code>int64 goodId = 1;</code>
       */
      public long getGoodId() {
        return goodId_;
      }
      /**
       * <code>int64 goodId = 1;</code>
       */
      public Builder setGoodId(long value) {
        
        goodId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 goodId = 1;</code>
       */
      public Builder clearGoodId() {
        
        goodId_ = 0L;
        onChanged();
        return this;
      }

      private int count_ ;
      /**
       * <code>int32 count = 2;</code>
       */
      public int getCount() {
        return count_;
      }
      /**
       * <code>int32 count = 2;</code>
       */
      public Builder setCount(int value) {
        
        count_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 count = 2;</code>
       */
      public Builder clearCount() {
        
        count_ = 0;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:PayCallbackReq)
    }

    // @@protoc_insertion_point(class_scope:PayCallbackReq)
    private static final PayCallbackReqProtoBuf.PayCallbackReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new PayCallbackReqProtoBuf.PayCallbackReq();
    }

    public static PayCallbackReqProtoBuf.PayCallbackReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PayCallbackReq>
        PARSER = new com.google.protobuf.AbstractParser<PayCallbackReq>() {
      public PayCallbackReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PayCallbackReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PayCallbackReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PayCallbackReq> getParserForType() {
      return PARSER;
    }

    public PayCallbackReqProtoBuf.PayCallbackReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PayCallbackReq_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PayCallbackReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024PayCallbackReq.proto\"/\n\016PayCallbackReq" +
      "\022\016\n\006goodId\030\001 \001(\003\022\r\n\005count\030\002 \001(\005B@\n&com.h" +
      "cj.study.nettydemo.codec.protobufB\026PayCa" +
      "llbackReqProtoBufb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_PayCallbackReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_PayCallbackReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_PayCallbackReq_descriptor,
        new java.lang.String[] { "GoodId", "Count", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}