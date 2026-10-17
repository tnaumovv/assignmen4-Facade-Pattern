# Assignment 4 Media Conversion Facade Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Deliver the approved Option A Java 17 Facade project, UML diagram, README, and Word-style assignment report in PDF.

**Architecture:** `Main` calls one high-level `MediaConversionFacade.convert` method. The Facade coordinates four method-local subsystem helpers and passes small immutable records between them; the subsystem classes do not reference the Facade.

**Tech Stack:** Java 17, JDK standard library, standalone Java acceptance-check runner, editable DOCX, PDF export, SVG UML diagram.

**Spec:** `docs/superpowers/specs/2026-10-17-assignment4-media-facade-design.md`

## Global Constraints

- Use Java 17 and the standard library only.
- Simulate media metadata and conversion calculations; do not claim to process real media files.
- Write the report in English to match the supplied assignment brief.
- `Main` calls only the Facade and receives a `ConversionResult`.
- Keep all subsystem classes independent of the Facade and directly instantiable.
- Support AVI, MKV, and MP4 as simulated source formats; support MP4 and WebM as targets.
- Use only dashed dependency arrows with open arrowheads for method-level `uses` relationships; do not draw association, aggregation, composition, or inheritance where none exists.
- Report must contain introduction, UML, five Clean Code principles with brief code examples, conclusion, repository URL, and references to the assignment and Lecture 6.
- Format the report as a plain A4 Word document with standard margins, black text, ordinary headings, and page numbers.
- Do not edit the existing Bridge assignment or the untracked Assignment 2 folder.

## Review Focus

- Blank or null file names and names without an extension must produce clear `IllegalArgumentException` messages; cover in `AcceptanceChecks`.
- Source extension matching must be case-insensitive; cover with `SAMPLE.AVI` in `AcceptanceChecks`.
- Unsupported source formats must be rejected with a source-specific message; cover in `AcceptanceChecks`.
- Unsupported target formats must be rejected with a target-specific message; cover in `AcceptanceChecks`.
- The demo result must reflect the target video bitrate, normalized audio bitrate/peak, and recomputed output size; cover with exact expected values in `AcceptanceChecks`.

---

### Task 1: Define acceptance checks before implementation

**Files:**
- Create: `src/AcceptanceChecks.java`

**Interfaces:**
- Consumes: approved facade API `ConversionResult convert(String fileName, String targetFormat)`.
- Produces: a standalone, no-dependency check runner for valid conversion output and the validation cases.

- [ ] **Step 1: Write the check runner**

Add checks for a normal AVI-to-MP4 conversion (including exact computed values), case-insensitive extension parsing, blank/null/missing-extension input, unsupported source format, and unsupported target format. Use explicit comparisons and throw `AssertionError` on mismatch; do not depend on JVM `-ea`.

- [ ] **Step 2: Run the checks before implementation**

Run: `javac --release 17 -encoding UTF-8 -d out src/*.java`

Expected: compilation fails because the approved Facade and result types do not exist yet.

---

### Task 2: Implement the simulated conversion pipeline

**Files:**
- Create: `src/MediaMetadata.java`
- Create: `src/CodecPair.java`
- Create: `src/ConversionResult.java`
- Create: `src/MetadataReader.java`
- Create: `src/CodecFactory.java`
- Create: `src/BitrateReader.java`
- Create: `src/AudioMixer.java`
- Create: `src/MediaConversionFacade.java`
- Create: `src/Main.java`
- Modify: `src/AcceptanceChecks.java` only if its API-facing assertions need final accessors.

**Interfaces:**
- Consumes: the check runner from Task 1.
- Produces: the exact classes and signatures in the approved spec, plus `Main --demo`.

- [ ] **Step 1: Add the immutable data records**

Implement `MediaMetadata(String fileName, String sourceFormat, int durationSeconds, int sourceVideoBitrateKbps, int sourceAudioBitrateKbps, double audioPeakDb)`, `CodecPair(String decoderName, String encoderName, int targetVideoBitrateKbps, String targetFormat)`, and `ConversionResult(String fileName, String sourceFormat, String targetFormat, String decoderName, String encoderName, int durationSeconds, int videoBitrateKbps, int audioBitrateKbps, double audioPeakDb, long estimatedSizeKb)`. Add `ConversionResult.withAudioSettings(int audioBitrateKbps, double audioPeakDb, long estimatedSizeKb): ConversionResult` so `AudioMixer` returns a new result rather than mutating data.

- [ ] **Step 2: Implement metadata and codec selection**

