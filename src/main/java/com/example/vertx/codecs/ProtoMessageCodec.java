package com.example.vertx.codecs;

import com.google.protobuf.MessageLite;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class ProtoMessageCodec<T extends MessageLite> implements MessageCodec<T, T> {
	private final T defaultInstance;

	public ProtoMessageCodec(T defaultInstance) {
		this.defaultInstance = defaultInstance;
	}

	@Override
	public void encodeToWire(Buffer buffer, T t) {
		byte[] bytes = t.toByteArray();
		buffer.appendInt(bytes.length);
		buffer.appendBytes(bytes);
	}

	@Override
	public T decodeFromWire(int pos, Buffer buffer) {
		int length = buffer.getInt(pos);
		byte[] bytes = buffer.getBytes(pos + 4, pos + 4 + length);
		try {
			return (T) defaultInstance.getParserForType().parseFrom(bytes);
		} catch (Exception e) {
			throw new RuntimeException("Failed to decode proto message", e);
		}
	}

	@Override
	public T transform(T t) {
		return t; // used for local delivery
	}

	@Override
	public String name() {
		return defaultInstance.getClass().getName();
	}

	@Override
	public byte systemCodecID() {
		return -1; // custom codec
	}
}
