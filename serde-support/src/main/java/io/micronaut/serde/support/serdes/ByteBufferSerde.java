/*
 * Copyright 2017-2023 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.serde.support.serdes;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * {@link Serde} implementation of {@link java.nio.ByteBuffer}.
 * This is a based on `com.fasterxml.jackson.databind.ser.std.ByteBufferSerializer` which is licenced under the Apache 2.0 licence.
 */
@Singleton
public class ByteBufferSerde implements Serde<ByteBuffer> {
    @Override
    public @Nullable ByteBuffer deserialize(@NonNull Decoder decoder,
                                            @NonNull DecoderContext context,
                                            @NonNull Argument<? super ByteBuffer> type) throws IOException {
        byte[] b = decoder.decodeBinary();
        return ByteBuffer.wrap(b);
    }

    @Override
    public void serialize(@NonNull Encoder encoder,
                          @NonNull EncoderContext context,
                          @NonNull Argument<? extends ByteBuffer> type,
                          @NonNull ByteBuffer value) throws IOException {
        ByteBuffer slice = value.asReadOnlyBuffer();
        ByteBuffer copy = ByteBuffer.allocate(slice.remaining());
        copy.put(slice);
        encoder.encodeBinary(copy.array());
    }
}
