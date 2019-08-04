/*
 * Copyright 2019 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.unityskin.util;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolbox {
    
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static Optional<String> readline() {
        return readline("> ");
    }
    
    public static Optional<String> readline(String prompt) {
        try {
            System.out.print(prompt);
            if (SCANNER.hasNextLine()) {
                return Optional.ofNullable(SCANNER.nextLine());
            }
            
            return Optional.empty();
        } catch (NoSuchElementException ex) {
            return Optional.empty();
        }
    }
    
    public static long findRegion(Path path, Integer[] region) throws IOException {
        try (InputStream inputStream = Toolbox.newBufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ))) {
            long count = 0;
            int index = 0;
            int result;
            
            while ((result = inputStream.read()) != -1) {
                count++;
                
                result = result & 0xFF;
                if (region[index] != null && region[index] != result) {
                    index = 0;
                    continue;
                }
                
                index++;
                if (index == region.length) {
                    return count - index;
                }
            }
            
            throw new EOFException("End of file reached");
        }
    }
    
    public static int readByte(Path path, long position) throws IOException {
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.READ)) {
            seekableByteChannel.position(position);
            
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            if (seekableByteChannel.read(byteBuffer) == 1) {
                return byteBuffer.get(0) & 0xFF;
            }
            
            return -1;
        }
    }
    
    public static boolean writeByte(Path path, long position, byte value) throws IOException {
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, StandardOpenOption.WRITE)) {
            seekableByteChannel.position(position);
            
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            byteBuffer.put(value);
            byteBuffer.flip();
            return seekableByteChannel.write(byteBuffer) == 1;
        }
    }
    
    public static String toHexString(long value) {
        return "0x" + Long.toHexString(value).toUpperCase();
    }
    
    public static Path getPath() {
        return Paths.get(System.getProperty("user.dir"));
    }
    
    public static BufferedInputStream newBufferedInputStream(InputStream inputStream) {
        return new BufferedInputStream(inputStream);
    }
    
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }
}