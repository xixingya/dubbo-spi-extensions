/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.common.serialize.gson;

import org.apache.dubbo.common.serialize.DefaultJsonDataOutput;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;


public class GsonJsonObjectOutput implements DefaultJsonDataOutput {

    private final PrintWriter writer;
    private Gson gson = null;

    public GsonJsonObjectOutput(OutputStream out) {
        this(new OutputStreamWriter(out));
    }

    public GsonJsonObjectOutput(Writer writer) {
        this.gson = new Gson();
        this.writer = new PrintWriter(writer);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        writer.println(new String(b));
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        writer.println(new String(b, off, len));
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        char[] json = gson.toJson(obj).toCharArray();
        writer.write(json, 0, json.length);
        writer.println();
        writer.flush();
        json = null;
    }

    @Override
    public void writeThrowable(Throwable obj) throws IOException {
        String clazz = obj.getClass().getName();
        ExceptionWrapper bo = new ExceptionWrapper(obj, clazz);
        this.writeObject(bo);
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }

}
