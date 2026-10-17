# Assignment 4 Media Conversion Facade Design

## Goal

Create a Java 17 Facade assignment for media/file conversion that is simple to demonstrate and defend, satisfies the supplied Assignment 4 requirements, and includes a readable Word-style report exported to PDF with an accurate UML class diagram.

## Scope and constraints

- Work in a self-contained `Assignment4_Facade` project folder; leave the existing Bridge assignment and the untracked Assignment 2 folder untouched.
- Use Java 17 and the standard library only.
- Simulate media metadata and conversion calculations. Do not claim to read, decode, or write real media files.
- Include a short README with build/run instructions and an actual GitHub repository URL in the report when a remote repository is available.
- Produce an editable DOCX source and a PDF report in a plain Word-style layout: A4, standard margins, black text, familiar fonts, ordinary headings, page numbers, and no decorative cover treatment.
- Use the student's existing coursework identity: Timur Naumov, SE-2537.
- The Client must call only the Facade; subsystem classes must not refer to the Facade and remain publicly usable on their own.

## Architecture

`Main` is the Client. It creates `MediaConversionFacade` and calls `convert(fileName, targetFormat)`. The Facade hides the ordering of metadata reading, codec selection, bitrate conversion, and audio normalization. It creates subsystem helper objects inside the conversion method, so it has no stored subsystem fields.

The conversion pipeline is:

1. `MetadataReader.read(fileName)` validates the file name, parses its extension, and returns deterministic simulated metadata.
2. `CodecFactory.select(sourceFormat, targetFormat)` validates supported formats and returns a `CodecPair` describing the decoder, encoder, and target video bitrate.
3. `BitrateReader.transcode(metadata, codecPair)` caps the video bitrate to the target codec limit and calculates an estimated output size.
4. `AudioMixer.normalizeAudio(result)` adjusts the simulated audio peak to -1.0 dB, caps output audio bitrate at 160 kbps, and recalculates estimated size.
5. The Facade returns a high-level `ConversionResult` to the Client.

Supported simulated source formats are AVI, MKV, and MP4. Supported targets are MP4 and WebM. The sample metadata is deterministic: 120 seconds, 4,500 kbps video, 192 kbps audio, and a -4.0 dB audio peak. Output size is an estimate in decimal kilobytes, computed as `(videoBitrateKbps + audioBitrateKbps) * durationSeconds / 8`.

## Class interfaces

- `MediaConversionFacade.convert(String fileName, String targetFormat): ConversionResult`
- `MetadataReader.read(String fileName): MediaMetadata`
- `CodecFactory.select(String sourceFormat, String targetFormat): CodecPair`
- `BitrateReader.transcode(MediaMetadata metadata, CodecPair codecs): ConversionResult`
- `AudioMixer.normalizeAudio(ConversionResult media): ConversionResult`
- `MediaMetadata` stores the input name, source format, duration, source video/audio bitrates, and simulated audio peak.
- `CodecPair` stores the decoder name, encoder name, and target video bitrate.
- `ConversionResult` stores the input/output formats, codec names, duration, output video/audio bitrates, normalized audio peak, and estimated size.

Subsystem objects are method-local helpers created by the Facade. UML therefore uses dashed dependencies with open arrowheads for Facade-to-subsystem relationships, not associations or diamonds. `Main` depends on the Facade and the returned result type. Subsystems depend on the record types named in their signatures. No subsystem points back to the Facade, and no subsystem-to-subsystem arrows are shown because coordination and handoff happen through the Facade.

## Error handling

Reject null or blank file names, missing extensions, unsupported source formats, and unsupported target formats with `IllegalArgumentException` messages that identify the problem. Do not return `null` or silently substitute a format. The demo uses a supported sample name and target.

## Report contents

The report contains:

1. Introduction describing the simulated conversion subsystem and why callers benefit from a Facade.
2. A UML class diagram that matches the Java files, including the Client, Facade, four subsystem classes, and the data records required by their signatures. Dashed open-arrow dependencies and their meaning are explained in a legend.
3. Five Clean Code principles with short before/after or annotated Java excerpts: meaningful names, Single Responsibility, a small high-level Facade API, focused subsystem responsibilities, and explicit error handling.
4. A conclusion stating what the Facade hides and which public subsystem classes remain usable directly.
5. The actual GitHub repository URL and references to the supplied assignment and Lecture 6.

## Verification

- Compile all Java source with `javac --release 17 -encoding UTF-8`.
- Run the documented `Main --demo` command and inspect the output values for the sample conversion.
- Check that the README commands and report descriptions match the implementation.
- Render the editable report to PDF and inspect every page, including the UML arrows, labels, code excerpts, page breaks, and legibility.
- Review the final UML against source declarations and method signatures, verifying arrow direction and relationship type.

## Explicit exclusions

- No real media codecs, external FFmpeg dependency, file mutation, GUI, or network upload workflow inside the Java program.
- No extra design patterns or optional secondary Facade.
- No unverified claims that simulated values represent real media conversion.
