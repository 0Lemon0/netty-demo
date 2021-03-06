// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PayCallbackResp.proto

package com.hcj.study.nettydemo.codec.protobuf.protobuf;

public final class PayCallbackRespProtoBuf {
  private PayCallbackRespProtoBuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PayCallbackRespOrBuilder extends
      // @@protoc_insertion_point(interface_extends:PayCallbackResp)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 code = 1;</code>
     */
    int getCode();

    /**
     * <code>string message = 2;</code>
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 2;</code>
     */
    com.google.protobuf.ByteString
        getMessageBytes();
  }
  /**
   * Protobuf type {@code PayCallbackResp}
   */
  public  static final class PayCallbackResp extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:PayCallbackResp)
      PayCallbackRespOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PayCallbackResp.newBuilder() to construct.
    private PayCallbackResp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PayCallbackResp() {
      code_ = 0;
      message_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PayCallbackResp(
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

              code_ = input.readInt32();
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              message_ = s;
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
      return PayCallbackRespProtoBuf.internal_static_PayCallbackResp_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PayCallbackRespProtoBuf.internal_static_PayCallbackResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              PayCallbackRespProtoBuf.PayCallbackResp.class, PayCallbackRespProtoBuf.PayCallbackResp.Builder.class);
    }

    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <code>int32 code = 1;</code>
     */
    public int getCode() {
      return code_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private volatile java.lang.Object message_;
    /**
     * <code>string message = 2;</code>
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      }
    }
    /**
     * <code>string message = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
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
      if (code_ != 0) {
        output.writeInt32(1, code_);
      }
      if (!getMessageBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (code_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, code_);
      }
      if (!getMessageBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
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
      if (!(obj instanceof PayCallbackRespProtoBuf.PayCallbackResp)) {
        return super.equals(obj);
      }
      PayCallbackRespProtoBuf.PayCallbackResp other = (PayCallbackRespProtoBuf.PayCallbackResp) obj;

      boolean result = true;
      result = result && (getCode()
          == other.getCode());
      result = result && getMessage()
          .equals(other.getMessage());
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
      hash = (37 * hash) + CODE_FIELD_NUMBER;
      hash = (53 * hash) + getCode();
      hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
      hash = (53 * hash) + getMessage().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static PayCallbackRespProtoBuf.PayCallbackResp parseFrom(
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
    public static Builder newBuilder(PayCallbackRespProtoBuf.PayCallbackResp prototype) {
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
     * Protobuf type {@code PayCallbackResp}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:PayCallbackResp)
        PayCallbackRespProtoBuf.PayCallbackRespOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return PayCallbackRespProtoBuf.internal_static_PayCallbackResp_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return PayCallbackRespProtoBuf.internal_static_PayCallbackResp_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                PayCallbackRespProtoBuf.PayCallbackResp.class, PayCallbackRespProtoBuf.PayCallbackResp.Builder.class);
      }

      // Construct using com.hcj.study.nettydemo.codec.protobuf.proto.PayCallbackRespProtoBuf.PayCallbackResp.newBuilder()
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
        code_ = 0;

        message_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return PayCallbackRespProtoBuf.internal_static_PayCallbackResp_descriptor;
      }

      public PayCallbackRespProtoBuf.PayCallbackResp getDefaultInstanceForType() {
        return PayCallbackRespProtoBuf.PayCallbackResp.getDefaultInstance();
      }

      public PayCallbackRespProtoBuf.PayCallbackResp build() {
        PayCallbackRespProtoBuf.PayCallbackResp result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public PayCallbackRespProtoBuf.PayCallbackResp buildPartial() {
        PayCallbackRespProtoBuf.PayCallbackResp result = new PayCallbackRespProtoBuf.PayCallbackResp(this);
        result.code_ = code_;
        result.message_ = message_;
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
        if (other instanceof PayCallbackRespProtoBuf.PayCallbackResp) {
          return mergeFrom((PayCallbackRespProtoBuf.PayCallbackResp)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(PayCallbackRespProtoBuf.PayCallbackResp other) {
        if (other == PayCallbackRespProtoBuf.PayCallbackResp.getDefaultInstance()) return this;
        if (other.getCode() != 0) {
          setCode(other.getCode());
        }
        if (!other.getMessage().isEmpty()) {
          message_ = other.message_;
          onChanged();
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
        PayCallbackRespProtoBuf.PayCallbackResp parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (PayCallbackRespProtoBuf.PayCallbackResp) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int code_ ;
      /**
       * <code>int32 code = 1;</code>
       */
      public int getCode() {
        return code_;
      }
      /**
       * <code>int32 code = 1;</code>
       */
      public Builder setCode(int value) {
        
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 code = 1;</code>
       */
      public Builder clearCode() {
        
        code_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object message_ = "";
      /**
       * <code>string message = 2;</code>
       */
      public java.lang.String getMessage() {
        java.lang.Object ref = message_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       */
      public com.google.protobuf.ByteString
          getMessageBytes() {
        java.lang.Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder setMessage(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder clearMessage() {
        
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }
      /**
       * <code>string message = 2;</code>
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        message_ = value;
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


      // @@protoc_insertion_point(builder_scope:PayCallbackResp)
    }

    // @@protoc_insertion_point(class_scope:PayCallbackResp)
    private static final PayCallbackRespProtoBuf.PayCallbackResp DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new PayCallbackRespProtoBuf.PayCallbackResp();
    }

    public static PayCallbackRespProtoBuf.PayCallbackResp getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PayCallbackResp>
        PARSER = new com.google.protobuf.AbstractParser<PayCallbackResp>() {
      public PayCallbackResp parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PayCallbackResp(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PayCallbackResp> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PayCallbackResp> getParserForType() {
      return PARSER;
    }

    public PayCallbackRespProtoBuf.PayCallbackResp getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PayCallbackResp_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_PayCallbackResp_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025PayCallbackResp.proto\"0\n\017PayCallbackRe" +
      "sp\022\014\n\004code\030\001 \001(\005\022\017\n\007message\030\002 \001(\tBA\n&com" +
      ".hcj.study.nettydemo.codec.protobufB\027Pay" +
      "CallbackRespProtoBufb\006proto3"
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
    internal_static_PayCallbackResp_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_PayCallbackResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_PayCallbackResp_descriptor,
        new java.lang.String[] { "Code", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
