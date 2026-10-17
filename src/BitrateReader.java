public class BitrateReader {
    public ConversionResult transcode(MediaMetadata metadata, CodecPair codecs) {
        if (metadata == null || codecs == null) {
            throw new IllegalArgumentException("Metadata and codec settings are required.");
        }

        int videoBitrateKbps = Math.min(
                metadata.sourceVideoBitrateKbps(), codecs.targetVideoBitrateKbps());
        int audioBitrateKbps = metadata.sourceAudioBitrateKbps();
        long estimatedSizeKb = estimateSizeKb(
                videoBitrateKbps, audioBitrateKbps, metadata.durationSeconds());

        return new ConversionResult(
                metadata.fileName(),
                metadata.sourceFormat(),
                codecs.targetFormat(),
                codecs.decoderName(),
                codecs.encoderName(),
                metadata.durationSeconds(),
                videoBitrateKbps,
                audioBitrateKbps,
                metadata.audioPeakDb(),
                estimatedSizeKb);
    }

    private long estimateSizeKb(int videoBitrateKbps, int audioBitrateKbps, int durationSeconds) {
        double kilobits = (videoBitrateKbps + (long) audioBitrateKbps) * durationSeconds;
        return (long) Math.ceil(kilobits / 8.0);
    }
}
