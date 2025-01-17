# Offline instrumentation

Will insert default probe to all classes in classDir and write the transformed class in output dir.
When run, the instrumented class will produce a json trace in a file called `yajta-trace.json` in the working directory.

Note that in order to run them you must add the yajta jar to your classpath (as the probe call classes from yajta).

```
# Instrument
java -cp yajta-offline/target/offline/-2.0.0-jar-with-dependencies.jar fr.inria.offline.Instrumenter -c classDir -o outputDir

# Run
java -cp outputDir:yajta-offline/target/offline/-2.0.0-jar-with-dependencies.jar org.myApp
```

# API

```Java
    //File classDir is the directory that contains byte code to be instrumented
    //You should implement in a class the interface Tracking (here TestLogger does)
    //Other constructors exist if you want to add filters to the class to be instrumented 
    //and/or specify the output for the transformed bytecode (by default a temporary directory is created)
    InstrumentationBuilder builder = new InstrumentationBuilder(classDir, TestLogger.class);
    //Note that the tracking class must also contain a static method getInstance() that returns an instance of the logger.
    //If bytecode is to be instrumented offline and run after the stop of the jvm, this getInstance() method should also register a shutdown hook that will call flush()
    //flush is supposed to contain whatever processing is supposed to be done after all logs are collected.

    //See fr.inria.yajta.api.loggerimplem.TestLogger as example.

    //Apply the instrumentation
    builder.instrument();
    //Optionally run the instrumented classes
    builder.setEntryPoint("fr.inria.helloworld.App", "main", String[].class);
    builder.runInstrumented((Object) new String[]{""});
```

Note that your logging facade should implement either `se.kth.castor.yajta.api.Tracking`, `se.kth.castor.yajta.api.FastTracking` or `se.kth.castor.yajta.api.ValueTracking`. Additionally it may implement `se.kth.castor.yajta.api.BranchTracking`


# Create a standalone instrumented Jar

```bash
	./script/instrument_jar.sh path/to/myJar.jar outputDIr
```

Run your application with the generated jar in the classpath. Temporary traces should appear in a directory traceDir

Run the following to generate json traces:

```bash
	java -cp path/to/yajta-offline-2.0.3-SNAPSHOT-jar-with-dependencies.jar se.kth.castor.offline.RemoteUserReader -i traceDir -o trace.json -f
```
