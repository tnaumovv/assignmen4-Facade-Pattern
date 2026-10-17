# Assignment 4: Media Conversion Facade

A small Java 17 example of the Facade design pattern. The client asks `MediaConversionFacade` to convert a file, and the Facade coordinates four independent subsystem classes.

The conversion is simulated. The program does not read, decode, or write media files. It uses fixed sample metadata to show the conversion steps and calculate an estimated output size.

## Supported formats

- Source: AVI, MKV, MP4
- Target: MP4, WebM

## Build and run

```bash
mkdir -p out
javac --release 17 -encoding UTF-8 -d out src/*.java
java -cp out Main --demo
```

Run the dependency-free acceptance checks with:

```bash
java -cp out AcceptanceChecks
```

## Source files

| File | Responsibility |
| --- | --- |
| `Main.java` | Client for the Facade and a runnable demo |
| `MediaConversionFacade.java` | Coordinates the conversion pipeline |
| `MetadataReader.java` | Validates the file name and returns simulated metadata |
| `CodecFactory.java` | Selects decoder and encoder settings |
| `BitrateReader.java` | Applies the target video bitrate and estimates size |
| `AudioMixer.java` | Normalizes the simulated audio settings |
| `MediaMetadata.java` | Immutable input metadata record |
| `CodecPair.java` | Immutable codec selection record |
| `ConversionResult.java` | Immutable high-level conversion result |
| `AcceptanceChecks.java` | Checks the conversion result and invalid inputs |

## GitHub repository

https://github.com/tnaumovv/assignmen4-Facade-Pattern

