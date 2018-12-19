package com.zhoubi.graindepot.util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018-07-04.
 */
public class WrappedServletInputStream extends ServletInputStream {
    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    private InputStream stream;

    public WrappedServletInputStream(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public int read() throws IOException {
        return stream.read();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}