Implement `MetadataReader.read` to validate the name, parse its extension with `Path`, lowercase the extension with `Locale.ROOT`, and return the deterministic simulated metadata (120 seconds, 4,500 kbps video, 192 kbps audio, -4.0 dB peak). Implement `CodecFactory.select` for the declared source/target sets: AVI uses the Xvid decoder; MKV and MP4 use the H.264 decoder; MP4 output uses the H.264 encoder at 2,500 kbps; WebM output uses the VP9 encoder at 1,800 kbps.

- [ ] **Step 3: Implement bitrate conversion and audio normalization**

Implement `BitrateReader.transcode` to cap output video bitrate and estimate output size. Implement `AudioMixer.normalizeAudio` to set the simulated peak to `-1.0 dB`, cap audio bitrate at 160 kbps, and recalculate size as `ceil((videoBitrateKbps + audioBitrateKbps) * durationSeconds / 8.0)` decimal kilobytes.

- [ ] **Step 4: Implement the Facade and Client**

Implement `MediaConversionFacade.convert` as the only coordination point. Keep subsystem objects method-local and expose only file name, target format, and `ConversionResult`. `Main --demo` creates only the Facade and prints one sample result.

- [ ] **Step 5: Run the acceptance checks**

Run: `javac --release 17 -encoding UTF-8 -d out src/*.java && java -cp out AcceptanceChecks`

Expected: all valid-conversion and validation checks print `PASS`; process exits with code 0.

- [ ] **Step 6: Run the live demo**

Run: `java -cp out Main --demo`

Expected: one concise result for `sample.avi` converted to `mp4`, with H.264 target codec, 2,500 kbps video, 160 kbps audio, `-1.0 dB` peak, and 39,900 kB estimated size.

---

### Task 3: Add README and source-matched UML

**Files:**
- Create: `README.md`
- Create: `report/Facade_Class_Diagram.svg`

**Interfaces:**
- Consumes: final source declarations and method signatures from Task 2.
- Produces: student-readable build/run guide and a UML class diagram with every shown arrow justified by source code.

- [ ] **Step 1: Write the README**

Document the purpose, simulated-data limitation, supported formats, build command, demo command, and source file map.

- [ ] **Step 2: Draw the UML diagram**

Show `Main`, `MediaConversionFacade`, all four subsystem classes, and the three record types. Draw these dashed open-arrow dependencies: `Main` to `MediaConversionFacade` and `ConversionResult`; `MediaConversionFacade` to all four subsystem classes and all three record types; `MetadataReader` to `MediaMetadata`; `CodecFactory` to `CodecPair`; `BitrateReader` to `MediaMetadata`, `CodecPair`, and `ConversionResult`; and `AudioMixer` to `ConversionResult`. Keep every arrow directed from the using class to the used class. Do not add arrows from a subsystem to the Facade or between subsystems.

- [ ] **Step 3: Review UML against source**

For each arrow, identify the exact constructor call, method call, parameter, or return type in source. Remove any unsupported arrow and add any omitted direct dependency.

---

### Task 4: Create the editable report and export PDF

**Files:**
- Create: `report/Assignment4_Facade_Report.docx`
- Create: `report/Assignment4_Facade_Report.pdf`
- Use: `report/Facade_Class_Diagram.svg`

**Interfaces:**
- Consumes: accepted source, README, and UML from Tasks 2-3.
- Produces: submission-ready report with the five required sections and readable code examples.

- [ ] **Step 1: Draft the report content**

Write the introduction, Facade explanation, five Clean Code principles with short before/after excerpts, conclusion, repository link, and references. State clearly that conversion values are simulated.

- [ ] **Step 2: Format the DOCX**

Use A4, standard margins, black Times New Roman text, ordinary heading styles, readable code excerpts, and page numbers. Place the UML on its own page if needed to keep labels legible.

- [ ] **Step 3: Export and inspect the PDF**

Render every page to PNG and visually inspect all pages. Repair any clipped text, broken arrows, unreadable UML labels, bad page breaks, or code wrapping, then export and inspect again.

---

### Task 5: Build the Git history and complete delivery checks

**Files:**
- Commit project files in the independent `Assignment4_Facade` Git repository.

**Interfaces:**
- Consumes: verified Java source, README, UML, report DOCX/PDF.
- Produces: clean project repository with incremental commits and the actual GitHub URL recorded in the README and report.

- [ ] **Step 1: Create focused commits**

Commit the implementation, documentation/UML, and report in separate commits after their checks pass.

- [ ] **Step 2: Publish the repository and insert its URL**

Create/push the GitHub repository, then update the README and report with the actual URL. Re-export and visually inspect the PDF after inserting the link.

- [ ] **Step 3: Run final delivery checks**

Run: `javac --release 17 -encoding UTF-8 -d out src/*.java`

Run: `java -cp out AcceptanceChecks`

Run: `java -cp out Main --demo`

Check: repository status is clean, the README commands match, the GitHub link opens, and the final PDF render has no visual defects.
