package fr.inria.yajta.api.loggerimplem;

import fr.inria.yajta.api.SimpleTracerTest;
import fr.inria.yajta.api.Tracking;

import java.io.File;

public class IncompleteLogger3 implements Tracking {
    public static IncompleteLogger3 getInstance() {return null;}

    @Override
    public void setLogFile(File log) {

    }

    @Override
    public void stepIn(String thread, String clazz, String method) {

    }

    @Override
    public void stepOut(String thread) {

    }

    @Override
    public void flush() {

    }
}
